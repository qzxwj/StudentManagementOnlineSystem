<template>
  <div class="login-page">
    <div class="mesh-hero" aria-hidden="true"></div>

    <div class="login-split">
      <section class="login-feature" aria-hidden="true">
        <div class="login-feature__inner">
          <p class="login-feature__eyebrow">NUIST · TERM 2026–2027</p>
          <h1 class="login-feature__heading">Course Management<br />Online System</h1>
          <p class="login-feature__sub">
            One unified workspace for students to pick courses, teachers to offer them,
            and administrators to keep everything in orbit.
          </p>
          <ul class="login-feature__list">
            <li>
              <span class="login-feature__dot login-feature__dot--sky"></span>
              <div>
                <p class="login-feature__title">Browse &amp; select courses</p>
                <p class="login-feature__hint">Search by name, credit, or teacher.</p>
              </div>
            </li>
            <li>
              <span class="login-feature__dot login-feature__dot--mint"></span>
              <div>
                <p class="login-feature__title">Track grades in one place</p>
                <p class="login-feature__hint">Term-by-term academic history.</p>
              </div>
            </li>
            <li>
              <span class="login-feature__dot login-feature__dot--lavender"></span>
              <div>
                <p class="login-feature__title">Manage offerings with ease</p>
                <p class="login-feature__hint">Teachers offer, admins coordinate.</p>
              </div>
            </li>
          </ul>
          <p class="login-feature__footer mono-eyebrow">ORBIT SELECT · v3.0</p>
        </div>
      </section>

      <section class="login-form-section">
        <div class="login-form-inner">
          <div class="login-brand">
            <div class="login-brand__monogram">
              <el-icon><Reading /></el-icon>
            </div>
            <div class="login-brand__text">
              <span>NUIST · Course Selection</span>
              <span class="login-brand__tagline">Nanjing University · IST</span>
            </div>
          </div>

          <el-card class="login-module" shadow="never">
            <template #header>
              <div class="login-card-header">
                <div class="login-card-header__icon">
                  <el-icon><UserFilled /></el-icon>
                </div>
                <div>
                  <p class="login-title">Welcome back</p>
                  <p class="login-subtitle">Sign in with personal information to continue</p>
                </div>
              </div>
            </template>

            <el-form
              :model="ruleForm"
              :rules="rules"
              ref="ruleFormRef"
              label-position="top"
              class="login-form"
              @submit.prevent="submitForm"
            >
              <el-form-item label="User ID" prop="id">
                <el-input
                  v-model.number="ruleForm.id"
                  :prefix-icon="User"
                  placeholder="Enter your ID"
                  size="large"
                />
              </el-form-item>
              <el-form-item label="Password" prop="password">
                <el-input
                  v-model="ruleForm.password"
                  type="password"
                  show-password
                  :prefix-icon="Lock"
                  placeholder="Enter your password"
                  size="large"
                  @keyup.enter="submitForm"
                />
              </el-form-item>
              <el-form-item label="Role" prop="type">
                <el-radio-group v-model="ruleForm.type" class="login-role-group">
                  <el-radio-button label="student" value="student">Student</el-radio-button>
                  <el-radio-button label="teacher" value="teacher">Teacher</el-radio-button>
                  <el-radio-button label="admin" value="admin">Admin</el-radio-button>
                </el-radio-group>
              </el-form-item>

              <el-button
                type="primary"
                class="login-submit press"
                :loading="loading"
                @click="submitForm"
              >
                Sign in
              </el-button>
            </el-form>

            <div class="register-actions">
              <span>New to NUIST?</span>
              <el-button text @click="goRegister('student')">Student register</el-button>
              <span class="register-actions__sep">·</span>
              <el-button text @click="goRegister('teacher')">Teacher register</el-button>
            </div>
          </el-card>

          <p class="login-footer">© NUIST · Course Selection System</p>
        </div>
      </section>
    </div>
  </div>
</template>

<script setup>
import { getCurrentInstance, reactive, ref, toRefs } from 'vue'
import { Lock, Reading, User, UserFilled } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const { proxy } = getCurrentInstance()
const ruleFormRef = ref(null)
const loading = ref(false)

