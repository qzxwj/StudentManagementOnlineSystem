<template>
  <div>
    <el-card class="app-table-card">
      <el-table :data="tableData" border stripe style="width: 100%">
        <el-table-column fixed prop="cid" label="Course ID" width="150"> </el-table-column>
        <el-table-column prop="cname" label="Course Name" width="150"> </el-table-column>
        <el-table-column prop="ccredit" label="Credits" width="150"> </el-table-column>
        <el-table-column label="Actions" width="100">
          <template #default="scope">
            <el-popconfirm
              confirm-button-text="Delete"
              cancel-button-text="Cancel"
              icon-color="red"
              title="Deletion cannot be undone"
              @confirm="deleteTeacher(scope.row)"
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
import { getCurrentInstance, reactive, toRefs, watch } from 'vue'

import { useRouter } from 'vue-router'

const props = defineProps({
  ruleForm: Object,
  isActive: Boolean,
})

const router = useRouter()

const { proxy } = getCurrentInstance()

const state = reactive({
  tableData: null,
  pageSize: 10,
  total: null,
  tmpList: null,
  type: sessionStorage.getItem('type'),
})

const { tableData, pageSize, total, tmpList, type } = toRefs(state)

function select(row) {}

function deleteTeacher(row) {
  axios
    .get('/course/deleteById/' + row.cid)
    .then(function (resp) {
      if (resp.data === true) {
        proxy.$message({
          showClose: true,
          message: 'Deleted successfully',
          type: 'success',
        })
        window.location.reload()
      } else {
        proxy.$message({
          showClose: true,
          message: 'Delete failed,ManagementSearch',
          type: 'error',
        })
      }
    })
    .catch(function (error) {
      proxy.$message({
        showClose: true,
        message: 'Delete failed,Management',
        type: 'error',
      })
    })
}

function offer(row) {
  const tid = sessionStorage.getItem('tid')
  const cid = row.cid
  const term = sessionStorage.getItem('currentTerm')

  axios.get('/courseTeacher/insert/' + cid + '/' + tid + '/' + term).then(function (resp) {
    if (resp.data === true) {
      proxy.$message({
        showClose: true,
        message: 'Offer Course',
        type: 'success',
      })
      window.location.reload()
    } else {
      proxy.$message({
        showClose: true,
        message: 'Offer Course,Contact the administrator',
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

function editor(row) {
  router.push({
    path: '/editorCourse',
    query: {
      cid: row.cid,
    },
  })
}

watch(
  () => props.ruleForm,
  (newRuleForm, oldRuleForm) => {
    state.tmpList = null
    state.total = null
    state.tableData = null
    axios.post('/course/findBySearch', newRuleForm).then(function (resp) {
      state.tmpList = resp.data
      state.total = resp.data.length
      let start = 0,
        end = state.pageSize
      let length = state.tmpList.length
      let ans = end < length ? end : length
      state.tableData = state.tmpList.slice(start, ans)
    })
  },
  { deep: true, immediate: true },
)
</script>
