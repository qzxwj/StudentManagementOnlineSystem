<template>
  <div>
    <el-aside class="app-sidebar">
      <div class="app-sidebar__brand">
        <div class="app-sidebar__monogram">
          <el-icon><Reading /></el-icon>
        </div>
        <div class="app-sidebar__brand-text">
          <p class="app-sidebar__title">NUIST Course</p>
          <p class="app-sidebar__subtitle">{{ roleLabel }} · Workspace</p>
        </div>
      </div>
      <el-menu router :default-active="route.path">
        <template v-for="(item, index) in menuRoutes" :key="item.name">
          <template v-for="item2 in item.children || []" :key="item2.path">
            <el-sub-menu v-if="item2.children?.length" :index="item2.path">
              <template #title>
                <el-icon><component :is="iconFor(item2.name, true)" /></el-icon>
                <span>{{ item2.meta?.title || item2.name }}</span>
              </template>
              <el-menu-item v-for="item3 in item2.children" :index="item3.path" :key="item3.path">
                <el-icon><component :is="iconFor(item3.name, false)" /></el-icon>
                <span>{{ item3.meta?.title || item3.name }}</span>
              </el-menu-item>
            </el-sub-menu>
            <el-menu-item v-else :index="item2.path">
              <el-icon><component :is="iconFor(item2.name, false)" /></el-icon>
              <span>{{ item2.meta?.title || item2.name }}</span>
            </el-menu-item>
          </template>
        </template>
      </el-menu>
    </el-aside>
  </div>
</template>

<script setup>
import { computed, markRaw } from 'vue'
import {
  Calendar,
  Connection,
  DataAnalysis,
  DataLine,
  Document,
  EditPen,
  House,
  Menu,
  Notebook,
  Plus,
  Reading,
  School,
  Search,
  Setting,
  User,
  UserFilled,
} from '@element-plus/icons-vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()
const type = computed(() => sessionStorage.getItem('type'))

const menuRoutes = computed(() =>
  router.options.routes.filter((item) => item.name === type.value),
)

const roleLabel = computed(() => {
  if (type.value === 'admin') return 'Admin'
  if (type.value === 'teacher') return 'Teacher'
  if (type.value === 'student') return 'Student'
  return 'User'
})

const iconMap = {
  // Parent groups
  adminHomeLayout: markRaw(House),
  adminStudentManage: markRaw(User),
  adminTeacherManage: markRaw(UserFilled),
  adminCourseManage: markRaw(Notebook),
  adminCourseTeacherManage: markRaw(Connection),
  adminGradeCourseManage: markRaw(DataAnalysis),
  teacherHomeLayout: markRaw(House),
  teacherEdit: markRaw(Setting),
  teacherCourseManage: markRaw(Notebook),
  teacherGradeCourseManage: markRaw(DataAnalysis),
  studentHome: markRaw(House),
  studentEdit: markRaw(Setting),
  studentSelectCourseManage: markRaw(Notebook),
  studentCourseGrade: markRaw(DataLine),

  // Leaves
  adminHome: markRaw(House),
  adminStudentList: markRaw(User),
  adminEditorStudent: markRaw(EditPen),
  adminQueryTeacher: markRaw(Search),
  adminEditorTeacher: markRaw(EditPen),
  adminAddCourse: markRaw(Plus),
  adminQueryCourse: markRaw(Search),
  adminCourseList: markRaw(Reading),
  adminEditorCourse: markRaw(EditPen),
  adminQueryCourseTeacher: markRaw(Connection),
  adminQueryGradeCourse: markRaw(DataAnalysis),
  adminEditorGradeCourse: markRaw(EditPen),
  teacherHome: markRaw(House),
  teacherUpdateInfoHome: markRaw(Setting),
  teacherMyOfferCourse: markRaw(Calendar),
  teacherOfferCourse: markRaw(Plus),
  teacherQueryGradeCourse: markRaw(DataAnalysis),
  teacherEditorGradeCourse: markRaw(EditPen),
  studentUpdateInfoHome: markRaw(Setting),
  studentSelectCourse: markRaw(Search),
  studentQuerySelectedCourse: markRaw(Document),
  studentQueryCourseGrade: markRaw(DataLine),
}

function iconFor(name, isParent) {
  return iconMap[name] || markRaw(isParent ? Menu : School)
}
</script>

<style scoped>
.app-sidebar :deep(.el-menu) {
  padding: 4px 0 12px;
}

.app-sidebar :deep(.el-menu-item),
.app-sidebar :deep(.el-sub-menu__title) {
  margin: 2px 12px;
  padding-left: 14px !important;
}

.app-sidebar :deep(.el-sub-menu .el-menu-item) {
  margin: 2px 12px;
  padding-left: 38px !important;
}

.app-sidebar__brand-text {
  min-width: 0;
  flex: 1;
}
</style>
