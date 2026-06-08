<template>
  <div>
    <div class="page-header">
      <div>
        <p class="page-header__eyebrow">Admin · Courses</p>
        <h1 class="page-header__title">Add a new course</h1>
        <p class="page-header__subtitle">Create a new course in the catalog.</p>
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
          <el-input v-model="ruleForm.cname" placeholder="e.g. Machine Learning" />
        </el-form-item>
        <el-form-item label="Credits" prop="ccredit">
          <el-input-number
            v-model="ruleForm.ccredit"
            :min="1"
            :max="10"
            placeholder="Credit hours"
            style="width: 200px"
          />
        </el-form-item>
      </el-form>
      <div class="glass-bar">
        <el-button text @click="router.push('/queryCourse')">Cancel</el-button>
        <el-button @click="resetForm">Reset</el-button>
        <el-button type="primary" class="press" :loading="saving" @click="submitForm">
          Create course
        </el-button>
      </div>
    </el-card>
  </div>
</template>
<script setup>
import { getCurrentInstance, reactive, ref, toRefs } from 'vue'
import { ArrowLeft } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const { proxy } = getCurrentInstance()

const ruleFormRef = ref(null)
const saving = ref(false)

const state = reactive({
  ruleForm: {
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

function submitForm() {
  ruleFormRef.value.validate((valid) => {
    if (valid) {
      saving.value = true
      axios
        .post('/course/save', state.ruleForm)
        .then(function (resp) {
          if (resp.data === true) {
            proxy.$message({ message: 'Course created', type: 'success' })
            router.push('/queryCourse')
          } else {
            proxy.$message({ message: 'Could not create the course. Please check the database.', type: 'error' })
            saving.value = false
          }
        })
        .catch(function () {
          proxy.$message({ message: 'Network error. Please try again.', type: 'error' })
          saving.value = false
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
