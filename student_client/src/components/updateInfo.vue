<template>
  <el-container>
    <el-main>
      <el-card>
        <el-form
          :model="ruleForm"
          status-icon
          :rules="rules"
          ref="ruleFormRef"
          label-width="100px"
          class="demo-ruleForm"
        >
          <el-form-item label="Name" prop="name">
            <el-input v-model.name="ruleForm.name" :value="ruleForm.name"></el-input>
          </el-form-item>
          <el-form-item label="Password" prop="pass">
            <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="Confirm Password" prop="checkPass">
            <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm">Submit</el-button>
            <el-button @click="resetForm">Reset</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </el-main>
  </el-container>
</template>
<script setup>
import { getCurrentInstance, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const ruleFormRef = ref(null)
const { proxy } = getCurrentInstance()

const ruleForm = reactive({
  pass: '',
  checkPass: '',
  name: sessionStorage.getItem('name'),
})

const validatePass = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('Please enterPassword'))
  } else {
    if (ruleForm.checkPass !== '') {
      ruleFormRef.value.validateField('checkPass')
    }
    callback()
  }
}

const validatePass2 = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('Password'))
  } else if (value !== ruleForm.pass) {
    callback(new Error('Password error!'))
  } else {
    callback()
  }
}

const rules = reactive({
  pass: [{ validator: validatePass, trigger: 'blur' }],
  checkPass: [{ validator: validatePass2, trigger: 'blur' }],
  name: [{ require: true, message: 'Name cannot be empty', trigger: 'blur' }],
})

function submitForm() {
  ruleFormRef.value.validate((valid) => {
    if (valid) {
      sessionStorage.setItem('name', ruleForm.name)
      const type = sessionStorage.getItem('type')
      let form = null
      let ss = null
      if (type === 'student') {
        ss = 'Student'
        form = {
          sid: sessionStorage.getItem('sid'),
          sname: ruleForm.name,
          password: ruleForm.pass,
        }
      } else {
        ss = 'Teacher'
        form = {
          tid: sessionStorage.getItem('tid'),
          tname: ruleForm.name,
          password: ruleForm.pass,
        }
      }

      axios.post('/' + type + '/update' + ss, form).then(function (resp) {
        if (resp.data === true) {
          proxy.$message({
            showClose: true,
            message: 'Edit',
            type: 'success',
          })
        } else {
          proxy.$message.error('Edit,Contact the administrator')
        }
        router.push('/' + type + 'Home')
      })
    } else {
      return false
    }
  })
}

function resetForm() {
  ruleFormRef.value.resetFields()
}
</script>
