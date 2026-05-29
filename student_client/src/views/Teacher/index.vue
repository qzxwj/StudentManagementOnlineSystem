<template>
  <div>
    <el-container>
      <el-header style="text-align: right; font-size: 18px; background-color: #409EFF; border-radius: 3px">
        <r-header></r-header>
      </el-header>
      <el-container>
        <r-aside></r-aside>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>
import RAside from "@/components/r-aside.vue";

import RHeader from "@/components/r-header.vue";

defineOptions({ name: "index" })
</script>

<style scoped>

</style>