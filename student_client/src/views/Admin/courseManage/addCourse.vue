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
        <el-form-item label="Course Name" prop="cname">
          <el-input v-model="ruleForm.cname"></el-input>
        </el-form-item>
        <el-form-item label="Credits" prop="ccredit">
          <el-input v-model.number="ruleForm.ccredit"></el-input>
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
    cname: null,
    ccredit: null,
  },
  rules: {
    cname: [{ required: true, message: 'Please enter a name', trigger: 'blur' }],
    ccredit: [
      { required: true, message: 'Please enterCredits', trigger: 'change' },
      { type: 'number', message: 'Please enter a number', trigger: 'blur' },
    ],
  },
})

const { ruleForm, rules } = toRefs(state)

function submitForm(formName) {
  ruleFormRef.value.validate((valid) => {
    if (valid) {
      // Passed frontend validation

      axios.post('/course/save', state.ruleForm).then(function (resp) {
        if (resp.data === true) {
          proxy.$message({
            showClose: true,
            message: 'Inserted successfully',
            type: 'success',
          })
        } else {
          proxy.$message.error('Insert failed. Check the database')
        }
        router.push('/queryCourse')
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
