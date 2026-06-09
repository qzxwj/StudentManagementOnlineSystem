<template>
  <div>
    <div class="page-header">
      <div>
        <p class="page-header__eyebrow">Student · Grades</p>
        <h1 class="page-header__title">My Grades</h1>
        <p class="page-header__subtitle">Term-by-term grade history.</p>
      </div>
    </div>

    <el-card class="app-query-card">
      <el-form :inline="true" class="query-form">
        <el-form-item label="Term">
          <el-select v-model="term" placeholder="All terms" clearable style="width: 200px">
            <el-option
              v-for="(item, index) in termList"
              :key="index"
              :label="item"
              :value="item"
            />
          </el-select>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card class="app-table-card">
      <el-table :data="tableData" empty-text="No grades for this term yet">
        <el-table-column prop="cid" label="Course ID" width="160">
          <template #default="scope">
            <span class="cell-mono">{{ scope.row.cid }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="cname" label="Course name" />
        <el-table-column prop="tid" label="Teacher ID" width="140">
          <template #default="scope">
            <span class="cell-mono cell-mono--muted">{{ scope.row.tid }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="tname" label="Teacher" width="160" />
        <el-table-column prop="ccredit" label="Credits" width="120">
          <template #default="scope">
            <el-tag size="small" type="primary">{{ scope.row.ccredit }} credits</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="grade" label="Grade" width="140">
          <template #default="scope">
            <el-tag size="small" :type="gradeType(scope.row.grade)">
              {{ scope.row.grade ?? '—' }}
            </el-tag>
          </template>
        </el-table-column>
        <template #empty>
          <el-empty description="No grades for this term" :image-size="80" />
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
  </div>
</template>

<script setup>
import { reactive, toRefs, watch } from 'vue'

const state = reactive({
  tableData: null,
  pageSize: 10,
  total: null,
  tmpList: null,
  term: sessionStorage.getItem('currentTerm'),
  termList: null,
})

const { tableData, pageSize, total, tmpList, term, termList } = toRefs(state)

axios.get('/SCT/findAllTerm').then(function (resp) {
  state.termList = resp.data
})

function changePage(page) {
  page = page - 1
  let start = page * state.pageSize,
    end = state.pageSize * (page + 1)
  let length = state.tmpList.length
  let ans = end < length ? end : length
  state.tableData = state.tmpList.slice(start, ans)
}

function gradeType(grade) {
  if (grade === null || grade === undefined) return 'info'
  const g = Number(grade)
  if (g >= 85) return 'success'
  if (g >= 60) return 'primary'
  return 'danger'
}

watch(
  () => state.term,
  (newTerm) => {
    const sid = sessionStorage.getItem('sid')
    axios.get('/SCT/findBySid/' + sid + '/' + newTerm).then(function (resp) {
      state.tmpList = resp.data
      state.total = resp.data.length
      let start = 0,
        end = state.pageSize
      let length = state.tmpList.length
      let ans = end < length ? end : length
      state.tableData = state.tmpList.slice(start, end)
    })
  },
  { immediate: true },
)
</script>

<style scoped>
.query-form {
  max-width: none;
  display: flex;
  flex-wrap: wrap;
  gap: 16px 24px;
  align-items: flex-end;
}
.query-form :deep(.el-form-item) {
  margin-bottom: 0;
  margin-right: 0;
}
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
