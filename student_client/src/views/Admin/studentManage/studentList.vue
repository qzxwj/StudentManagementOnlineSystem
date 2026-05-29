<template>
  <div>
    <el-table
        :data="tableData"
        border
        style="width: 100%">
      <el-table-column
          fixed
          prop="sid"
          label="Student ID"
          width="150">
      </el-table-column>
      <el-table-column
          prop="sname"
          label="Name"
          width="120">
      </el-table-column>
      <el-table-column
          label="Actions"
          width="100">
        <template #default="scope">
          <el-popconfirm
              confirm-button-text='Delete'
              cancel-button-text='Cancel'
              icon-color="red"
              title="Deletion cannot be undone"
              @confirm="deleteStudent(scope.row)"
          >
            <template #reference>

              <el-button type="text" size="small">Delete</el-button>

            </template>
          </el-popconfirm>
          <el-button @click="editor(scope.row)" type="text" size="small">Edit</el-button>
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
  </div>
</template>

<script setup>
import { getCurrentInstance, reactive, toRefs } from 'vue'

import { useRoute, useRouter } from 'vue-router'

const router = useRouter()
const route = useRoute()

const { proxy } = getCurrentInstance()

const state = reactive({
      tableData: null,
      pageSize: 7,
      total: null,
      ruleForm: null,
      tmpList: null
    })

const { tableData, pageSize, total, ruleForm, tmpList } = toRefs(state)

if (state.tmpList !== null)
      state.tmpList = null
    // ManagementSearch
    state.ruleForm = route.query.ruleForm
    if (route.query.ruleForm === undefined || (state.ruleForm.sid === null && state.ruleForm.sname === null)) {
      axios.get('/student/getLength').then(function (resp) {
        state.total = resp.data
      })

      axios.get('/student/findByPage/0/' + state.pageSize).then(function (resp) {
        state.tableData = resp.data
      })
    }
    else {
      // ManagementSearchSearch
      axios.post('/student/findBySearch', state.ruleForm).then(function (resp) {
        state.tmpList = resp.data
        state.total = resp.data.length
        let start = 0, end = state.pageSize
        let length = state.tmpList.length
        let ans = end < length ? end : length
        state.tableData = state.tmpList.slice(start, ans)
      })
    }

function deleteStudent(row) {
axios.get('/student/deleteById/' + row.sid).then(function (resp) {
        if (resp.data === true) {
          proxy.$message({
            showClose: true,
            message: 'Deleted successfully',
            type: 'success'
          });
          if (state.tmpList === null) {
            window.location.reload()
          }
          else {
            router.push('/queryStudent')
          }
        }
        else {
          proxy.$message({
            showClose: true,
            message: 'Delete failed,ManagementSearch',
            type: 'error'
          });
        }
      }).catch(function (e) {
        proxy.$message({
          showClose: true,
          message: 'Delete failed,Management',
          type: 'error'
        });
      })
}

function changePage(page) {
page = page - 1
      if (state.tmpList === null) {
        axios.get('/student/findByPage/' + page + '/' + state.pageSize).then(function (resp) {
          state.tableData = resp.data
        })
      }
      else {
        let start = page * state.pageSize, end = state.pageSize * (page + 1)
        let length = state.tmpList.length
        let ans = end < length ? end : length
        state.tableData = state.tmpList.slice(start, ans)
      }
}

function editor(row) {
router.push({
        path: '/editorStudent',
        query: {
          sid: row.sid
        }
      })
}
</script>
