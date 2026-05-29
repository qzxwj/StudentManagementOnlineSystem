<template>
  <div>
      <el-button type="text" style="color: #333333; font-size: 18px" @click="out()">
        logout
      </el-button>
      <el-divider direction="vertical" style="color: #333333"></el-divider>
      <el-icon style="margin-right: 18px"><Paperclip /></el-icon>
      <span>{{ currentTerm }}</span>
      <el-divider direction="vertical"></el-divider>
      <el-icon style="margin-right: 18px"><User /></el-icon>
      <span>{{ name }}</span>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const name = ref(sessionStorage.getItem("name"))
const currentTerm = ref(sessionStorage.getItem("currentTerm"))

function out() {
  sessionStorage.clear();
  router.push('/')
}
</script>

<style scoped>

</style>
