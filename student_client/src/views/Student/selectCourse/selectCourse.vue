<template>
  <div>
    <div class="page-header">
      <div>
        <p class="page-header__eyebrow">Student · Courses</p>
        <h1 class="page-header__title">Browse Courses</h1>
        <p class="page-header__subtitle">Find courses to add to your schedule this term.</p>
      </div>
      <div class="page-header__actions">
        <el-button @click="router.push('/querySelectedCourse')">
          <el-icon><Document /></el-icon><span>My schedule</span>
        </el-button>
        <el-button @click="resetForm">
          <el-icon><Refresh /></el-icon><span>Reset</span>
        </el-button>
      </div>
    </div>

    <el-card class="app-query-card">
      <el-form
        :inline="true"
        :model="ruleForm"
        :rules="rules"
        ref="ruleFormRef"
        class="query-form"
      >
        <el-form-item label="Teacher ID" prop="tid">
          <el-input v-model.number="ruleForm.tid" placeholder="Teacher ID" clearable />
        </el-form-item>
        <el-form-item label="Teacher name" prop="tname">
          <el-input v-model="ruleForm.tname" placeholder="Search by name" clearable />
        </el-form-item>
        <el-form-item label="Fuzzy (teacher)">
          <el-switch v-model="ruleForm.tFuzzy" />
        </el-form-item>
        <el-form-item label="Course ID" prop="cid">
          <el-input v-model.number="ruleForm.cid" placeholder="Course ID" clearable />
        </el-form-item>
        <el-form-item label="Course name" prop="cname">
          <el-input v-model="ruleForm.cname" placeholder="Search by name" clearable />
        </el-form-item>
        <el-form-item label="Fuzzy (course)">
          <el-switch v-model="ruleForm.cFuzzy" />
        </el-form-item>
      </el-form>
    </el-card>

    <select-course-list :rule-form="ruleForm"></select-course-list>
  </div>
</template>
<script setup>
import { reactive, ref, toRefs } from 'vue'
import { Document, Refresh } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'
import SelectCourseList from '@/views/Student/selectCourse/selectCourseList.vue'

const router = useRouter()
const ruleFormRef = ref(null)

const state = reactive({
  ruleForm: {
    tid: null,
    cid: null,
    cname: null,
    tname: null,
    tFuzzy: true,
    cFuzzy: true,
  },
  rules: {
    tid: [{ type: 'number', message: 'Must be a number' }],
    cid: [{ type: 'number', message: 'Must be a number' }],
  },
})

const { ruleForm, rules } = toRefs(state)

function resetForm() {
  ruleFormRef.value.resetFields()
}
</script>

<style scoped>
.query-form {
  max-width: none;
  display: flex;
  flex-wrap: wrap;
  gap: 16px 24px;
  align-items: flex-end;
}
.query-form :deep(.el-form-item) {
  margin-bottom: 0;
  margin-right: 0;
}
</style>
