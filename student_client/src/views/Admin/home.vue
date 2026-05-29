<template>
  <div>
    <el-container>
      <el-main>
        <h1>admin home</h1>
        <el-card style="width: 40%">
          <p><el-icon style="margin-right: 18px"><House /></el-icon>Management:Student Management</p>
          <p><el-icon style="margin-right: 18px"><Location /></el-icon>Nanjing University of Information Science and Technology</p>
        </el-card>
        <logout></logout>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import Logout from "@/components/logout.vue";

defineOptions({ name: "home" })
</script>

<style scoped>

</style>
