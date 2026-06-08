<template>
  <div class="app-page home-page">
    <section class="home-hero">
      <div class="home-hero__mesh" aria-hidden="true"></div>
      <div class="home-hero__content">
        <span class="home-hero__eyebrow">
          <el-icon><UserFilled /></el-icon>Student workspace
        </span>
        <h1 class="home-hero__heading">Welcome back, {{ name }}</h1>
        <p class="home-hero__subheading">
          Build your {{ term || 'current term' }} schedule, drop what doesn't fit, and keep an eye
          on your grades as the term progresses.
        </p>
      </div>
      <div class="home-hero__cta">
        <div class="home-hero__cta-meta">
          <el-icon><Calendar /></el-icon>
          <span>Current term · <strong>{{ term || '—' }}</strong></span>
        </div>
        <el-button type="primary" class="press" @click="$router.push('/studentSelectCourse')">
          <el-icon><Search /></el-icon><span>Browse courses</span>
        </el-button>
      </div>
    </section>

    <section class="ink-band">
      <div class="ink-band__grid" style="grid-template-columns: repeat(4, 1fr)">
        <div class="ink-band__item">
          <p class="ink-band__label">Enrolled</p>
          <p class="ink-band__value">{{ stats.enrolled }}</p>
          <p class="ink-band__hint">Courses this term</p>
        </div>
        <div class="ink-band__item">
          <p class="ink-band__label">Credits</p>
          <p class="ink-band__value">{{ stats.credits }}</p>
          <p class="ink-band__hint">Total credit hours</p>
        </div>
        <div class="ink-band__item">
          <p class="ink-band__label">Average grade</p>
          <p class="ink-band__value">{{ stats.averageGrade }}</p>
          <p class="ink-band__hint">Across this term's graded courses</p>
        </div>
        <div class="ink-band__item">
          <p class="ink-band__label">Major</p>
          <p class="ink-band__value" style="font-size: 22px">Artificial Intelligence</p>
          <p class="ink-band__hint">School of AI &amp; Computing</p>
        </div>
      </div>
    </section>

    <section>
      <div class="section-header">
        <div>
          <h2 class="section-header__title">Quick actions</h2>
          <p class="section-header__hint">Move fast through the term.</p>
        </div>
      </div>
      <div class="quick-grid">
        <div class="tint-card tint-card--sky" @click="$router.push('/studentSelectCourse')">
          <el-icon class="tint-card__arrow"><ArrowRight /></el-icon>
          <div class="tint-card__icon"><el-icon><Search /></el-icon></div>
          <p class="tint-card__title">Browse courses</p>
          <p class="tint-card__desc">Search the catalog and select a new course.</p>
        </div>
        <div class="tint-card tint-card--mint" @click="$router.push('/querySelectedCourse')">
          <el-icon class="tint-card__arrow"><ArrowRight /></el-icon>
          <div class="tint-card__icon"><el-icon><Document /></el-icon></div>
          <p class="tint-card__title">My schedule</p>
          <p class="tint-card__desc">View selected courses and drop if needed.</p>
        </div>
        <div class="tint-card tint-card--lavender" @click="$router.push('/queryCourseGrade')">
          <el-icon class="tint-card__arrow"><ArrowRight /></el-icon>
          <div class="tint-card__icon"><el-icon><DataAnalysis /></el-icon></div>
          <p class="tint-card__title">My grades</p>
          <p class="tint-card__desc">Term-by-term grade history.</p>
        </div>
        <div class="tint-card tint-card--peach" @click="$router.push('/updateInfoHome')">
          <el-icon class="tint-card__arrow"><ArrowRight /></el-icon>
          <div class="tint-card__icon"><el-icon><User /></el-icon></div>
          <p class="tint-card__title">Account settings</p>
          <p class="tint-card__desc">Update your name and password.</p>
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
  DataAnalysis,
  Document,
  Search,
  User,
  UserFilled,
} from '@element-plus/icons-vue'

const name = ref(sessionStorage.getItem('name') || 'Student')
const term = ref(sessionStorage.getItem('currentTerm') || '')

const stats = reactive({
  enrolled: '—',
  credits: '—',
  averageGrade: '—',
})

function safeGet(url, cb) {
  axios
    .get(url)
    .then((r) => cb(r.data))
    .catch(() => {})
}

function computeAverageGrade(rows) {
  const grades = rows
    .map((r) => Number(r.grade))
    .filter((n) => Number.isFinite(n))
  if (grades.length === 0) return '—'
  return (grades.reduce((a, b) => a + b, 0) / grades.length).toFixed(1)
}

onMounted(() => {
  const sid = sessionStorage.getItem('sid')
  if (!sid) return
  const t = sessionStorage.getItem('currentTerm') || ''
  safeGet(`/SCT/findBySid/${sid}/${encodeURIComponent(t)}`, (data) => {
    if (Array.isArray(data)) {
      stats.enrolled = data.length
      stats.credits = data.reduce((sum, c) => sum + (Number(c.ccredit) || 0), 0)
      stats.averageGrade = computeAverageGrade(data)
    }
  })
})
</script>

<style scoped>
.home-page {
  gap: 28px;
}

.quick-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
  gap: 16px;
  margin-top: 4px;
}
</style>
