<template>
  <div>
    <el-card class="app-table-card">
      <el-table :data="tableData" border style="width: 100%">
        <el-table-column fixed prop="sid" label="Student ID" width="150"> </el-table-column>
        <el-table-column prop="sname" label="Name" width="120"> </el-table-column>
        <el-table-column label="Actions" width="100">
          <template #default="scope">
            <el-popconfirm
              confirm-button-text="Delete"
              cancel-button-text="Cancel"
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
    </el-card>
  </div>
</template>

<script setup>
import { getCurrentInstance, reactive, toRefs } from 'vue'

import { useRouter } from 'vue-router'

const router = useRouter()

const { proxy } = getCurrentInstance()

const state = reactive({
  tableData: null,
  pageSize: 7,
  total: null,
})

const { tableData, pageSize, total } = toRefs(state)

loadTotal()
loadPage(0)

function deleteStudent(row) {
  axios
    .get('/student/deleteById/' + row.sid)
    .then(function (resp) {
      if (resp.data === true) {
        proxy.$message({
          showClose: true,
          message: 'Deleted successfully',
          type: 'success',
        })
        loadTotal()
        loadPage(0)
      } else {
        proxy.$message({
          showClose: true,
          message: 'Delete failed,ManagementSearch',
          type: 'error',
        })
      }
    })
    .catch(function (e) {
      proxy.$message({
        showClose: true,
        message: 'Delete failed,Management',
        type: 'error',
      })
    })
}

function changePage(page) {
  loadPage(page - 1)
}

function loadTotal() {
  axios.get('/student/getLength').then(function (resp) {
    state.total = resp.data
  })
}

function loadPage(page) {
  axios.get('/student/findByPage/' + page + '/' + state.pageSize).then(function (resp) {
    state.tableData = resp.data
  })
}

function editor(row) {
  router.push({
    path: '/editorStudent',
    query: {
      sid: row.sid,
    },
  })
}
</script>
