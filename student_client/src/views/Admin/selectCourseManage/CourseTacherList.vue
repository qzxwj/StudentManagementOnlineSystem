<template>
  <div>
    <el-card class="app-table-card">
      <el-table :data="tableData" border show-header stripe style="width: 100%">
        <el-table-column fixed prop="cid" label="Course ID" width="150"> </el-table-column>
        <el-table-column prop="cname" label="Course ID" width="150"> </el-table-column>
        <el-table-column prop="tid" label="Teacher ID" width="150"> </el-table-column>
        <el-table-column prop="tname" label="Teacher Name" width="150"> </el-table-column>
        <el-table-column label="Actions" width="100">
          <template #default="scope">
            <el-popconfirm
              confirm-button-text="Delete"
              cancel-button-text="Cancel"
              icon-color="red"
              title="Deletion cannot be undone"
              @confirm="deleteCourseTeacher(scope.row)"
            >
              <template #reference>
                <el-button type="text" size="small">Delete</el-button>
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
import { getCurrentInstance, reactive, toRefs, watch } from 'vue'

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
  const sct = {
    cid: cid,
    tid: tid,
    sid: sid,
    term: term,
  }
  axios.post('/SCT/save', sct).then(function (resp) {
    if (resp.data === true) {
      proxy.$message({
        showClose: true,
        message: 'Course selected successfully',
        type: 'success',
      })
    } else {
      proxy.$message({
        showClose: true,
        message: 'Course selection failed. Contact the administrator',
        type: 'error',
      })
    }
  })
}

function deleteCourseTeacher(row) {
  axios.post('/courseTeacher/deleteById', row).then(function (resp) {
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
  (newRuleForm, oldRuleForm) => {
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
      state.tableData = state.tmpList.slice(start, ans)
    })
  },
  { deep: true, immediate: true },
)
</script>
