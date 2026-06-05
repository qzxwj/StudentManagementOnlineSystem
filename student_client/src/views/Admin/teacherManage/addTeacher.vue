<template>
  <div>
    <el-card class="app-panel">
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleFormRef"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item label="Teacher Name" prop="tname">
          <el-input v-model="ruleForm.tname"></el-input>
        </el-form-item>
        <el-form-item label="Initial Password" prop="password">
          <el-input v-model="ruleForm.password" show-password></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm">Submit</el-button>
          <el-button @click="resetForm">Reset</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>
<script setup>
import { getCurrentInstance, reactive, ref, toRefs } from 'vue'

import { useRouter } from 'vue-router'

const router = useRouter()

const { proxy } = getCurrentInstance()

const ruleFormRef = ref(null)

const state = reactive({
  ruleForm: {
    tname: '',
    password: '',
  },
  rules: {
    tname: [
      { required: true, message: 'Please enter a name', trigger: 'blur' },
      { min: 2, max: 5, message: 'Length must be 2 to 5 characters', trigger: 'blur' },
    ],
    password: [{ required: true, message: 'Please enterPassword', trigger: 'change' }],
  },
})

const { ruleForm, rules } = toRefs(state)

function submitForm(formName) {
  ruleFormRef.value.validate((valid) => {
    if (valid) {
      // Passed frontend validation
      if (state.ruleForm.tname === 'admin') {
        proxy.$message({
          showClose: true,
          message: 'Admin cannot be added',
          type: 'error',
        })
        return
      }
      axios.post('/teacher/addTeacher', state.ruleForm).then(function (resp) {
        if (resp.data === true) {
          proxy.$message({
            showClose: true,
            message: 'Inserted successfully',
            type: 'success',
          })
        } else {
          proxy.$message.error('Insert failed. Check the database')
        }
        router.push('/queryTeacher')
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
