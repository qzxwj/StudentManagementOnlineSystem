<template>
  <div>
    <div class="page-header">
      <div>
        <p class="page-header__eyebrow">Student · Courses</p>
        <h1 class="page-header__title">My Schedule</h1>
        <p class="page-header__subtitle">Courses you've selected for the {{ term || 'current' }} term.</p>
      </div>
      <div class="page-header__actions">
        <el-button type="primary" class="press" @click="$router.push('/studentSelectCourse')">
          <el-icon><Plus /></el-icon><span>Add a course</span>
        </el-button>
      </div>
    </div>

    <el-card class="app-table-card">
      <el-table :data="tableData" empty-text="You haven't selected any courses yet">
        <el-table-column prop="cid" label="Course ID" width="160">
          <template #default="scope">
            <span class="cell-mono">{{ scope.row.cid }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="cname" label="Course name" />
        <el-table-column prop="tid" label="Teacher ID" width="140">
          <template #default="scope">
            <span class="cell-mono cell-mono--muted">{{ scope.row.tid }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="tname" label="Teacher" width="160" />
        <el-table-column prop="ccredit" label="Credits" width="120">
          <template #default="scope">
            <el-tag size="small" type="primary">{{ scope.row.ccredit }} credits</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="Actions" width="160" align="right">
          <template #default="scope">
            <el-popconfirm
              confirm-button-text="Drop"
              cancel-button-text="Cancel"
              title="Drop this course? This cannot be undone."
              @confirm="deleteSCT(scope.row)"
            >
              <template #reference>
                <el-button text class="row-action-danger">
                  <el-icon><Delete /></el-icon><span>Drop</span>
                </el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
        <template #empty>
          <el-empty description="No courses selected yet" :image-size="80">
            <el-button type="primary" class="press" @click="$router.push('/studentSelectCourse')">
              Browse the catalog
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
import { getCurrentInstance, reactive, ref, toRefs } from 'vue'
import { Delete, Plus } from '@element-plus/icons-vue'

const { proxy } = getCurrentInstance()
const term = ref(sessionStorage.getItem('currentTerm'))

const state = reactive({
  tableData: null,
  pageSize: 10,
  total: null,
  tmpList: null,
})

const { tableData, pageSize, total, tmpList } = toRefs(state)

const sid = sessionStorage.getItem('sid')
const termStr = sessionStorage.getItem('currentTerm')
axios.get('/SCT/findBySid/' + sid + '/' + termStr).then(function (resp) {
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
  const sct = { cid, tid, sid, term: termStr }

  axios
    .post('/SCT/deleteBySCT', sct)
    .then(function (resp) {
      if (resp.data === true) {
        proxy.$message({ message: 'Course dropped', type: 'success' })
        // refresh inline
        axios.get('/SCT/findBySid/' + sid + '/' + termStr).then((r) => {
          state.tmpList = r.data
          state.total = r.data.length
          state.tableData = r.data.slice(0, state.pageSize)
        })
      } else {
        proxy.$message({ message: 'Could not drop the course. Please try again.', type: 'error' })
      }
    })
    .catch(function () {
      proxy.$message({ message: 'Network error. Please try again.', type: 'error' })
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
.cell-mono--muted {
  color: var(--ink-muted);
}
.row-action-danger {
  color: var(--ink-muted) !important;
}
.row-action-danger:hover {
  color: var(--danger) !important;
  background: var(--danger-soft) !important;
}
</style>
