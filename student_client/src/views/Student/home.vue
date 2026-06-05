<template>
  <div>
    <el-container>
      <el-main>
        <h1>student home</h1>
        <info-card></info-card>
        <el-card class="home-card">
          <p>
            <el-icon><House /></el-icon>Management:Student Management
          </p>
          <p>
            <el-icon><Location /></el-icon>Nanjing University of Information Science and Technology
          </p>
          <p>
            <el-icon><Major /></el-icon>Artificial Intelligence
          </p>
        </el-card>
        <logout></logout>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import Logout from '@/components/logout.vue'

import InfoCard from '@/components/infoCard.vue'

defineOptions({ name: 'home' })
</script>

<style scoped></style>
