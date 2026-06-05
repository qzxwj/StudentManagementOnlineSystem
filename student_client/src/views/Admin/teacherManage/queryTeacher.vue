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
            label-width="100px"
            class="demo-ruleForm"
          >
            <el-form-item label="Teacher ID" prop="tid">
              <el-input v-model.number="ruleForm.tid"></el-input>
            </el-form-item>
            <el-form-item label="Teacher Name" prop="tname">
              <el-input v-model="ruleForm.tname"></el-input>
            </el-form-item>
            <el-form-item label="Fuzzy Search" prop="fuzzy">
              <el-switch v-model="ruleForm.fuzzy"></el-switch>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="resetForm">Reset</el-button>
            </el-form-item>
          </el-form>
        </el-card>
        <el-card>
          <teacher-list :ruleForm="ruleForm"></teacher-list>
        </el-card>
      </el-main>
    </el-container>
  </div>
</template>
<script setup>
import { reactive, ref, toRefs } from 'vue'

import TeacherList from '@/views/Admin/teacherManage/teacherList.vue'

const ruleFormRef = ref(null)

const state = reactive({
  ruleForm: {
    tid: null,
    tname: null,
    fuzzy: true,
  },
  rules: {
    tid: [{ type: 'number', message: 'Must be a number' }],
    tname: [],
  },
})

const { ruleForm, rules } = toRefs(state)

function resetForm(formName) {
  ruleFormRef.value.resetFields()
}
</script>
