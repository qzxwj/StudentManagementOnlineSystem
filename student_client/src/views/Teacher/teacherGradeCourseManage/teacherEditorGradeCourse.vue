<template>
  <div>
    <div class="page-header">
      <div>
        <p class="page-header__eyebrow">Teacher · Grades</p>
        <h1 class="page-header__title">Edit grade</h1>
        <p class="page-header__subtitle">Adjust the recorded grade for this student.</p>
      </div>
      <div class="page-header__actions">
        <el-button @click="router.back()">
          <el-icon><ArrowLeft /></el-icon><span>Back</span>
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
        <el-form-item label="Course name">
          <el-input v-model="ruleForm.cname" :value="ruleForm.cname" disabled />
        </el-form-item>
        <el-form-item label="Teacher name">
          <el-input v-model="ruleForm.tname" :value="ruleForm.tname" disabled />
        </el-form-item>
        <el-form-item label="Student name">
          <el-input v-model="ruleForm.sname" :value="ruleForm.sname" disabled />
        </el-form-item>
        <el-form-item label="Grade (0 – 100)" prop="grade">
          <el-input-number
            v-model="ruleForm.grade"
            :value="ruleForm.grade"
            :min="0"
            :max="100"
            style="width: 200px"
          />
        </el-form-item>
      </el-form>
      <div class="glass-bar">
        <el-button text @click="router.back()">Cancel</el-button>
        <el-button @click="resetForm">Reset</el-button>
        <el-button type="primary" class="press" @click="submitForm">Save changes</el-button>
      </div>
    </el-card>
  </div>
</template>
<script setup>
import { getCurrentInstance, reactive, ref } from 'vue'
import { ArrowLeft } from '@element-plus/icons-vue'
import { useRoute, useRouter } from 'vue-router'

const router = useRouter()
const route = useRoute()
const { proxy } = getCurrentInstance()
const ruleFormRef = ref(null)

const checkGrade = (rule, value, callback) => {
  if (value === null || value === undefined || value === '') {
    return callback(new Error('Please enter a grade'))
  }
  if (!Number.isInteger(Number(value))) {
    return callback(new Error('Grade must be a number'))
  }
  if (value > 100 || value < 0) {
    return callback(new Error('Grade must be between 0 and 100'))
  }
  callback()
}

const ruleForm = reactive({
  cid: null,
  cname: null,
  grade: null,
  sid: null,
  sname: null,
  tid: null,
  tname: null,
  term: null,
})

const rules = reactive({
  grade: [
    { required: true, message: 'Please enter a grade', trigger: 'change' },
    { type: 'number', message: 'Grade must be a number', trigger: 'change' },
    { validator: checkGrade, trigger: 'blur' },
  ],
})

ruleForm.cid = route.query.cid
ruleForm.tid = route.query.tid
ruleForm.sid = route.query.sid
ruleForm.term = route.query.term
axios
  .get('/SCT/findById/' + ruleForm.sid + '/' + ruleForm.cid + '/' + ruleForm.tid + '/' + ruleForm.term)
  .then(function (resp) {
    Object.assign(ruleForm, resp.data)
  })

function submitForm() {
  ruleFormRef.value.validate((valid) => {
    if (valid) {
      const { sid, cid, tid, term, grade } = ruleForm
      axios
        .get('/SCT/updateById/' + sid + '/' + cid + '/' + tid + '/' + term + '/' + grade)
        .then(function (resp) {
          if (resp.data === true) {
            proxy.$message({ message: 'Grade updated', type: 'success' })
          } else {
            proxy.$message({ message: 'Update failed. Please contact the administrator.', type: 'error' })
          }
          router.push('/teacherQueryGradeCourseManage')
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
