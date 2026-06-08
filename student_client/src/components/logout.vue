<template>
  <el-popconfirm
    confirm-button-text="Sign out"
    cancel-button-text="Cancel"
    title="Sign out of your account?"
    @confirm="out"
  >
    <template #reference>
      <button class="signout-link" type="button">
        <el-icon><SwitchButton /></el-icon>
        <span>Sign out</span>
      </button>
    </template>
  </el-popconfirm>
</template>

<script setup>
import { SwitchButton } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'

const router = useRouter()

function out() {
  sessionStorage.removeItem('token')
  router.push('/login')
}
</script>

<style scoped>
.signout-link {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  padding: 8px 14px;
  background: transparent;
  border: 0;
  border-radius: var(--radius-sm);
  color: var(--ink-muted);
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: all var(--duration-base) var(--ease-out);
}

.signout-link:hover {
  background: var(--danger-soft);
  color: var(--danger);
}

.signout-link:active {
  transform: scale(0.96);
}
</style>
