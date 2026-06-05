<template>
  <div>
    <el-card class="app-table-card">
      <el-table :data="tableData" border style="width: 100%">
        <el-table-column fixed prop="cid" label="Course ID" width="150"> </el-table-column>
        <el-table-column prop="cname" label="Course ID" width="150"> </el-table-column>
        <el-table-column prop="tid" label="Teacher ID" width="150"> </el-table-column>
        <el-table-column prop="tname" label="Teacher Name" width="150"> </el-table-column>
        <el-table-column prop="ccredit" label="Credits" width="150"> </el-table-column>
        <el-table-column label="Actions" width="100">
          <template #default="scope">
            <el-popconfirm
              confirm-button-text="Drop Course"
              cancel-button-text="Cancel"
              title="Confirm dropping this course?"
              @confirm="deleteSCT(scope.row)"
            >
              <template #reference>
                <el-button type="text" size="small">Drop Course</el-button>
              </template>
            </el-popconfirm>
          </template>
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
import { getCurrentInstance, reactive, toRefs } from 'vue'

const { proxy } = getCurrentInstance()

const state = reactive({
  tableData: null,
  pageSize: 10,
  total: null,
  tmpList: null,
  type: sessionStorage.getItem('type'),
})

const { tableData, pageSize, total, tmpList, type } = toRefs(state)

const sid = sessionStorage.getItem('sid')
const term = sessionStorage.getItem('currentTerm')
axios.get('/SCT/findBySid/' + sid + '/' + term).then(function (resp) {
  state.tmpList = resp.data
  state.total = resp.data.length
  let start = 0,
    end = state.pageSize
  let length = state.tmpList.length
  let ans = end < length ? end : length
  state.tableData = state.tmpList.slice(start, end)
})

function deleteSCT(row) {
  const cid = row.cid
  const tid = row.tid
  const sid = sessionStorage.getItem('sid')
  const term = sessionStorage.getItem('currentTerm')
  const sct = {
    cid: cid,
    tid: tid,
    sid: sid,
    term: term,
  }

  axios.post('/SCT/deleteBySCT', sct).then(function (resp) {
    if (resp.data === true) {
      proxy.$message({
        showClose: true,
        message: 'Course dropped successfully',
        type: 'success',
      })
      window.location.reload()
    } else {
      proxy.$message({
        showClose: true,
        message: 'Course drop failed. Contact the administrator',
        type: 'error',
      })
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
</script>
