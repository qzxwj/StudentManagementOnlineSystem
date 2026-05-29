<template>
  <div>
    <el-container>
      <el-main>
        <h1>student home</h1>
        <info-card></info-card>
        <el-card style="width: 100%; margin-top: 10px">
          <p><el-icon style="margin-right: 18px"><House /></el-icon>Management:Student Management</p>
          <p><el-icon style="margin-right: 18px"><Location /></el-icon>Nanjing University of Information Science and Technology</p>
          <p><el-icon style="margin-right: 18px"><Major /></el-icon>Artificial Intelligence</p>
        </el-card>
        <logout></logout>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import Logout from "@/components/logout.vue";

import InfoCard from "@/components/infoCard.vue";

defineOptions({ name: "home" })
</script>

<style scoped>

</style>