const state = reactive({
  ruleForm: {
    id: null,
    password: '',
    type: 'student',
  },
  rules: {
    id: [
      { required: true, message: 'Please enter your ID', trigger: 'blur' },
      { type: 'number', message: 'ID must be a number', trigger: 'blur' },
    ],
    password: [{ required: true, message: 'Please enter your password', trigger: 'blur' }],
    type: [{ required: true, message: 'Please select a role', trigger: 'change' }],
  },
})

const { ruleForm, rules } = toRefs(state)

function submitForm() {
  ruleFormRef.value.validate((valid) => {
    if (!valid) return false
    loading.value = true
    axios.get('/info/getCurrentTerm').then((resp) => {
      sessionStorage.setItem('currentTerm', resp.data)
    })
    axios.get('/info/getForbidCourseSelection').then((resp) => {
      sessionStorage.setItem('ForbidCourseSelection', resp.data)
    })

    if (state.ruleForm.type === 'admin' || state.ruleForm.type === 'teacher') {
      const form = { tid: state.ruleForm.id, password: state.ruleForm.password }
      axios
        .post('/teacher/login', form)
        .then((resp) => {
          if (resp.data === true) {
            return axios.get('/teacher/findById/' + state.ruleForm.id).then((r) => {
              const name = r.data.tname
              sessionStorage.setItem('token', 'true')
              sessionStorage.setItem('type', state.ruleForm.type)
              sessionStorage.setItem('name', name)
              sessionStorage.setItem('tid', r.data.tid)

              if (state.ruleForm.type === 'admin' && name === 'admin') {
                proxy.$message({ message: `Welcome back, ${name}`, type: 'success' })
                router.push('/admin')
              } else if (state.ruleForm.type === 'teacher' && name !== 'admin') {
                proxy.$message({ message: `Welcome back, ${name}`, type: 'success' })
                router.push('/teacher')
              } else {
                proxy.$message({ message: 'Invalid credentials for this role.', type: 'error' })
                loading.value = false
              }
            })
          } else {
            proxy.$message({ message: 'Incorrect password. Please try again.', type: 'error' })
            loading.value = false
          }
        })
        .catch(() => {
          proxy.$message({ message: 'Network error. Please try again.', type: 'error' })
          loading.value = false
        })
    } else if (state.ruleForm.type === 'student') {
      const form = { sid: state.ruleForm.id, password: state.ruleForm.password }
      axios
        .post('/student/login', form)
        .then((resp) => {
          if (resp.data === true) {
            return axios.get('/student/findById/' + state.ruleForm.id).then((r) => {
              const name = r.data.sname
              sessionStorage.setItem('token', 'true')
              sessionStorage.setItem('type', 'student')
              sessionStorage.setItem('name', name)
              sessionStorage.setItem('sid', r.data.sid)
              proxy.$message({ message: `Welcome back, ${name}`, type: 'success' })
              router.push('/student')
            })
          } else {
            proxy.$message({ message: 'Incorrect password. Please try again.', type: 'error' })
            loading.value = false
          }
        })
        .catch(() => {
          proxy.$message({ message: 'Network error. Please try again.', type: 'error' })
          loading.value = false
        })
    }
  })
}

function goRegister(type) {
  router.push(type === 'teacher' ? '/teacherRegister' : '/studentRegister')
}
</script>

<style scoped>
.login-split {
  position: relative;
  z-index: 1;
  min-height: 100vh;
  display: grid;
  grid-template-columns: 1.1fr 1fr;
}

.login-feature {
  display: grid;
  place-items: center;
  padding: 80px 64px;
  position: relative;
}

.login-feature__inner {
  max-width: 480px;
  position: relative;
}

.login-feature__eyebrow {
  margin: 0 0 20px;
  color: var(--color-ink-subtle);
  font-family: var(--font-mono);
  font-size: 11px;
  font-weight: 500;
  letter-spacing: var(--tracking-eyebrow);
  text-transform: uppercase;
}

.login-feature__heading {
  margin: 0 0 20px;
  font-family: var(--font-display);
  font-size: 56px;
  font-weight: 600;
  letter-spacing: var(--tracking-display);
  line-height: 1.02;
  color: var(--color-ink);
  background: linear-gradient(
    180deg,
    var(--color-ink) 0%,
    var(--color-accent-twilight) 100%
  );
  -webkit-background-clip: text;
  background-clip: text;
  -webkit-text-fill-color: transparent;
}

