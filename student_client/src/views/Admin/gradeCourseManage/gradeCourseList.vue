<template>
  <el-card class="app-table-card">
    <el-table :data="tableData" empty-text="No grades match the search">
      <el-table-column prop="cid" label="Course ID" width="140">
        <template #default="scope">
          <span class="cell-mono">{{ scope.row.cid }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="cname" label="Course" min-width="160" />
      <el-table-column prop="tid" label="Teacher ID" width="120">
        <template #default="scope">
          <span class="cell-mono cell-mono--muted">{{ scope.row.tid }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="tname" label="Teacher" width="140" />
      <el-table-column prop="sid" label="Student ID" width="120">
        <template #default="scope">
          <span class="cell-mono cell-mono--muted">{{ scope.row.sid }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="sname" label="Student" width="140" />
      <el-table-column prop="grade" label="Grade" width="120">
        <template #default="scope">
          <el-tag size="small" :type="gradeType(scope.row.grade)">
            {{ scope.row.grade ?? '—' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="term" label="Term" width="120">
        <template #default="scope">
          <el-tag size="small" effect="plain" type="info">{{ scope.row.term }}</el-tag>
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
            title="Delete this grade record? This cannot be undone."
            @confirm="deleteRecord(scope.row)"
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
        <el-empty description="No grade records found" :image-size="80" />
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

function gradeType(grade) {
  if (grade === null || grade === undefined) return 'info'
  const g = Number(grade)
  if (g >= 85) return 'success'
  if (g >= 60) return 'primary'
  return 'danger'
}

function deleteRecord(row) {
  const { sid, cid, tid, term } = row
  axios
    .get('/SCT/deleteById/' + sid + '/' + cid + '/' + tid + '/' + term)
    .then(function (resp) {
      if (resp.data === true) {
        proxy.$message({ message: 'Grade record deleted', type: 'success' })
        axios.post('/SCT/findBySearch', props.ruleForm).then((r) => {
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
  router.push({
    path: '/editorGradeCourse',
    query: { cid: row.cid, tid: row.tid, sid: row.sid, term: row.term },
  })
}

watch(
  () => props.ruleForm,
  (newRuleForm) => {
    state.tmpList = null
    state.total = null
    state.tableData = null
    axios.post('/SCT/findBySearch', newRuleForm).then(function (resp) {
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
.cell-mono--muted {
  color: var(--ink-muted);
}
.row-action-danger {
  color: var(--ink-muted) !important;
}
.row-action-danger:hover {
  color: var(--danger) !important;
  background: var(--danger-soft) !important;
}
</style>
