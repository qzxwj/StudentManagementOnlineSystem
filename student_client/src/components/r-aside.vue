<template>
  <div>
    <el-aside class="app-sidebar">
      <div class="app-sidebar__brand">
        <p class="app-sidebar__title">Course Selection</p>
        <p class="app-sidebar__subtitle">{{ roleLabel }} workspace</p>
      </div>
      <el-menu router :default-active="route.path">
        <div v-for="(item, index) in menuRoutes" :index="index + ''" :key="item.name">
          <template v-for="item2 in item.children || []" :key="item2.path">
            <el-sub-menu v-if="item2.children?.length" :index="item2.path">
              <template #title
                ><el-icon><Menu /></el-icon>{{ item2.meta?.title || item2.name }}</template
              >
              <el-menu-item v-for="item3 in item2.children" :index="item3.path" :key="item3.path"
                ><el-icon><Promotion /></el-icon>{{ item3.meta?.title || item3.name }}</el-menu-item
              >
            </el-sub-menu>
            <el-menu-item v-else :index="item2.path"
              ><el-icon><Promotion /></el-icon>{{ item2.meta?.title || item2.name }}</el-menu-item
            >
          </template>
        </div>
      </el-menu>
    </el-aside>
  </div>
</template>

<script setup>
import { computed, ref } from 'vue'
import { Menu, Promotion } from '@element-plus/icons-vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()
const type = ref(sessionStorage.getItem('type'))
const menuRoutes = computed(() => router.options.routes.filter((item) => item.name === type.value))
const roleLabel = computed(() => {
  if (type.value === 'admin') return 'Admin'
  if (type.value === 'teacher') return 'Teacher'
  if (type.value === 'student') return 'Student'
  return 'User'
})
</script>

<style scoped>
.app-sidebar :deep(.el-menu) {
  padding: 12px 0;
}

.app-sidebar :deep(.el-menu-item),
.app-sidebar :deep(.el-sub-menu__title) {
  padding-left: 20px !important;
}
</style>
