<template>
  <div class="app-page home-page">
    <section class="home-hero">
      <div class="home-hero__mesh" aria-hidden="true"></div>
      <div class="home-hero__content">
        <span class="home-hero__eyebrow">
          <el-icon><Reading /></el-icon>Teacher workspace
        </span>
        <h1 class="home-hero__heading">Welcome back, {{ name }}.</h1>
        <p class="home-hero__subheading">
          Set up your {{ term || 'current term' }} offerings, track who's enrolled, and submit
          grades when the term closes.
        </p>
      </div>
      <div class="home-hero__cta">
        <div class="home-hero__cta-meta">
          <el-icon><Calendar /></el-icon>
          <span>Current term · <strong>{{ term || '—' }}</strong></span>
        </div>
        <el-button type="primary" class="press" @click="$router.push('/offerCourse')">
          <el-icon><Plus /></el-icon><span>Offer a course</span>
        </el-button>
      </div>
    </section>

    <section class="ink-band">
      <div class="ink-band__grid" style="grid-template-columns: repeat(4, 1fr)">
        <div class="ink-band__item">
          <p class="ink-band__label">Offerings</p>
          <p class="ink-band__value">{{ stats.offerings }}</p>
          <p class="ink-band__hint">Courses you teach this term</p>
        </div>
        <div class="ink-band__item">
          <p class="ink-band__label">Students</p>
          <p class="ink-band__value">{{ stats.students }}</p>
          <p class="ink-band__hint">Total enrolled across courses</p>
        </div>
        <div class="ink-band__item">
          <p class="ink-band__label">Pending grades</p>
          <p class="ink-band__value">{{ stats.pendingGrades }}</p>
          <p class="ink-band__hint">Records awaiting entry</p>
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
          <p class="section-header__hint">The things teachers do most.</p>
        </div>
      </div>
      <div class="quick-grid">
        <div class="tint-card tint-card--sky" @click="$router.push('/myOfferCourse')">
          <el-icon class="tint-card__arrow"><ArrowRight /></el-icon>
          <div class="tint-card__icon"><el-icon><Calendar /></el-icon></div>
          <p class="tint-card__title">My offerings</p>
          <p class="tint-card__desc">View the courses you're teaching this term.</p>
        </div>
        <div class="tint-card tint-card--mint" @click="$router.push('/offerCourse')">
          <el-icon class="tint-card__arrow"><ArrowRight /></el-icon>
          <div class="tint-card__icon"><el-icon><Plus /></el-icon></div>
          <p class="tint-card__title">Offer a course</p>
          <p class="tint-card__desc">Add a new course offering to the term.</p>
        </div>
        <div class="tint-card tint-card--lavender" @click="$router.push('/teacherQueryGradeCourseManage')">
          <el-icon class="tint-card__arrow"><ArrowRight /></el-icon>
          <div class="tint-card__icon"><el-icon><DataAnalysis /></el-icon></div>
          <p class="tint-card__title">Manage grades</p>
          <p class="tint-card__desc">Search, view, and edit your students' grades.</p>
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
  Plus,
  Reading,
  User,
} from '@element-plus/icons-vue'

const name = ref(sessionStorage.getItem('name') || 'Teacher')
const term = ref(sessionStorage.getItem('currentTerm') || '')

const stats = reactive({
  offerings: '—',
  students: '—',
  pendingGrades: '—',
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
  const tid = sessionStorage.getItem('tid')
  if (!tid) return
  const t = sessionStorage.getItem('currentTerm') || ''

  safeGet(`/courseTeacher/findMyCourse/${tid}/${encodeURIComponent(t)}`, (data) => {
    if (Array.isArray(data)) stats.offerings = data.length
  })

  safePost('/SCT/findBySearch', { tid: Number(tid), term: t }, (data) => {
    if (!Array.isArray(data)) return
    stats.students = new Set(data.map((r) => r.sid)).size
    stats.pendingGrades = data.filter((r) => r.grade == null).length
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
