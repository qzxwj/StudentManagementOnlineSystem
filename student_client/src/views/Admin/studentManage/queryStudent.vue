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
            <el-form-item label="Student ID" prop="sid">
              <el-input v-model.number="ruleForm.sid"></el-input>
            </el-form-item>
            <el-form-item label="Student Name" prop="sname">
              <el-input v-model="ruleForm.sname"></el-input>
            </el-form-item>
            <el-form-item label="Fuzzy Search" prop="password">
              <el-switch v-model="ruleForm.password"></el-switch>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="submitForm">Search</el-button>
              <el-button @click="resetForm">Reset</el-button>
              <el-button @click="flush('ruleForm')">Refresh</el-button>
            </el-form-item>
          </el-form>
        </el-card>
        <router-view></router-view>
      </el-main>
    </el-container>
  </div>
</template>
<script setup>
import { reactive, ref, toRefs } from 'vue'

import { useRouter } from 'vue-router'

const router = useRouter()

const ruleFormRef = ref(null)

const state = reactive({
  ruleForm: {
    sid: null,
    sname: null,
    password: true,
  },
  rules: {
    sid: [{ type: 'number', message: 'Must be a number' }],
    sname: [],
  },
})

const { ruleForm, rules } = toRefs(state)

function flush(formName) {
  router.push('/queryStudent')
  ruleFormRef.value.resetFields()
}

function submitForm(formName) {
  ruleFormRef.value.validate((valid) => {
    if (valid) {
      if (state.ruleForm.password === true) {
        state.ruleForm.password = 'fuzzy'
      } else {
        state.ruleForm.password = null
      }
      let url = null
      if (state.ruleForm.sid === null && state.ruleForm.sname === null) {
        url = '/studentList'
      } else {
        url = '/queryStudent/studentList'
      }
      router.push({
        path: url,
        query: {
          ruleForm: state.ruleForm,
        },
      })
    } else {
      return false
    }
  })
}

function resetForm(formName) {
  ruleFormRef.value.resetFields()
}
</script>