.login-feature__sub {
  margin: 0 0 40px;
  color: var(--color-ink-muted);
  font-size: 16px;
  line-height: 1.6;
  max-width: 440px;
}

.login-feature__list {
  list-style: none;
  margin: 0 0 40px;
  padding: 0;
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.login-feature__list li {
  display: flex;
  align-items: flex-start;
  gap: 14px;
  padding: 16px 18px;
  background: var(--color-surface-glass);
  border: 1px solid var(--color-hairline);
  backdrop-filter: blur(20px);
  -webkit-backdrop-filter: blur(20px);
  border-radius: var(--radius-md);
  box-shadow: var(--shadow-inset-top);
  transition: border-color var(--duration-base) var(--ease-standard);
}

.login-feature__list li:hover {
  border-color: var(--color-hairline-strong);
}

.login-feature__dot {
  width: 8px;
  height: 8px;
  border-radius: 50%;
  margin-top: 8px;
  flex-shrink: 0;
}

.login-feature__dot--sky {
  background: var(--color-primary);
  box-shadow: 0 0 0 4px rgba(94, 106, 210, 0.22);
}

.login-feature__dot--mint {
  background: var(--color-status-open);
  box-shadow: 0 0 0 4px rgba(159, 201, 162, 0.22);
}

.login-feature__dot--lavender {
  background: var(--color-accent-twilight);
  box-shadow: 0 0 0 4px rgba(196, 181, 253, 0.22);
}

.login-feature__title {
  margin: 0;
  color: var(--color-ink);
  font-size: 14.5px;
  font-weight: 600;
  letter-spacing: -0.005em;
}

.login-feature__hint {
  margin: 2px 0 0;
  color: var(--color-ink-subtle);
  font-size: 13px;
}

.login-feature__footer {
  margin: 0;
  color: var(--color-ink-tertiary);
}

.login-form-section {
  display: grid;
  place-items: center;
  padding: 64px 48px;
  background: var(--color-canvas-elevated);
  border-left: 1px solid var(--color-hairline);
  position: relative;
}

.login-form-inner {
  width: min(100%, 460px);
}

.login-brand {
  margin-bottom: 32px;
  display: none;
}

.login-form :deep(.el-form-item__label) {
  font-weight: 500;
  color: var(--color-ink-muted);
  font-size: 12px;
  letter-spacing: 0.04em;
  text-transform: uppercase;
  font-family: var(--font-mono);
  padding-bottom: 6px;
}

.login-form :deep(.el-input__wrapper) {
  height: 48px;
  font-size: 15px;
}

.login-role-group {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  gap: 8px;
  width: 100%;
}

.login-role-group :deep(.el-radio-button) {
  margin-right: 0;
  flex: 1;
}

.login-role-group :deep(.el-radio-button__inner) {
  width: 100%;
  border-radius: var(--radius-md) !important;
  border: 1px solid var(--color-hairline) !important;
  background: var(--color-surface-1) !important;
  color: var(--color-ink-subtle);
  font-weight: 500;
  box-shadow: none !important;
  padding: 12px 14px;
}

.login-role-group
  :deep(.el-radio-button__original-radio:checked + .el-radio-button__inner) {
  background: var(--color-primary-soft) !important;
  color: var(--color-primary-hover) !important;
  border-color: var(--color-primary) !important;
  box-shadow:
    0 0 0 1px var(--color-primary) inset,
    var(--shadow-glow-sm) !important;
  font-weight: 600;
}

.register-actions__sep {
  color: var(--color-ink-tertiary);
}

.login-footer {
  margin: 32px 0 0;
  text-align: center;
  color: var(--color-ink-tertiary);
  font-family: var(--font-mono);
  font-size: 11px;
  letter-spacing: var(--tracking-eyebrow);
  text-transform: uppercase;
}

@media (max-width: 1100px) {
  .login-feature {
    padding: 64px 48px;
  }
  .login-feature__heading {
    font-size: 44px;
  }
}

@media (max-width: 960px) {
  .login-split {
    grid-template-columns: 1fr;
  }
  .login-feature {
    display: none;
  }
  .login-form-section {
    padding: 56px 24px;
    border-left: 0;
  }
  .login-brand {
    display: flex;
  }
}

@media (max-width: 640px) {
  .login-form-section {
    padding: 40px 16px;
  }
  .login-role-group {
    grid-template-columns: 1fr;
  }
}
</style>
