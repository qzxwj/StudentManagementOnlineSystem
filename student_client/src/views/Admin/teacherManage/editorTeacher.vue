<template>
  <div>
    <el-form style="width: 60%" :model="ruleForm" :rules="rules" ref="ruleFormRef" label-width="100px" class="demo-ruleForm">
      <el-form-item label="Teacher Name" prop="tname">
        <el-input v-model="ruleForm.tname" :value="ruleForm.tname"></el-input>
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
        tid: null,
        tname: null,
        password: null
      },
      rules: {
        tname: [
          { required: true, message: 'Please enter a name', trigger: 'blur' },
          { min: 2, max: 5, message: 'Length must be 2 to 5 characters', trigger: 'blur' }
        ],
        password: [
          { required: true, message: 'Please enterPassword', trigger: 'change' }
        ],
      }
    })

const { ruleForm, rules } = toRefs(state)

if (route.query.tid === undefined) {
      state.ruleForm.tid = 6
    }
    else {
      state.ruleForm.tid = route.query.tid
    }
    axios.get('/teacher/findById/' + state.ruleForm.tid).then(function (resp) {
      Object.assign(state.ruleForm, resp.data)
    })

function submitForm(formName) {
ruleFormRef.value.validate((valid) => {
        if (valid) {
          // Passed frontend validation
          if (state.ruleForm.tname === 'admin') {
            proxy.$message({
              showClose: true,
              message: 'Admin HomeEdit',
              type: 'error'
            });
            router.push('/queryTeacher')
            return
          }
          axios.post("/teacher/updateTeacher", state.ruleForm).then(function (resp) {
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
            router.push("/queryTeacher")
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