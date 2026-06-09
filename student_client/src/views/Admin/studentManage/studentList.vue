<template>
  <div>
    <div class="page-header">
      <div>
        <p class="page-header__eyebrow">Admin · Students</p>
        <h1 class="page-header__title">All Students</h1>
        <p class="page-header__subtitle">Browse, edit, and remove student records.</p>
      </div>
    </div>

    <el-card class="app-table-card">
      <el-table :data="tableData" empty-text="No students yet">
        <el-table-column prop="sid" label="Student ID" width="160">
          <template #default="scope">
            <span class="cell-mono">{{ scope.row.sid }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="sname" label="Name" />
        <el-table-column label="Actions" width="180" align="right">
          <template #default="scope">
            <el-button text @click="editor(scope.row)">
              <el-icon><EditPen /></el-icon><span>Edit</span>
            </el-button>
            <el-popconfirm
              confirm-button-text="Delete"
              cancel-button-text="Cancel"
              title="Delete this student? This cannot be undone."
              @confirm="deleteStudent(scope.row)"
            >
              <template #reference>
                <el-button text class="row-action-danger">
                  <el-icon><Delete /></el-icon><span>Delete</span>
                </el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
        <template #empty>
          <el-empty description="No students found" :image-size="80" />
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
import { getCurrentInstance, reactive, toRefs } from 'vue'
import { Delete, EditPen } from '@element-plus/icons-vue'
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
        proxy.$message({ message: 'Student deleted', type: 'success' })
        loadTotal()
        loadPage(0)
      } else {
        proxy.$message({ message: 'Delete failed. Please try again.', type: 'error' })
      }
    })
    .catch(function () {
      proxy.$message({ message: 'Network error. Please try again.', type: 'error' })
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
    query: { sid: row.sid },
  })
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
.row-action-danger {
  color: var(--ink-muted) !important;
}
.row-action-danger:hover {
  color: var(--danger) !important;
  background: var(--danger-soft) !important;
}
</style>
