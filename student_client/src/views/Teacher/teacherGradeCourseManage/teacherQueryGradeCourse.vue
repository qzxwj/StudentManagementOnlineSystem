<template>
  <div>
    <el-container>
      <el-main>
        <el-card>
          <el-form :inline="true" :model="ruleForm" :rules="rules" ref="ruleFormRef" label-width="120px" class="demo-ruleForm">
            <el-form-item label="Student ID" prop="sid">
              <el-input v-model.number="ruleForm.sid"></el-input>
            </el-form-item>
            <el-form-item label="Student Name" prop="sname">
              <el-input v-model="ruleForm.sname"></el-input>
            </el-form-item>
            <el-form-item label="Fuzzy Search" prop="sFuzzy">
              <el-switch v-model="ruleForm.sFuzzy"></el-switch>
            </el-form-item>
            <el-form-item label="Course ID" prop="cid">
              <el-input v-model.number="ruleForm.cid"></el-input>
            </el-form-item>
            <el-form-item label="Course Name" prop="cname">
              <el-input v-model="ruleForm.cname"></el-input>
            </el-form-item>
            <el-form-item label="Fuzzy Search" prop="cFuzzy">
              <el-switch v-model="ruleForm.cFuzzy"></el-switch>
            </el-form-item>
            <el-form-item label="Grade Management" prop="lowBound">
              <el-input v-model.number="ruleForm.lowBound"></el-input>
            </el-form-item>
            <el-form-item label="Grade Management" prop="highBound">
              <el-input v-model.number="ruleForm.highBound"></el-input>
            </el-form-item>
            <el-form-item label="SelectTerm">
              <el-select v-model="ruleForm.term" placeholder="Please select a term">
                <el-option v-for="(item, index) in termList" :key="index" :label="item" :value="item"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="resetForm">Reset</el-button>
            </el-form-item>
          </el-form>
        </el-card>
        <el-card style="margin-top: 10px">
          <teacher-grade-course-list :rule-form="ruleForm"></teacher-grade-course-list>
        </el-card>
      </el-main>
    </el-container>
  </div>
</template>
<script setup>
import { reactive, ref, toRefs } from 'vue'

import GradeCourseList from "@/views/Admin/gradeCourseManage/gradeCourseList.vue";

import TeacherGradeCourseList from "@/views/Teacher/teacherGradeCourseManage/teacherGradeCourseList.vue";

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
        term: sessionStorage.getItem('currentTerm')
      },
      rules: {
        cid: [
          { type: 'number', message: 'Must be a number' }
        ],
        tid: [
          { type: 'number', message: 'Must be a number' }
        ],
        sid: [
          { type: 'number', message: 'Must be a number' }
        ],
        cname: [
        ],
        lowBound: [
          { type: 'number', message: 'Must be a number' }
        ],
        highBound: [
          { type: 'number', message: 'Must be a number' }
        ],
      }
    })

const { termList, ruleForm, rules } = toRefs(state)

axios.get('/SCT/findAllTerm').then(function (resp) {
      state.termList = resp.data
    })

function resetForm(formName) {
ruleFormRef.value.resetFields();
}
</script>