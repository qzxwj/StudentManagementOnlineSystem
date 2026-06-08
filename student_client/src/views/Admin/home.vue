<template>
  <div class="app-page home-page">
    <section class="home-hero">
      <div class="home-hero__mesh" aria-hidden="true"></div>
      <div class="home-hero__content">
        <span class="home-hero__eyebrow">
          <el-icon><Setting /></el-icon>Admin workspace
        </span>
        <h1 class="home-hero__heading">Welcome back, {{ name }}</h1>
        <p class="home-hero__subheading">
          Coordinate the {{ term || 'current term' }} course offerings, manage user records, and
          keep the registrar running smoothly — all in one place.
        </p>
      </div>
      <div class="home-hero__cta">
        <div class="home-hero__cta-meta">
          <el-icon><Calendar /></el-icon>
          <span>Current term · <strong>{{ term || '—' }}</strong></span>
        </div>
        <el-button type="primary" class="press" @click="$router.push('/addCourse')">
          <el-icon><Plus /></el-icon><span>Add a new course</span>
        </el-button>
      </div>
    </section>

    <section class="ink-band">
      <div class="ink-band__grid" style="grid-template-columns: repeat(4, 1fr)">
        <div class="ink-band__item">
          <p class="ink-band__label">Students</p>
          <p class="ink-band__value">{{ stats.students }}</p>
          <p class="ink-band__hint">Total enrolled learners</p>
        </div>
        <div class="ink-band__item">
          <p class="ink-band__label">Teachers</p>
          <p class="ink-band__value">{{ stats.teachers }}</p>
          <p class="ink-band__hint">Active faculty accounts</p>
        </div>
        <div class="ink-band__item">
          <p class="ink-band__label">Courses</p>
          <p class="ink-band__value">{{ stats.courses }}</p>
          <p class="ink-band__hint">In the curriculum</p>
        </div>
        <div class="ink-band__item">
          <p class="ink-band__label">Term</p>
          <p class="ink-band__value mono">{{ term || '—' }}</p>
          <p class="ink-band__hint">Active registration window</p>
        </div>
      </div>
    </section>

    <section>
      <div class="section-header">
        <div>
          <h2 class="section-header__title">Quick actions</h2>
          <p class="section-header__hint">Jump straight into common admin tasks.</p>
        </div>
      </div>
      <div class="quick-grid">
        <div class="tint-card tint-card--sky" @click="$router.push('/studentList')">
          <el-icon class="tint-card__arrow"><ArrowRight /></el-icon>
          <div class="tint-card__icon"><el-icon><User /></el-icon></div>
          <p class="tint-card__title">Manage students</p>
          <p class="tint-card__desc">Add, edit, or remove student records.</p>
        </div>
        <div class="tint-card tint-card--mint" @click="$router.push('/queryCourse')">
          <el-icon class="tint-card__arrow"><ArrowRight /></el-icon>
          <div class="tint-card__icon"><el-icon><Reading /></el-icon></div>
          <p class="tint-card__title">Course catalog</p>
          <p class="tint-card__desc">Browse and edit the full course list.</p>
        </div>
        <div class="tint-card tint-card--lavender" @click="$router.push('/queryCourseTeacher')">
          <el-icon class="tint-card__arrow"><ArrowRight /></el-icon>
          <div class="tint-card__icon"><el-icon><Connection /></el-icon></div>
          <p class="tint-card__title">Course offerings</p>
          <p class="tint-card__desc">Coordinate which teachers teach which courses.</p>
        </div>
        <div class="tint-card tint-card--peach" @click="$router.push('/queryGradeCourse')">
          <el-icon class="tint-card__arrow"><ArrowRight /></el-icon>
          <div class="tint-card__icon"><el-icon><DataAnalysis /></el-icon></div>
          <p class="tint-card__title">Review grades</p>
          <p class="tint-card__desc">Search and edit recorded student grades.</p>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import {
  ArrowRight,
  Calendar,
  Connection,
  DataAnalysis,
  Plus,
  Reading,
  Setting,
  User,
} from '@element-plus/icons-vue'

const name = ref(sessionStorage.getItem('name') || 'Admin')
const term = ref(sessionStorage.getItem('currentTerm') || '')

const stats = reactive({
  students: '—',
  teachers: '—',
  courses: '—',
})

function safeGet(url, cb) {
  axios
    .get(url)
    .then((r) => cb(r.data))
    .catch(() => {})
}

function safePost(url, body, cb) {
  axios
    .post(url, body || {})
    .then((r) => cb(r.data))
    .catch(() => {})
}

onMounted(() => {
  safeGet('/student/getLength', (n) => (stats.students = Number(n) || 0))
  // No dedicated count endpoints for teachers / courses; use findBySearch with empty body to total.
  // Exclude the admin account (lives in the t table, identified by tname === 'admin').
  safePost('/teacher/findBySearch', {}, (data) => {
    if (Array.isArray(data)) stats.teachers = data.filter((t) => t.tname !== 'admin').length
  })
  safePost('/course/findBySearch', {}, (data) => {
    if (Array.isArray(data)) stats.courses = data.length
  })
})
</script>

<style scoped>
.home-page {
  gap: 28px;
}

.ink-band__value.mono {
  font-family: 'JetBrains Mono', ui-monospace, SFMono-Regular, monospace;
  font-size: 26px;
  font-weight: 600;
}

.quick-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
  gap: 16px;
  margin-top: 4px;
}
</style>
