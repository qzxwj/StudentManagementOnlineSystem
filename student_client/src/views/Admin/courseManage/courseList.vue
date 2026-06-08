<template>
  <el-card class="app-table-card">
    <el-table :data="tableData" empty-text="No courses found">
      <el-table-column prop="cid" label="Course ID" width="160">
        <template #default="scope">
          <span class="cell-mono">{{ scope.row.cid }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="cname" label="Course name" />
      <el-table-column prop="ccredit" label="Credits" width="140">
        <template #default="scope">
          <el-tag size="small" type="primary">{{ scope.row.ccredit }} credits</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="Actions" width="180" align="right">
        <template #default="scope">
          <el-button text @click="editor(scope.row)">
            <el-icon><EditPen /></el-icon><span>Edit</span>
          </el-button>
          <el-popconfirm
            v-if="type !== 'teacher'"
            confirm-button-text="Delete"
            cancel-button-text="Cancel"
            title="Delete this course? This cannot be undone."
            @confirm="deleteCourse(scope.row)"
          >
            <template #reference>
              <el-button text class="row-action-danger">
                <el-icon><Delete /></el-icon><span>Delete</span>
              </el-button>
            </template>
          </el-popconfirm>
          <el-popconfirm
            v-else
            confirm-button-text="Offer"
            cancel-button-text="Cancel"
            title="Offer this course for the current term?"
            @confirm="offer(scope.row)"
          >
            <template #reference>
              <el-button text class="row-action-primary">
                <el-icon><Plus /></el-icon><span>Offer</span>
              </el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="No courses match the search" :image-size="80" />
      </template>
    </el-table>
    <div class="app-pagination">
      <el-pagination
        background
        layout="prev, pager, next"
        :total="total || 0"
        :page-size="pageSize"
        @current-change="changePage"
      />
    </div>
  </el-card>
</template>

<script setup>
import { getCurrentInstance, reactive, toRefs, watch } from 'vue'
import { Delete, EditPen, Plus } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'

const props = defineProps({
  ruleForm: Object,
  isActive: Boolean,
})

const router = useRouter()
const { proxy } = getCurrentInstance()

const state = reactive({
  tableData: null,
  pageSize: 10,
  total: null,
  tmpList: null,
  type: sessionStorage.getItem('type'),
})

const { tableData, pageSize, total, tmpList, type } = toRefs(state)

function deleteCourse(row) {
  axios
    .get('/course/deleteById/' + row.cid)
    .then(function (resp) {
      if (resp.data === true) {
        proxy.$message({ message: 'Course deleted', type: 'success' })
        axios.post('/course/findBySearch', props.ruleForm).then((r) => {
          state.tmpList = r.data
          state.total = r.data.length
          state.tableData = r.data.slice(0, state.pageSize)
        })
      } else {
        proxy.$message({ message: 'Delete failed. Please try again.', type: 'error' })
      }
    })
    .catch(function () {
      proxy.$message({ message: 'Network error. Please try again.', type: 'error' })
    })
}

function offer(row) {
  const tid = sessionStorage.getItem('tid')
  const cid = row.cid
  const term = sessionStorage.getItem('currentTerm')

  axios.get('/courseTeacher/insert/' + cid + '/' + tid + '/' + term).then(function (resp) {
    if (resp.data === true) {
      proxy.$message({ message: 'Course offered for this term', type: 'success' })
    } else {
      proxy.$message({ message: 'Could not offer the course. Please try again.', type: 'error' })
    }
  })
}

function changePage(page) {
  page = page - 1
  let start = page * state.pageSize,
    end = state.pageSize * (page + 1)
  let length = state.tmpList.length
  let ans = end < length ? end : length
  state.tableData = state.tmpList.slice(start, ans)
}

function editor(row) {
  router.push({
    path: '/editorCourse',
    query: { cid: row.cid },
  })
}

watch(
  () => props.ruleForm,
  (newRuleForm) => {
    state.tmpList = null
    state.total = null
    state.tableData = null
    axios.post('/course/findBySearch', newRuleForm).then(function (resp) {
      state.tmpList = resp.data
      state.total = resp.data.length
      let start = 0,
        end = state.pageSize
      let length = state.tmpList.length
      let ans = end < length ? end : length
      state.tableData = state.tmpList.slice(start, end)
    })
  },
  { deep: true, immediate: true },
)
</script>

<style scoped>
.app-pagination {
  padding: 16px 20px;
  border-top: 1px solid var(--hairline-soft);
}
.cell-mono {
  font-family: 'JetBrains Mono', ui-monospace, monospace;
  font-size: 13px;
  color: var(--sky-700);
  font-weight: 500;
}
.row-action-danger {
  color: var(--ink-muted) !important;
}
.row-action-danger:hover {
  color: var(--danger) !important;
  background: var(--danger-soft) !important;
}
.row-action-primary {
  color: var(--sky-700) !important;
}
.row-action-primary:hover {
  background: var(--sky-50) !important;
}
</style>
