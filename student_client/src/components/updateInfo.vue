<template>
  <div class="app-page">
    <div class="page-header">
      <div>
        <p class="page-header__eyebrow">Profile</p>
        <h1 class="page-header__title">Account settings</h1>
        <p class="page-header__subtitle">Update your display name and password.</p>
      </div>
    </div>

    <el-card class="app-panel edit-panel">
      <el-form
        :model="ruleForm"
        status-icon
        :rules="rules"
        ref="ruleFormRef"
        label-position="top"
        class="edit-form"
      >
        <div class="edit-form__row">
          <el-form-item label="Display name" prop="name">
            <el-input
              v-model.trim="ruleForm.name"
              :prefix-icon="User"
              placeholder="Enter your name"
            />
          </el-form-item>
        </div>
        <div class="edit-form__row edit-form__row--split">
          <el-form-item label="New password" prop="pass">
            <el-input
              v-model="ruleForm.pass"
              type="password"
              show-password
              autocomplete="new-password"
              placeholder="At least 3 characters"
              :prefix-icon="Lock"
            />
          </el-form-item>
          <el-form-item label="Confirm password" prop="checkPass">
            <el-input
              v-model="ruleForm.checkPass"
              type="password"
              show-password
              autocomplete="new-password"
              placeholder="Re-enter password"
              :prefix-icon="Lock"
            />
          </el-form-item>
        </div>
      </el-form>
      <div class="glass-bar">
        <el-button text @click="router.push('/' + userType + 'Home')">Cancel</el-button>
        <el-button @click="resetForm">Reset</el-button>
        <el-button type="primary" :loading="saving" @click="submitForm">Save changes</el-button>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { getCurrentInstance, onMounted, reactive, ref } from 'vue'
import { Lock, User } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const { proxy } = getCurrentInstance()
const ruleFormRef = ref(null)
const saving = ref(false)
const userType = sessionStorage.getItem('type') || 'student'

const ruleForm = reactive({
  pass: '',
  checkPass: '',
  name: sessionStorage.getItem('name') || '',
})

const validatePass = (rule, value, callback) => {
  if (!value) {
    callback(new Error('Please enter a new password'))
  } else if (value.length < 3) {
    callback(new Error('Password must be at least 3 characters'))
  } else {
    if (ruleForm.checkPass !== '') {
      ruleFormRef.value.validateField('checkPass')
    }
    callback()
  }
}

const validatePass2 = (rule, value, callback) => {
  if (!value) {
    callback(new Error('Please confirm the password'))
  } else if (value !== ruleForm.pass) {
    callback(new Error('Passwords do not match'))
  } else {
    callback()
  }
}

const rules = reactive({
  pass: [{ validator: validatePass, trigger: 'blur' }],
  checkPass: [{ validator: validatePass2, trigger: 'blur' }],
  name: [{ required: true, message: 'Name cannot be empty', trigger: 'blur' }],
})

onMounted(() => {
  ruleForm.name = sessionStorage.getItem('name') || ''
})

function submitForm() {
  ruleFormRef.value.validate((valid) => {
    if (!valid) return false
    sessionStorage.setItem('name', ruleForm.name)
    const type = userType
    let form = null
    let ss = null
    if (type === 'student') {
      ss = 'Student'
      form = {
        sid: sessionStorage.getItem('sid'),
        sname: ruleForm.name,
        password: ruleForm.pass,
      }
    } else {
      ss = 'Teacher'
      form = {
        tid: sessionStorage.getItem('tid'),
        tname: ruleForm.name,
        password: ruleForm.pass,
      }
    }
    saving.value = true
    axios
      .post('/' + type + '/update' + ss, form)
      .then(function (resp) {
        if (resp.data === true) {
          proxy.$message({ message: 'Profile updated', type: 'success' })
          router.push('/' + type + 'Home')
        } else {
          proxy.$message({ message: 'Update failed. Please contact the administrator.', type: 'error' })
        }
      })
      .catch(function () {
        proxy.$message({ message: 'Network error. Please try again.', type: 'error' })
      })
      .finally(function () {
        saving.value = false
      })
  })
}

function resetForm() {
  ruleFormRef.value.resetFields()
}
</script>

<style scoped>
.page-header__eyebrow {
  margin: 0 0 6px;
  color: var(--sky-700);
  font-size: 11.5px;
  font-weight: 600;
  letter-spacing: 0.08em;
  text-transform: uppercase;
}

.edit-panel {
  padding: 32px;
}

.edit-form {
  max-width: 720px;
}

.edit-form__row + .edit-form__row {
  margin-top: 8px;
}

.edit-form__row--split {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 18px;
}

@media (max-width: 640px) {
  .edit-form__row--split {
    grid-template-columns: 1fr;
  }
}
</style>
