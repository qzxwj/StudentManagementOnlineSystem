<template>
  <div>
    <el-popconfirm
      confirm-button-text="OK"
      cancel-button-text="No"
      icon-color="red"
      title="Not logged in page?"
      @confirm="out"
    >
      <template #reference>
        <el-button type="danger" class="logout-button"> logout </el-button>
      </template>
    </el-popconfirm>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

function out() {
  sessionStorage.removeItem('token')
  router.push('/')
}
</script>

<style scoped></style>
