<template>
  <div>
    <el-container>
      <el-main>
        <h1>Current TermOffer Course</h1>
        <el-card class="app-table-card">
          <el-table :data="tableData" border stripe style="width: 100%">
            <el-table-column fixed prop="cid" label="Course ID" width="150"> </el-table-column>
            <el-table-column prop="cname" label="Course Name" width="150"> </el-table-column>
            <el-table-column prop="ccredit" label="Credits" width="150"> </el-table-column>
          </el-table>
          <el-pagination
            background
            layout="prev, pager, next"
            :total="total"
            :page-size="pageSize"
            @current-change="changePage"
          >
          </el-pagination>
        </el-card>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import { reactive, toRefs } from 'vue'

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
  state.tableData = state.tmpList.slice(start, ans)
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
