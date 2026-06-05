<template>
  <div class="register-page">
    <el-container class="register-shell">
      <el-header class="register-header">
        <div class="register-brand">
          <el-icon><House /></el-icon>
          <span>Course Selection Management System</span>
        </div>
      </el-header>
      <el-main class="register-main">
        <el-card class="register-module" shadow="never">
          <template #header>
            <div class="register-card-header">
              <el-icon><UserFilled /></el-icon>
              <div>
                <p class="register-title">{{ pageTitle }}</p>
                <p class="register-subtitle">
                  Create an account and use the generated ID to login.
                </p>
              </div>
            </div>
          </template>
          <el-form
            :model="ruleForm"
            :rules="rules"
            ref="ruleFormRef"
            label-width="130px"
            class="demo-ruleForm"
          >
            <el-form-item :label="nameLabel" prop="name">
              <el-input v-model.trim="ruleForm.name" :prefix-icon="User"></el-input>
            </el-form-item>
            <el-form-item label="Password" prop="password">
              <el-input
                v-model="ruleForm.password"
                show-password
                placeholder="Please enter password"
                :prefix-icon="Lock"
              ></el-input>
            </el-form-item>
            <el-form-item label="Confirm" prop="confirmPassword">
              <el-input
                v-model="ruleForm.confirmPassword"
                show-password
                placeholder="Please confirm password"
                :prefix-icon="Lock"
              ></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" :loading="submitting" @click="submitForm"
                >Register</el-button
              >
              <el-button @click="resetForm">Reset</el-button>
              <el-button text @click="goLogin">Back to Login</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import { computed, getCurrentInstance, reactive, ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { House, Lock, User, UserFilled } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const { proxy } = getCurrentInstance()

const ruleFormRef = ref(null)
const submitting = ref(false)

const ruleForm = reactive({
  name: '',
  password: '',
  confirmPassword: '',
})

const registerType = computed(() => (route.meta.registerType === 'teacher' ? 'teacher' : 'student'))
const pageTitle = computed(() =>
  registerType.value === 'teacher' ? 'Teacher Register' : 'Student Register',
)
const nameLabel = computed(() =>
  registerType.value === 'teacher' ? 'Teacher Name' : 'Student Name',
)

const validateConfirmPassword = (rule, value, callback) => {
  if (!value) {
    callback(new Error('Please confirm password'))
  } else if (value !== ruleForm.password) {
    callback(new Error('The two passwords do not match'))
  } else {
    callback()
  }
}

const rules = reactive({
  name: [
    { required: true, message: 'Please enter a name', trigger: 'blur' },
    { min: 2, max: 30, message: 'Length must be 2 to 30 characters', trigger: 'blur' },
  ],
  password: [
    { required: true, message: 'Please enter password', trigger: 'blur' },
    { min: 3, max: 30, message: 'Length must be 3 to 30 characters', trigger: 'blur' },
  ],
  confirmPassword: [{ validator: validateConfirmPassword, trigger: 'blur' }],
})

watch(registerType, () => {
  resetForm()
})

function buildPayload() {
  if (registerType.value === 'teacher') {
    return {
      url: '/teacher/register',
      body: {
        tname: ruleForm.name,
        password: ruleForm.password,
      },
      idLabel: 'Teacher ID',
    }
  }

  return {
    url: '/student/register',
    body: {
      sname: ruleForm.name,
      password: ruleForm.password,
    },
    idLabel: 'Student ID',
  }
}

function submitForm() {
  ruleFormRef.value.validate((valid) => {
    if (!valid) {
      return false
    }

    const payload = buildPayload()
    submitting.value = true
    axios
      .post(payload.url, payload.body)
      .then(function (resp) {
        if (resp.data && resp.data.id) {
          proxy
            .$alert(
              'Registration successful. Your ' + payload.idLabel + ' is ' + resp.data.id + '.',
              'Registration Complete',
              {
                confirmButtonText: 'Back to Login',
                type: 'success',
              },
            )
            .then(function () {
              router.push('/login')
            })
        } else {
          proxy.$message({
            showClose: true,
            message: resp.data?.message || 'Registration failed',
            type: 'error',
          })
        }
      })
      .catch(function () {
        proxy.$message({
          showClose: true,
          message: 'Registration failed, please try again later',
          type: 'error',
        })
      })
      .finally(function () {
        submitting.value = false
      })
  })
}

function resetForm() {
  if (ruleFormRef.value) {
    ruleFormRef.value.resetFields()
  }
}

function goLogin() {
  router.push('/login')
}
</script>

<style scoped>
.register-page {
  min-height: 100vh;
  background:
    linear-gradient(90deg, rgba(15, 98, 254, 0.08), rgba(15, 98, 254, 0) 42%), var(--sms-surface);
}

.register-shell {
  min-height: 100vh;
}

.register-header {
  display: flex;
  align-items: center;
  height: 64px;
  border-bottom: 1px solid var(--sms-hairline);
  background: var(--sms-canvas);
}

.register-brand {
  display: flex;
  align-items: center;
  gap: 12px;
  color: var(--sms-ink);
  font-size: 18px;
  font-weight: 600;
}

.register-brand .el-icon {
  color: var(--sms-blue);
}

.register-main {
  display: grid;
  place-items: center;
  padding: 48px 20px;
}

.register-module {
  width: min(100%, 520px);
}

.register-card-header {
  display: flex;
  align-items: center;
  gap: 12px;
}

.register-card-header .el-icon {
  color: var(--sms-blue);
  font-size: 22px;
}

.register-title {
  margin: 0;
  color: var(--sms-ink);
  font-size: 22px;
  font-weight: 400;
  line-height: 1.25;
}

.register-subtitle {
  margin: 4px 0 0;
  color: var(--sms-ink-subtle);
  font-size: 13px;
}

.register-module :deep(.el-form) {
  max-width: none;
}

@media (max-width: 560px) {
  .register-brand {
    font-size: 15px;
  }

  .register-main {
    align-items: start;
    padding-top: 24px;
  }
}
</style>
