<template>
  <div class="login-page">
    <el-container class="login-shell">
      <el-header class="login-header">
        <div class="login-brand">
          <el-icon><House /></el-icon>
          <span>Course Selection Management System</span>
        </div>
      </el-header>
      <el-main class="login-main">
        <el-card class="login-module" shadow="never">
          <template #header>
            <div class="login-card-header">
              <el-icon><OfficeBuilding /></el-icon>
              <div>
                <p class="login-title">Login</p>
                <p class="login-subtitle">Enter your account information to continue.</p>
              </div>
            </div>
          </template>
          <div>
            <el-form
              :model="ruleForm"
              :rules="rules"
              ref="ruleFormRef"
              label-width="100px"
              class="demo-ruleForm"
            >
              <el-form-item label="User ID" prop="id">
                <el-input v-model.number="ruleForm.id" :prefix-icon="Lollipop"></el-input>
              </el-form-item>
              <el-form-item label="Password" prop="password">
                <el-input
                  v-model="ruleForm.password"
                  placeholder="Please enter password"
                  show-password
                  :prefix-icon="IceCreamRound"
                ></el-input>
              </el-form-item>
              <el-form-item label="User Type" prop="type">
                <el-radio-group v-model="ruleForm.type">
                  <el-radio label="student" value="student">Student</el-radio>
                  <el-radio label="teacher" value="teacher">Teacher</el-radio>
                  <el-radio label="admin" value="admin">Admin</el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="submitForm">Login</el-button>
                <el-button @click="resetForm">Reset</el-button>
              </el-form-item>
            </el-form>
            <div class="register-actions">
              <el-button text @click="goRegister('student')">Student Register</el-button>
              <el-divider direction="vertical"></el-divider>
              <el-button text @click="goRegister('teacher')">Teacher Register</el-button>
            </div>
          </div>
        </el-card>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import { getCurrentInstance, reactive, ref, toRefs } from 'vue'
import { House, IceCreamRound, Lollipop, OfficeBuilding } from '@element-plus/icons-vue'

import { useRouter } from 'vue-router'

const router = useRouter()

const { proxy } = getCurrentInstance()

const ruleFormRef = ref(null)

const state = reactive({
  ruleForm: {
    id: null,
    password: null,
    type: null,
  },
  rules: {
    id: [
      { required: true, message: 'Please enterUser ID', trigger: 'blur' },
      { type: 'number', message: 'Please enter a number', trigger: 'blur' },
    ],
    password: [{ required: true, message: 'Please enter Password', trigger: 'blur' }],
    type: [{ required: true, message: 'Please select', trigger: 'change' }],
  },
})

const { ruleForm, rules } = toRefs(state)

function submitForm(formName) {
  ruleFormRef.value.validate((valid) => {
    if (valid) {
      let check = false
      let name = null

      axios.get('/info/getCurrentTerm').then(function (resp) {
        sessionStorage.setItem('currentTerm', resp.data)
      })

      axios.get('/info/getForbidCourseSelection').then(function (resp) {
        sessionStorage.setItem('ForbidCourseSelection', resp.data)
      })

      if (state.ruleForm.type === 'admin' || state.ruleForm.type === 'teacher') {
        let form = { tid: state.ruleForm.id, password: state.ruleForm.password }
        axios.post('/teacher/login', form).then(function (resp) {
          check = resp.data
          if (check === true) {
            axios.get('/teacher/findById/' + state.ruleForm.id).then(function (resp) {
              name = resp.data.tname

              sessionStorage.setItem('token', 'true')
              sessionStorage.setItem('type', state.ruleForm.type)
              sessionStorage.setItem('name', name)
              sessionStorage.setItem('tid', resp.data.tid)

              if (state.ruleForm.type === 'admin' && name === 'admin') {
                proxy.$message({
                  showClose: true,
                  message: 'Login page,Welcome ' + name + '!',
                  type: 'success',
                })
                router.push('/admin')
              } else if (state.ruleForm.type === 'teacher' && name !== 'admin') {
                proxy.$message({
                  showClose: true,
                  message: 'Login page,Welcome ' + name + '!',
                  type: 'success',
                })
                router.push('/teacher')
              } else {
                proxy.$message({
                  showClose: true,
                  message: 'admin Login page,Not logged in page',
                  type: 'error',
                })
              }
            })
          } else {
            proxy.$message({
              showClose: true,
              message: 'Login page,Password',
              type: 'error',
            })
          }
        })
      } else if (state.ruleForm.type === 'student') {
        let form = { sid: state.ruleForm.id, password: state.ruleForm.password }
        axios.post('/student/login', form).then(function (resp) {
          check = resp.data
          if (check === true) {
            axios.get('/student/findById/' + state.ruleForm.id).then(function (resp) {
              name = resp.data.sname

              sessionStorage.setItem('token', 'true')
              sessionStorage.setItem('type', state.ruleForm.type)
              sessionStorage.setItem('name', name)
              sessionStorage.setItem('sid', resp.data.sid)

              proxy.$message({
                showClose: true,
                message: 'Login page,Welcome ' + name + '!',
                type: 'success',
              })

              // 3. Route redirect
              router.push({
                path: '/' + state.ruleForm.type,
                query: {},
              })
            })
          } else {
            proxy.$message({
              showClose: true,
              message: 'Password error,Contact the administrator',
              type: 'error',
            })
          }
        })
      } else {
      }
    } else {
      return false
    }
  })
}

function resetForm(formName) {
  ruleFormRef.value.resetFields()
}

function goRegister(type) {
  router.push(type === 'teacher' ? '/teacherRegister' : '/studentRegister')
}
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  background:
    linear-gradient(90deg, rgba(15, 98, 254, 0.08), rgba(15, 98, 254, 0) 42%), var(--sms-surface);
}

.login-shell {
  min-height: 100vh;
}

.login-header {
  display: flex;
  align-items: center;
  height: 64px;
  border-bottom: 1px solid var(--sms-hairline);
  background: var(--sms-canvas);
}

.login-brand {
  display: flex;
  align-items: center;
  gap: 12px;
  color: var(--sms-ink);
  font-size: 18px;
  font-weight: 600;
}

.login-brand .el-icon {
  color: var(--sms-blue);
}

.login-main {
  display: grid;
  place-items: center;
  padding: 48px 20px;
}

.login-module {
  width: min(100%, 460px);
}

.login-card-header {
  display: flex;
  align-items: center;
  gap: 12px;
}

.login-card-header .el-icon {
  color: var(--sms-blue);
  font-size: 22px;
}

.login-title {
  margin: 0;
  color: var(--sms-ink);
  font-size: 22px;
  font-weight: 400;
  line-height: 1.25;
}

.login-subtitle {
  margin: 4px 0 0;
  color: var(--sms-ink-subtle);
  font-size: 13px;
}

.login-module :deep(.el-form) {
  max-width: none;
}

.register-actions {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-top: 8px;
  padding-top: 16px;
  border-top: 1px solid var(--sms-hairline);
}

@media (max-width: 560px) {
  .login-brand {
    font-size: 15px;
  }

  .login-main {
    align-items: start;
    padding-top: 24px;
  }
}
</style>
