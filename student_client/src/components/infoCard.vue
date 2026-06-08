<template>
  <div class="info-card">
    <div class="info-card__icon">
      <el-icon><UserFilled /></el-icon>
    </div>
    <div class="info-card__grid">
      <div class="info-card__field">
        <p class="info-card__label">{{ typeLabel }} ID</p>
        <p class="info-card__value mono">{{ id || '—' }}</p>
      </div>
      <div class="info-card__field">
        <p class="info-card__label">Name</p>
        <p class="info-card__value">{{ name || '—' }}</p>
      </div>
      <div class="info-card__field">
        <p class="info-card__label">Role</p>
        <p class="info-card__value">
          <el-tag size="small" :type="type === 'admin' ? 'danger' : 'primary'">{{
            typeLabel
          }}</el-tag>
        </p>
      </div>
      <div class="info-card__field">
        <p class="info-card__label">Term</p>
        <p class="info-card__value">{{ term || '—' }}</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, ref } from 'vue'
import { UserFilled } from '@element-plus/icons-vue'

const type = ref(sessionStorage.getItem('type'))
const id = ref(
  type.value === 'teacher' ? sessionStorage.getItem('tid') : sessionStorage.getItem('sid'),
)
const name = ref(sessionStorage.getItem('name'))
const term = ref(sessionStorage.getItem('currentTerm'))

const typeLabel = computed(() => {
  if (type.value === 'admin') return 'Admin'
  if (type.value === 'teacher') return 'Teacher'
  if (type.value === 'student') return 'Student'
  return 'User'
})
</script>

<style scoped>
.info-card {
  display: flex;
  align-items: center;
  gap: 24px;
  background: var(--canvas);
  border: 1px solid var(--hairline);
  border-radius: var(--radius-md);
  padding: 24px 28px;
  box-shadow: var(--shadow-card);
}

.info-card__icon {
  width: 56px;
  height: 56px;
  border-radius: var(--radius-md);
  background: linear-gradient(135deg, var(--sky-100), var(--sky-200));
  color: var(--sky-700);
  display: grid;
  place-items: center;
  font-size: 24px;
  flex-shrink: 0;
  box-shadow: inset 0 1px 0 rgba(255, 255, 255, 0.6);
}

.info-card__grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(140px, 1fr));
  gap: 18px 28px;
  flex: 1;
  min-width: 0;
}

.info-card__field {
  display: flex;
  flex-direction: column;
  gap: 4px;
  min-width: 0;
}

.info-card__label {
  margin: 0;
  color: var(--ink-subtle);
  font-size: 11.5px;
  font-weight: 600;
  letter-spacing: 0.08em;
  text-transform: uppercase;
}

.info-card__value {
  margin: 0;
  color: var(--ink);
  font-size: 15px;
  font-weight: 500;
  letter-spacing: -0.01em;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.info-card__value.mono {
  font-family: 'JetBrains Mono', ui-monospace, SFMono-Regular, monospace;
  font-size: 14px;
  color: var(--sky-700);
}
</style>
