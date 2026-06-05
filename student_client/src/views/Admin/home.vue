<template>
  <div>
    <el-container>
      <el-main>
        <h1>admin home</h1>
        <el-card class="home-card">
          <p>
            <el-icon><House /></el-icon>Management:Student Management
          </p>
          <p>
            <el-icon><Location /></el-icon>Nanjing University of Information Science and Technology
          </p>
        </el-card>
        <logout></logout>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import Logout from '@/components/logout.vue'

defineOptions({ name: 'home' })
</script>

<style scoped></style>
