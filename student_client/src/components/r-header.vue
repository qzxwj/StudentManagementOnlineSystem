<template>
  <div class="topbar-row">
    <nav class="topbar-breadcrumb" aria-label="Breadcrumb">
      <span
        v-for="(crumb, idx) in breadcrumbs"
        :key="idx"
        class="topbar-breadcrumb__item"
        :class="{ 'is-current': idx === breadcrumbs.length - 1 }"
      >
        <el-icon v-if="idx === 0"><HomeFilled /></el-icon>
        <span>{{ crumb }}</span>
        <el-icon v-if="idx < breadcrumbs.length - 1" class="topbar-breadcrumb__sep"
          ><ArrowRight
        /></el-icon>
      </span>
    </nav>

    <div class="topbar-user">
      <div class="topbar-item topbar-item--term" :title="currentTerm || 'Current term'">
        <el-icon><Calendar /></el-icon>
        <span class="topbar-term">{{ termLabel }}</span>
      </div>
      <span class="topbar-divider" aria-hidden="true"></span>
      <el-popconfirm
        confirm-button-text="Sign out"
        cancel-button-text="Cancel"
        title="Sign out of your account?"
        @confirm="out"
      >
        <template #reference>
          <button class="user-chip" type="button">
            <span class="user-chip__avatar">{{ initial }}</span>
            <span class="user-chip__name">{{ name || 'Account' }}</span>
            <el-icon class="user-chip__caret"><ArrowDown /></el-icon>
          </button>
        </template>
      </el-popconfirm>
    </div>
  </div>
</template>

<script setup>
import { computed, ref } from 'vue'
import { ArrowDown, ArrowRight, Calendar, HomeFilled } from '@element-plus/icons-vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()

const name = ref(sessionStorage.getItem('name'))
const currentTerm = ref(sessionStorage.getItem('currentTerm'))

const initial = computed(() => (name.value || 'U').charAt(0).toUpperCase())

const termLabel = computed(() => {
  const raw = currentTerm.value
  if (!raw) return 'TERM · —'
  return `TERM · ${raw}`
})

const breadcrumbs = computed(() => {
  const parts = []
  for (const m of route.matched) {
    const t = m.meta?.title
    if (t && !parts.includes(t)) parts.push(t)
  }
  if (parts.length === 0) parts.push('Home')
  // Pretty-ify mangled titles
  return parts.map((p) =>
    String(p)
      .replace(/admin|Admin/gi, '')
      .replace(/teacher/gi, '')
      .replace(/student/gi, '')
      .replace(/Hi!/gi, 'Overview')
      .replace(/Management/gi, '')
      .replace(/Search/gi, 'Search')
      .trim() || 'Overview',
  )
})

function out() {
  sessionStorage.clear()
  router.push('/login')
}
</script>

<style scoped>
.topbar-row {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  gap: 16px;
}

.topbar-user {
  display: flex;
  align-items: center;
  gap: 8px;
  color: var(--color-ink-muted);
  font-size: 14px;
}

.topbar-item--term {
  color: var(--color-ink-subtle);
  padding: 0 14px;
}

.topbar-divider {
  display: inline-block;
  width: 1px;
  height: 20px;
  background: var(--color-hairline);
  margin: 0 6px;
}
</style>
