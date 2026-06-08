<template>
  <div>
    <div class="page-header">
      <div>
        <p class="page-header__eyebrow">Teacher · Grades</p>
        <h1 class="page-header__title">Search grades</h1>
        <p class="page-header__subtitle">Find grades for students in your offerings.</p>
      </div>
      <div class="page-header__actions">
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
        <el-form-item label="Student ID" prop="sid">
          <el-input v-model.number="ruleForm.sid" placeholder="Student ID" clearable />
        </el-form-item>
        <el-form-item label="Student name" prop="sname">
          <el-input v-model="ruleForm.sname" placeholder="Search by name" clearable />
        </el-form-item>
        <el-form-item label="Fuzzy (student)">
          <el-switch v-model="ruleForm.sFuzzy" />
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
        <el-form-item label="Min grade" prop="lowBound">
          <el-input-number v-model="ruleForm.lowBound" :min="0" :max="100" />
        </el-form-item>
        <el-form-item label="Max grade" prop="highBound">
          <el-input-number v-model="ruleForm.highBound" :min="0" :max="100" />
        </el-form-item>
        <el-form-item label="Term">
          <el-select v-model="ruleForm.term" placeholder="All terms" clearable>
            <el-option
              v-for="(item, index) in termList"
              :key="index"
              :label="item"
              :value="item"
            />
          </el-select>
        </el-form-item>
      </el-form>
    </el-card>

    <teacher-grade-course-list :rule-form="ruleForm"></teacher-grade-course-list>
  </div>
</template>
<script setup>
import { reactive, ref, toRefs } from 'vue'
import { Refresh } from '@element-plus/icons-vue'
import TeacherGradeCourseList from '@/views/Teacher/teacherGradeCourseManage/teacherGradeCourseList.vue'

const ruleFormRef = ref(null)

const state = reactive({
  termList: null,
  ruleForm: {
    sid: null,
    sname: null,
    sFuzzy: true,
    tid: sessionStorage.getItem('tid'),
    tname: null,
    tFuzzy: true,
    cid: null,
    cname: null,
    cFuzzy: true,
    lowBound: null,
    highBound: null,
    term: sessionStorage.getItem('currentTerm'),
  },
  rules: {
    cid: [{ type: 'number', message: 'Must be a number' }],
    tid: [{ type: 'number', message: 'Must be a number' }],
    sid: [{ type: 'number', message: 'Must be a number' }],
    cname: [],
    lowBound: [{ type: 'number', message: 'Must be a number' }],
    highBound: [{ type: 'number', message: 'Must be a number' }],
  },
})

const { termList, ruleForm, rules } = toRefs(state)

axios.get('/SCT/findAllTerm').then(function (resp) {
  state.termList = resp.data
})

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
