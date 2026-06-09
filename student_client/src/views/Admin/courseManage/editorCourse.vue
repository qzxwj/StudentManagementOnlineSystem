<template>
  <div>
    <div class="page-header">
      <div>
        <p class="page-header__eyebrow">Admin · Courses</p>
        <h1 class="page-header__title">Edit Course</h1>
        <p class="page-header__subtitle">Update the course name or credit hours.</p>
      </div>
      <div class="page-header__actions">
        <el-button @click="router.push('/queryCourse')">
          <el-icon><ArrowLeft /></el-icon><span>Back to catalog</span>
        </el-button>
      </div>
    </div>

    <el-card class="app-panel edit-panel">
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleFormRef"
        label-position="top"
        class="edit-form"
      >
        <el-form-item label="Course name" prop="cname">
          <el-input v-model="ruleForm.cname" :value="ruleForm.cname" />
        </el-form-item>
        <el-form-item label="Credits" prop="ccredit">
          <el-input-number
            v-model="ruleForm.ccredit"
            :value="ruleForm.ccredit"
            :min="1"
            :max="10"
            style="width: 200px"
          />
        </el-form-item>
      </el-form>
      <div class="glass-bar">
        <el-button text @click="router.push('/queryCourse')">Cancel</el-button>
        <el-button @click="resetForm">Reset</el-button>
        <el-button type="primary" class="press" @click="submitForm">Save changes</el-button>
      </div>
    </el-card>
  </div>
</template>
<script setup>
import { getCurrentInstance, reactive, ref, toRefs } from 'vue'
import { ArrowLeft } from '@element-plus/icons-vue'
import { useRoute, useRouter } from 'vue-router'

const router = useRouter()
const route = useRoute()
const { proxy } = getCurrentInstance()

const ruleFormRef = ref(null)

const state = reactive({
  ruleForm: {
    cid: null,
    cname: null,
    ccredit: null,
  },
  rules: {
    cname: [{ required: true, message: 'Please enter a course name', trigger: 'blur' }],
    ccredit: [
      { required: true, message: 'Please enter credits', trigger: 'change' },
      { type: 'number', message: 'Credits must be a number', trigger: 'change' },
    ],
  },
})

const { ruleForm, rules } = toRefs(state)

if (route.query.cid === undefined) {
  state.ruleForm.cid = 1
} else {
  state.ruleForm.cid = route.query.cid
}
axios.get('/course/findById/' + state.ruleForm.cid).then(function (resp) {
  Object.assign(state.ruleForm, resp.data[0])
})

function submitForm() {
  ruleFormRef.value.validate((valid) => {
    if (valid) {
      axios.post('/course/updateCourse', state.ruleForm).then(function (resp) {
        if (resp.data === true) {
          proxy.$message({ message: 'Course updated', type: 'success' })
        } else {
          proxy.$message({ message: 'Update failed. Please contact the administrator.', type: 'error' })
        }
        router.push('/queryCourse')
      })
    } else {
      return false
    }
  })
}

function resetForm() {
  ruleFormRef.value.resetFields()
}
</script>

<style scoped>
.edit-panel {
  padding: 32px;
}
.edit-form {
  max-width: 720px;
}
</style>
