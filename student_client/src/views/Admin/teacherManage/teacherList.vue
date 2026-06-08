<template>
  <el-card class="app-table-card">
    <el-table :data="tableData" empty-text="No teachers found">
      <el-table-column prop="tid" label="Teacher ID" width="160">
        <template #default="scope">
          <span class="cell-mono">{{ scope.row.tid }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="tname" label="Name">
        <template #default="scope">
          <span>{{ scope.row.tname }}</span>
          <el-tag v-if="scope.row.tname === 'admin'" size="small" type="danger" effect="light" style="margin-left: 8px">
            admin
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="Actions" width="180" align="right">
        <template #default="scope">
          <el-button text @click="editor(scope.row)">
            <el-icon><EditPen /></el-icon><span>Edit</span>
          </el-button>
          <el-popconfirm
            confirm-button-text="Delete"
            cancel-button-text="Cancel"
            title="Delete this teacher? This cannot be undone."
            @confirm="deleteTeacher(scope.row)"
          >
            <template #reference>
              <el-button text class="row-action-danger">
                <el-icon><Delete /></el-icon><span>Delete</span>
              </el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="No teachers match the search" :image-size="80" />
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
import { Delete, EditPen } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'

const props = defineProps({
  ruleForm: Object,
})

const router = useRouter()
const { proxy } = getCurrentInstance()

const state = reactive({
  tableData: null,
  pageSize: 10,
  total: null,
  tmpList: null,
})

const { tableData, pageSize, total, tmpList } = toRefs(state)

function deleteTeacher(row) {
  if (row.tname === 'admin') {
    proxy.$message({ message: 'The built-in admin account cannot be deleted.', type: 'error' })
    return
  }
  axios
    .get('/teacher/deleteById/' + row.tid)
    .then(function (resp) {
      if (resp.data === true) {
        proxy.$message({ message: 'Teacher deleted', type: 'success' })
        // refresh by triggering watch
        state.tmpList = null
        state.total = null
        state.tableData = null
        axios.post('/teacher/findBySearch', props.ruleForm).then((r) => {
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

function changePage(page) {
  page = page - 1
  let start = page * state.pageSize,
    end = state.pageSize * (page + 1)
  let length = state.tmpList.length
  let ans = end < length ? end : length
  state.tableData = state.tmpList.slice(start, ans)
}

function editor(row) {
  if (row.tname === 'admin') {
    proxy.$message({ message: 'The built-in admin account cannot be edited.', type: 'error' })
    return
  }
  router.push({
    path: '/editorTeacher',
    query: { tid: row.tid },
  })
}

watch(
  () => props.ruleForm,
  (newRuleForm) => {
    state.tmpList = null
    state.total = null
    state.tableData = null
    axios.post('/teacher/findBySearch', newRuleForm).then(function (resp) {
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
</style>
