<template>
  <el-card>
    <el-descriptions title="User Information">
      <el-descriptions-item label="User ID">{{ id }}</el-descriptions-item>
      <el-descriptions-item label="Username">{{ name }}</el-descriptions-item>
      <el-descriptions-item label="User Type">
        <el-tag size="small">{{ type }}</el-tag>
      </el-descriptions-item>
    </el-descriptions>
  </el-card>
</template>

<script setup>
import { ref } from 'vue'

const type = ref(sessionStorage.getItem('type'))
const id = ref(
  type.value === 'teacher' ? sessionStorage.getItem('tid') : sessionStorage.getItem('sid'),
)
const name = ref(sessionStorage.getItem('name'))
</script>

<style scoped></style>
