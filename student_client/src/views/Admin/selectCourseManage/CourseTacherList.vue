<template>
  <el-card class="app-table-card">
    <el-table :data="tableData" empty-text="No offerings match the search">
      <el-table-column prop="cid" label="Course ID" width="140">
        <template #default="scope">
          <span class="cell-mono">{{ scope.row.cid }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="cname" label="Course" min-width="160" />
      <el-table-column prop="tid" label="Teacher ID" width="140">
        <template #default="scope">
          <span class="cell-mono cell-mono--muted">{{ scope.row.tid }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="tname" label="Teacher" width="180" />
      <el-table-column label="Actions" width="140" align="right">
        <template #default="scope">
          <el-popconfirm
            confirm-button-text="Remove"
            cancel-button-text="Cancel"
            title="Remove this teacher from this course offering?"
            @confirm="deleteCourseTeacher(scope.row)"
          >
            <template #reference>
              <el-button text class="row-action-danger">
                <el-icon><Delete /></el-icon><span>Remove</span>
              </el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="No offerings match the search" :image-size="80" />
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
import { Delete } from '@element-plus/icons-vue'

const props = defineProps({
  ruleForm: Object,
})

const { proxy } = getCurrentInstance()

const state = reactive({
  tableData: null,
  pageSize: 10,
  total: null,
  tmpList: null,
  type: sessionStorage.getItem('type'),
})

const { tableData, pageSize, total, tmpList, type } = toRefs(state)

function deleteCourseTeacher(row) {
  axios
    .post('/courseTeacher/deleteById', row)
    .then(function (resp) {
      if (resp.data === true) {
        proxy.$message({ message: 'Offering removed', type: 'success' })
        axios.post('/courseTeacher/findCourseTeacherInfo', props.ruleForm).then((r) => {
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

watch(
  () => props.ruleForm,
  (newRuleForm) => {
    state.tmpList = null
    state.total = null
    state.tableData = null
    axios.post('/courseTeacher/findCourseTeacherInfo', newRuleForm).then(function (resp) {
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
