<template>
  <div>
    <el-table
        :data="tableData"
        border
        style="width: 100%">
      <el-table-column
          fixed
          prop="tid"
          label="Teacher ID"
          width="150">
      </el-table-column>
      <el-table-column
          prop="tname"
          label="Name"
          width="150">
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
  </div>
</template>

<script setup>
import { getCurrentInstance, reactive, toRefs, watch } from 'vue'

import { useRouter } from 'vue-router'

const props = defineProps({
    ruleForm: Object
  })

const router = useRouter()

const { proxy } = getCurrentInstance()

const state = reactive({
      tableData: null,
      pageSize: 10,
      total: null,
      tmpList: null,
    })

const { tableData, pageSize, total, tmpList } = toRefs(state)

function deleteTeacher(row) {
if (row.tname === 'admin') {
        proxy.$message({
          showClose: true,
          message: 'Admin HomeDelete',
          type: 'error'
        });
        return
      }
      axios.get('/teacher/deleteById/' + row.tid).then(function (resp) {
        if (resp.data === true) {
          proxy.$message({
            showClose: true,
            message: 'Deleted successfully',
            type: 'success'
          });
          window.location.reload()
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
      let start = page * state.pageSize, end = state.pageSize * (page + 1)
      let length = state.tmpList.length
      let ans = (end < length) ? end : length
      state.tableData = state.tmpList.slice(start, ans)
}

function editor(row) {
if (row.tname === 'admin') {
        proxy.$message({
          showClose: true,
          message: 'Admin HomeEdit',
          type: 'error'
        });
        return
      }
      router.push({
        path: '/editorTeacher',
        query: {
          tid: row.tid
        }
      })
}

watch(() => props.ruleForm, (newRuleForm, oldRuleForm) => {
        state.tmpList = null
        state.total = null
        state.tableData = null
        axios.post("/teacher/findBySearch", newRuleForm).then(function (resp) {
          state.tmpList = resp.data
          state.total = resp.data.length
          let start = 0, end = state.pageSize
          let length = state.tmpList.length
          let ans = (end < length) ? end : length
          state.tableData = state.tmpList.slice(start, end)
        })
}, { deep: true, immediate: true })
</script>
