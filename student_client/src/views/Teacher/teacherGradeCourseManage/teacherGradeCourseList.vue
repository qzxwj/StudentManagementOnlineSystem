<template>
  <el-card class="app-table-card">
    <el-table :data="tableData" empty-text="No grades match the search">
      <el-table-column prop="cid" label="Course ID" width="140">
        <template #default="scope">
          <span class="cell-mono">{{ scope.row.cid }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="cname" label="Course" min-width="160" />
      <el-table-column prop="sid" label="Student ID" width="120">
        <template #default="scope">
          <span class="cell-mono cell-mono--muted">{{ scope.row.sid }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="sname" label="Student" width="160" />
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
      <el-table-column label="Actions" width="120" align="right">
        <template #default="scope">
          <el-button text @click="editor(scope.row)">
            <el-icon><EditPen /></el-icon><span>Edit</span>
          </el-button>
        </template>
      </el-table-column>
      <template #empty>
        <el-empty description="No grade records for your courses yet" :image-size="80" />
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
import { reactive, toRefs, watch } from 'vue'
import { EditPen } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'

const props = defineProps({
  ruleForm: Object,
})

const router = useRouter()

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
    path: '/teacherEditorGradeCourse',
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
</style>
