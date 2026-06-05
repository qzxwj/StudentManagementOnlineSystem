<template>
  <div class="app-shell">
    <el-container>
      <el-header class="app-topbar">
        <r-header></r-header>
      </el-header>
      <el-container class="app-body">
        <r-aside></r-aside>
        <el-main class="app-main">
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import RAside from '@/components/r-aside.vue'

import RHeader from '@/components/r-header.vue'

defineOptions({ name: 'index' })
</script>

<style scoped></style>
