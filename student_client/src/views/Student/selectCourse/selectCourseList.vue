<template>
  <el-card class="app-table-card">
    <el-table :data="tableData" empty-text="No courses match the search">
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
      <el-table-column prop="ccredit" label="Credits" width="120">
        <template #default="scope">
          <el-tag size="small" type="primary">{{ scope.row.ccredit }} credits</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="Actions" width="140" align="right">
        <template #default="scope">
          <el-popconfirm
            confirm-button-text="Select"
            cancel-button-text="Cancel"
            title="Add this course to your schedule?"
            @confirm="select(scope.row)"
          >
            <template #reference>
              <el-button text class="row-action-primary">
                <el-icon><Plus /></el-icon><span>Select</span>
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
import { Plus } from '@element-plus/icons-vue'

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

function select(row) {
  const cid = row.cid
  const tid = row.tid
  const sid = sessionStorage.getItem('sid')
  const term = sessionStorage.getItem('currentTerm')
  const sct = { cid, tid, sid, term }
  axios.post('/SCT/save', sct).then(function (resp) {
    if (resp.data === 'Course selected successfully') {
      proxy.$message({ message: 'Course added to your schedule', type: 'success' })
    } else {
      proxy.$message({ message: resp.data || 'Could not add the course.', type: 'error' })
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
.row-action-primary {
  color: var(--sky-700) !important;
  font-weight: 500;
}
.row-action-primary:hover {
  background: var(--sky-50) !important;
}
</style>
