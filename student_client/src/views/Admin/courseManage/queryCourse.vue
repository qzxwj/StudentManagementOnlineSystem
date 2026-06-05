<template>
  <div>
    <el-container>
      <el-main>
        <el-card class="app-query-card">
          <el-form
            :inline="true"
            :model="ruleForm"
            :rules="rules"
            ref="ruleFormRef"
            label-width="120px"
            class="demo-ruleForm"
          >
            <el-form-item label="Course ID" prop="cid">
              <el-input v-model.number="ruleForm.cid"></el-input>
            </el-form-item>
            <el-form-item label="Course Name" prop="cname">
              <el-input v-model="ruleForm.cname"></el-input>
            </el-form-item>
            <el-form-item label="Fuzzy Search" prop="fuzzy">
              <el-switch v-model="ruleForm.fuzzy"></el-switch>
            </el-form-item>
            <el-form-item label="Credits Bound" prop="lowBound">
              <el-input v-model.number="ruleForm.lowBound"></el-input>
            </el-form-item>
            <el-form-item label="Credits Bound" prop="highBound">
              <el-input v-model.number="ruleForm.highBound"></el-input>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="resetForm">Reset</el-button>
            </el-form-item>
          </el-form>
        </el-card>
        <el-card>
          <course-list :ruleForm="ruleForm" :isActive="true"></course-list>
        </el-card>
      </el-main>
    </el-container>
  </div>
</template>
<script setup>
import { reactive, ref, toRefs } from 'vue'

import CourseList from '@/views/Admin/courseManage/courseList.vue'

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

function resetForm(formName) {
  ruleFormRef.value.resetFields()
}
</script>
