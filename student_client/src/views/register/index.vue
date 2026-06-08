<template>
  <div class="register-page">
    <div class="mesh-hero" aria-hidden="true"></div>

    <div class="register-split">
      <section class="register-feature" aria-hidden="true">
        <div class="register-feature__inner">
          <p class="register-feature__eyebrow">Create your account</p>
          <h1 class="register-feature__heading">
            {{ pageTitle === 'Teacher Register' ? 'Join the faculty.' : 'Join the classroom.' }}
          </h1>
          <p class="register-feature__sub">
            Set up your account in a minute. You'll get an ID you can use to sign in right away.
          </p>

          <div class="register-feature__cards">
            <div class="tint-card tint-card--sky">
              <div class="tint-card__icon"><el-icon><Notebook /></el-icon></div>
              <p class="tint-card__title">Course catalog</p>
              <p class="tint-card__desc">Browse the full curriculum by term and credit.</p>
            </div>
            <div class="tint-card tint-card--mint">
              <div class="tint-card__icon"><el-icon><DataAnalysis /></el-icon></div>
              <p class="tint-card__title">Grade history</p>
              <p class="tint-card__desc">All your grades, organized by term.</p>
            </div>
            <div class="tint-card tint-card--lavender">
              <div class="tint-card__icon"><el-icon><Calendar /></el-icon></div>
              <p class="tint-card__title">Smart scheduling</p>
              <p class="tint-card__desc">Plan your semester with a click.</p>
            </div>
          </div>
        </div>
      </section>

      <section class="register-form-section">
        <div class="register-form-inner">
          <div class="register-brand">
            <div class="register-brand__monogram">
              <el-icon><Reading /></el-icon>
            </div>
            <div class="register-brand__text">
              <span>NUIST · Course Selection</span>
              <span class="register-brand__tagline">Nanjing University of Information Science and Technology</span>
            </div>
          </div>

          <el-card class="register-module" shadow="never">
            <template #header>
              <div class="register-card-header">
                <div class="register-card-header__icon">
                  <el-icon><UserFilled /></el-icon>
                </div>
                <div>
                  <p class="register-title">{{ pageTitle }}</p>
                  <p class="register-subtitle">
                    Create an account and use the generated ID to sign in.
                  </p>
                </div>
              </div>
            </template>

            <el-form
              :model="ruleForm"
              :rules="rules"
              ref="ruleFormRef"
              label-position="top"
              class="register-form"
              @submit.prevent="submitForm"
            >
              <el-form-item :label="nameLabel" prop="name">
                <el-input
                  v-model.trim="ruleForm.name"
                  :prefix-icon="User"
                  :placeholder="nameLabel"
                  size="large"
                />
              </el-form-item>
              <el-form-item label="Password" prop="password">
                <el-input
                  v-model="ruleForm.password"
                  type="password"
                  show-password
                  :prefix-icon="Lock"
                  placeholder="At least 3 characters"
                  size="large"
                />
              </el-form-item>
              <el-form-item label="Confirm password" prop="confirmPassword">
                <el-input
                  v-model="ruleForm.confirmPassword"
                  type="password"
                  show-password
                  :prefix-icon="Lock"
                  placeholder="Re-enter password"
                  size="large"
                />
              </el-form-item>

              <el-button
                type="primary"
                class="register-submit press"
                :loading="submitting"
                @click="submitForm"
              >
                Create account
              </el-button>
            </el-form>

            <div class="register-actions">
              <span>Already have an account?</span>
              <el-button text @click="goLogin">Sign in</el-button>
            </div>
          </el-card>

          <p class="register-footer">© NUIST · Course Selection System</p>
        </div>
      </section>
    </div>
  </div>
</template>

<script setup>
import { computed, getCurrentInstance, reactive, ref, watch } from 'vue'
import {
  Calendar,
  DataAnalysis,
  Lock,
  Notebook,
  Reading,
  User,
  UserFilled,
} from '@element-plus/icons-vue'
import { useRoute, useRouter } from 'vue-router'

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
    callback(new Error('Please confirm the password'))
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
    { required: true, message: 'Please enter a password', trigger: 'blur' },
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
      body: { tname: ruleForm.name, password: ruleForm.password },
      idLabel: 'Teacher ID',
    }
  }
  return {
    url: '/student/register',
    body: { sname: ruleForm.name, password: ruleForm.password },
    idLabel: 'Student ID',
  }
}

function submitForm() {
  ruleFormRef.value.validate((valid) => {
    if (!valid) return false
    const payload = buildPayload()
    submitting.value = true
    axios
      .post(payload.url, payload.body)
      .then((resp) => {
        if (resp.data && resp.data.id) {
          proxy
            .$alert(
              `Registration successful. Your ${payload.idLabel} is ${resp.data.id}.`,
              'Registration complete',
              { confirmButtonText: 'Back to sign in', type: 'success' },
            )
            .then(() => router.push('/login'))
        } else {
          proxy.$message({ message: resp.data?.message || 'Registration failed', type: 'error' })
        }
      })
      .catch(() => {
        proxy.$message({ message: 'Registration failed. Please try again later.', type: 'error' })
      })
      .finally(() => {
        submitting.value = false
      })
  })
}

function resetForm() {
  if (ruleFormRef.value) ruleFormRef.value.resetFields()
}

function goLogin() {
  router.push('/login')
}
</script>

<style scoped>
.register-split {
  position: relative;
  z-index: 1;
  min-height: 100vh;
  display: grid;
  grid-template-columns: 1fr 1.05fr;
}

.register-feature {
  display: grid;
  place-items: center;
  padding: 80px 64px;
  background: linear-gradient(180deg, rgba(224, 242, 254, 0.4), rgba(255, 255, 255, 0));
}

.register-feature__inner {
  max-width: 480px;
}

.register-feature__eyebrow {
  margin: 0 0 14px;
  color: var(--sky-700);
  font-size: 12px;
  font-weight: 600;
  letter-spacing: 0.1em;
  text-transform: uppercase;
}

.register-feature__heading {
  margin: 0 0 18px;
  color: var(--ink);
  font-size: 44px;
  font-weight: 600;
  letter-spacing: -0.03em;
  line-height: 1.05;
}

.register-feature__sub {
  margin: 0 0 36px;
  color: var(--ink-muted);
  font-size: 16px;
  line-height: 1.6;
}

.register-feature__cards {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.register-form-section {
  display: grid;
  place-items: center;
  padding: 64px 48px;
  background: var(--canvas-soft);
}

.register-form-inner {
  width: min(100%, 480px);
}

.register-brand {
  margin-bottom: 32px;
  display: none;
}

.register-form :deep(.el-form-item__label) {
  font-weight: 600;
  color: var(--ink);
  font-size: 13px;
  padding-bottom: 6px;
}

.register-form :deep(.el-input__wrapper) {
  height: 48px;
  font-size: 15px;
}

.register-footer {
  margin: 32px 0 0;
  text-align: center;
  color: var(--ink-subtle);
  font-size: 12.5px;
}

@media (max-width: 960px) {
  .register-split {
    grid-template-columns: 1fr;
  }
  .register-feature {
    display: none;
  }
  .register-form-section {
    padding: 56px 24px;
  }
  .register-brand {
    display: flex;
  }
}

@media (max-width: 640px) {
  .register-form-section {
    padding: 40px 16px;
  }
}
</style>
