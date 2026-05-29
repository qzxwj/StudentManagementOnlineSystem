<template>
  <div>
    <el-form >
      <el-form-item label="SelectTerm">
        <el-select v-model="term" placeholder="Please select a term">
          <el-option v-for="(item, index) in termList" :key="index" :label="item" :value="item"></el-option>
        </el-select>
      </el-form-item>
    </el-form>
    <el-card>
      <el-table
          :data="tableData"
          border
          style="width: 100%">
        <el-table-column
            fixed
            prop="cid"
            label="Course ID"
            width="150">
        </el-table-column>
        <el-table-column
            prop="cname"
            label="Course ID"
            width="150">
        </el-table-column>
        <el-table-column
            prop="tid"
            label="Teacher ID"
            width="150">
        </el-table-column>
        <el-table-column
            prop="tname"
            label="Teacher Name"
            width="150">
        </el-table-column>
        <el-table-column
            prop="ccredit"
            label="Credits"
            width="150">
        </el-table-column>
        <el-table-column
            prop="grade"
            label="Grade"
            width="150">
        </el-table-column>
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
      termList: null
    })

const { tableData, pageSize, total, tmpList, term, termList } = toRefs(state)

axios.get('/SCT/findAllTerm').then(function (resp) {
      state.termList = resp.data
    })

function changePage(page) {
page = page - 1
      let start = page * state.pageSize, end = state.pageSize * (page + 1)
      let length = state.tmpList.length
      let ans = (end < length) ? end : length
      state.tableData = state.tmpList.slice(start, ans)
}

watch(() => state.term, (newTerm, oldTerm) => {
const sid = sessionStorage.getItem('sid')
        axios.get('/SCT/findBySid/' + sid + '/' + newTerm).then(function (resp) {
          state.tmpList = resp.data
          state.total = resp.data.length
          let start = 0, end = state.pageSize
          let length = state.tmpList.length
          let ans = (end < length) ? end : length
          state.tableData = state.tmpList.slice(start, end)
        })
}, { immediate: true })
</script>

<!--
  TODO:
  1. Admin:
    1. Student Management
    2. Grade Management(Current Term)
  2. Student:Grade Management
  3. Teacher:Grade Management(?Management)(Current Term)

-->
