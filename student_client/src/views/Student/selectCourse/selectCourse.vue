<template>
  <div>
    <el-container>
      <el-main>
        <el-card>
          <el-form :inline="true" :model="ruleForm" :rules="rules" ref="ruleFormRef" label-width="150px" class="demo-ruleForm">
            <el-form-item label="Teacher ID" prop="tid">
              <el-input v-model.number="ruleForm.tid"></el-input>
            </el-form-item>
            <el-form-item label="Teacher Name" prop="tname">
              <el-input v-model.number="ruleForm.tname"></el-input>
            </el-form-item>
            <el-form-item label="TeacherFuzzy Search">
              <el-switch v-model="ruleForm.tFuzzy"></el-switch>
            </el-form-item>
            <el-form-item label="Course ID" prop="cid">
              <el-input v-model.number="ruleForm.cid"></el-input>
            </el-form-item>
            <el-form-item label="Course Name" prop="cname">
              <el-input v-model.number="ruleForm.cname"></el-input>
            </el-form-item>
            <el-form-item label="Fuzzy Search">
              <el-switch v-model="ruleForm.cFuzzy"></el-switch>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="resetForm">Reset</el-button>
            </el-form-item>
          </el-form>
        </el-card>
        <el-card style="margin-top: 10px">
          <select-course-list :rule-form="ruleForm"></select-course-list>
        </el-card>
      </el-main>
    </el-container>
  </div>
</template>
<script setup>
import { reactive, ref, toRefs } from 'vue'

import SelectCourseList from "@/views/Student/selectCourse/selectCourseList.vue";

const ruleFormRef = ref(null)

const state = reactive({
      ruleForm: {
        tid: null,
        cid: null,
        cname: null,
        tname: null,
        tFuzzy: true,
        cFuzzy: true
      },
      rules: {
        tid: [
          { type: 'number', message: 'Must be a number' }
        ],
        cid: [
          { type: 'number', message: 'Must be a number' }
        ],
      }
    })

const { ruleForm, rules } = toRefs(state)

function resetForm(formName) {
ruleFormRef.value.resetFields();
}
</script>