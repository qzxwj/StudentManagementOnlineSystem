<template>
  <div class="topbar-user">
    <div class="topbar-item">
      <el-icon><Paperclip /></el-icon>
      <span>{{ currentTerm }}</span>
    </div>
    <el-divider direction="vertical"></el-divider>
    <div class="topbar-item">
      <el-icon><User /></el-icon>
      <span>{{ name }}</span>
    </div>
    <el-divider direction="vertical"></el-divider>
    <el-button type="primary" plain @click="out()"> Logout </el-button>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const name = ref(sessionStorage.getItem('name'))
const currentTerm = ref(sessionStorage.getItem('currentTerm'))

function out() {
  sessionStorage.clear()
  router.push('/')
}
</script>

<style scoped>
.topbar-user {
  display: flex;
  align-items: center;
  justify-content: flex-end;
  gap: 12px;
  color: var(--sms-ink-muted);
  font-size: 14px;
}

.topbar-item {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  min-height: 40px;
}

.topbar-item .el-icon {
  color: var(--sms-blue);
}
</style>
