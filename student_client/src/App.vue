<template>
  <div class="app-root">
    <div class="aurora-bg" aria-hidden="true">
      <span class="aurora-bg__orb aurora-bg__orb--sunset"></span>
      <span class="aurora-bg__orb aurora-bg__orb--midnight"></span>
    </div>
    <router-view v-slot="{ Component, route }">
      <transition name="page" mode="out-in">
        <component :is="Component" :key="route.fullPath" />
      </transition>
    </router-view>
  </div>
</template>

<script setup>
defineOptions({ name: 'app' })
</script>

<style scoped>
.app-root {
  position: relative;
  min-height: 100vh;
  z-index: 2;
}
</style>
