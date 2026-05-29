<template>
  <div>
    <el-form style="width: 60%" :model="ruleForm" :rules="rules" ref="ruleFormRef" label-width="100px" class="demo-ruleForm">
      <el-form-item label="Course Name" prop="cname">
        <el-input v-model="ruleForm.cname" :value="ruleForm.cname"></el-input>
      </el-form-item>
      <el-form-item label="Credits" prop="ccredit">
        <el-input v-model.number="ruleForm.ccredit" :value="ruleForm.ccredit"></el-input>
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
        cid: null,
        cname: null,
        ccredit: null
      },
      rules: {
        cname: [
          { required: true, message: 'Please enter a name', trigger: 'blur' },
        ],
        ccredit: [
          { required: true, message: 'Please enterCredits', trigger: 'change' },
          { type: 'number', message: 'Please enter a number', trigger: 'change'},
        ],
      }
    })

const { ruleForm, rules } = toRefs(state)

if (route.query.cid === undefined) {
      state.ruleForm.cid = 1
    }
    else {
      state.ruleForm.cid = route.query.cid
    }
    axios.get('/course/findById/' + state.ruleForm.cid).then(function (resp) {
      Object.assign(state.ruleForm, resp.data[0])
    })

function submitForm(formName) {
ruleFormRef.value.validate((valid) => {
        if (valid) {
          // Passed frontend validation
          axios.post("/course/updateCourse", state.ruleForm).then(function (resp) {
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
            router.push("/queryCourse")
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