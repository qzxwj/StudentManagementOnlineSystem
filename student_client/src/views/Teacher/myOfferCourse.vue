<template>
  <div>
    <div class="page-header">
      <div>
        <p class="page-header__eyebrow">Teacher · Courses</p>
        <h1 class="page-header__title">My Offerings</h1>
        <p class="page-header__subtitle">Courses you're teaching for the {{ term || 'current' }} term.</p>
      </div>
      <div class="page-header__actions">
        <el-button type="primary" class="press" @click="$router.push('/offerCourse')">
          <el-icon><Plus /></el-icon><span>Offer a course</span>
        </el-button>
      </div>
    </div>

    <el-card class="app-table-card">
      <el-table :data="tableData" empty-text="You haven't offered any courses yet">
        <el-table-column prop="cid" label="Course ID" width="160">
          <template #default="scope">
            <span class="cell-mono">{{ scope.row.cid }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="cname" label="Course name" />
        <el-table-column prop="ccredit" label="Credits" width="120">
          <template #default="scope">
            <el-tag size="small" type="primary">{{ scope.row.ccredit }} credits</el-tag>
          </template>
        </el-table-column>
        <template #empty>
          <el-empty description="You haven't offered any courses this term" :image-size="80">
            <el-button type="primary" class="press" @click="$router.push('/offerCourse')">
              Offer a course
            </el-button>
          </el-empty>
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
import { reactive, toRefs } from 'vue'
import { Plus } from '@element-plus/icons-vue'

const state = reactive({
  tableData: null,
  pageSize: 10,
  total: null,
  tmpList: null,
  tid: null,
  term: null,
})

const { tableData, pageSize, total, tmpList, tid, term } = toRefs(state)

state.tid = sessionStorage.getItem('tid')
state.term = sessionStorage.getItem('currentTerm')

axios.get('/courseTeacher/findMyCourse/' + state.tid + '/' + state.term).then(function (resp) {
  state.tmpList = resp.data
  state.total = resp.data.length
  let start = 0,
    end = state.pageSize
  let length = state.tmpList.length
  let ans = end < length ? end : length
  state.tableData = state.tmpList.slice(start, end)
})

function changePage(page) {
  page = page - 1
  let start = page * state.pageSize,
    end = state.pageSize * (page + 1)
  let length = state.tmpList.length
  let ans = end < length ? end : length
  state.tableData = state.tmpList.slice(start, ans)
}
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
</style>
