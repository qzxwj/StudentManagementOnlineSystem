<template>
  <div>
    <el-form style="width: 60%" :model="ruleForm" :rules="rules" ref="ruleFormRef" label-width="100px" class="demo-ruleForm">
      <el-form-item label="Student Name" prop="sname">
        <el-input v-model="ruleForm.sname" :value="ruleForm.sname"></el-input>
      </el-form-item>
      <el-form-item label="Initial Password" prop="password">
        <el-input v-model="ruleForm.password" :value="ruleForm.password" show-password></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm">Submit</el-button>
        <el-button @click="resetForm">Reset</el-button>
        <el-button @click="test">test</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script setup>
import { getCurrentInstance, reactive, ref, toRefs } from 'vue'

import { useRoute, useRouter } from 'vue-router'

const router = useRouter()
const route = useRoute()

const { proxy } = getCurrentInstance()

const ruleFormRef = ref(null)

const state = reactive({
      ruleForm: {
        sid: null,
        sname: null,
        password: null
      },
      rules: {
        sname: [
          { required: true, message: 'Please enter a name', trigger: 'blur' },
          { min: 2, max: 5, message: 'Length must be 2 to 5 characters', trigger: 'blur' }
        ],
        password: [
          { required: true, message: 'Please enterPassword', trigger: 'change' }
        ],
      }
    })

const { ruleForm, rules } = toRefs(state)

if (route.query.sid === undefined) {
      state.ruleForm.sid = 1
    }
    else {
      state.ruleForm.sid = route.query.sid
    }
    axios.get('/student/findById/' + state.ruleForm.sid).then(function (resp) {
      Object.assign(state.ruleForm, resp.data)
    })

function submitForm(formName) {
ruleFormRef.value.validate((valid) => {
        if (valid) {
          // Passed frontend validation
          axios.post("/student/updateStudent", state.ruleForm).then(function (resp) {
            if (resp.data === true) {
              proxy.$message({
                showClose: true,
                message: 'Edit',
                type: 'success'
              });
            }
            else {
              proxy.$message.error('Edit,Management');
            }
            router.push("/studentList")
          })
        } else {
          return false;
        }
      });
}

function resetForm(formName) {
ruleFormRef.value.resetFields();
}

function test() {
}
</script>