<template>
  <div>
    <div class="page-header">
      <div>
        <p class="page-header__eyebrow">Teacher · Courses</p>
        <h1 class="page-header__title">Offer a course</h1>
        <p class="page-header__subtitle">Search the catalog and offer a course for the current term.</p>
      </div>
      <div class="page-header__actions">
        <el-button @click="router.push('/myOfferCourse')">
          <el-icon><Calendar /></el-icon><span>My offerings</span>
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
        <el-form-item label="Course ID" prop="cid">
          <el-input v-model.number="ruleForm.cid" placeholder="Course ID" clearable />
        </el-form-item>
        <el-form-item label="Course name" prop="cname">
          <el-input v-model="ruleForm.cname" placeholder="Search by name" clearable />
        </el-form-item>
        <el-form-item label="Fuzzy match" prop="fuzzy">
          <el-switch v-model="ruleForm.fuzzy" />
        </el-form-item>
        <el-form-item label="Min credits" prop="lowBound">
          <el-input-number v-model="ruleForm.lowBound" :min="0" :max="10" />
        </el-form-item>
        <el-form-item label="Max credits" prop="highBound">
          <el-input-number v-model="ruleForm.highBound" :min="0" :max="10" />
        </el-form-item>
      </el-form>
    </el-card>

    <offer-course-list :rule-form="ruleForm"></offer-course-list>
  </div>
</template>
<script setup>
import { reactive, ref, toRefs } from 'vue'
import { Calendar, Refresh } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'
import OfferCourseList from '@/views/Teacher/offerCourseList.vue'

const router = useRouter()
const ruleFormRef = ref(null)

const state = reactive({
  ruleForm: {
    cid: null,
    cname: null,
    fuzzy: true,
    lowBound: null,
    highBound: null,
  },
  rules: {
    cid: [{ type: 'number', message: 'Must be a number' }],
    cname: [],
    lowBound: [{ type: 'number', message: 'Must be a number' }],
    highBound: [{ type: 'number', message: 'Must be a number' }],
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
