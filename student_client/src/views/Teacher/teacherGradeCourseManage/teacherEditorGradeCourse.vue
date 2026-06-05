<template>
  <div>
    <el-card>
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleFormRef"
        label-width="100px"
        class="demo-ruleForm"
      >
        <el-form-item label="Course Name" prop="cname">
          <el-input v-model="ruleForm.cname" :value="ruleForm.cname" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="Teacher Name" prop="tname">
          <el-input v-model="ruleForm.tname" :value="ruleForm.tname" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="Student Name" prop="sname">
          <el-input v-model="ruleForm.sname" :value="ruleForm.sname" :disabled="true"></el-input>
        </el-form-item>
        <el-form-item label="Grade" prop="grade">
          <el-input v-model.number="ruleForm.grade" :value="ruleForm.grade"></el-input>
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
import { getCurrentInstance, reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const router = useRouter()
const route = useRoute()
const { proxy } = getCurrentInstance()
const ruleFormRef = ref(null)

const checkGrade = (rule, value, callback) => {
  if (!value) {
    return callback(new Error('Grade Management'))
  }
  if (!Number.isInteger(value)) {
    callback(new Error('Please enter a numeric value'))
  } else {
    if (value > 100 || value < 0) {
      callback(new Error('Grade Management [0 - 100]'))
    } else {
      callback()
    }
  }
}

const ruleForm = reactive({
  cid: null,
  cname: null,
  grade: null,
  sid: null,
  sname: null,
  tid: null,
  tname: null,
  term: null,
})

const rules = reactive({
  grade: [
    { required: true, message: 'Please enterCredits', trigger: 'change' },
    { type: 'number', message: 'Please enter a number', trigger: 'change' },
    { validator: checkGrade, trigger: 'blur' },
  ],
})

ruleForm.cid = route.query.cid
ruleForm.tid = route.query.tid
ruleForm.sid = route.query.sid
ruleForm.term = route.query.term
axios
  .get(
    '/SCT/findById/' + ruleForm.sid + '/' + ruleForm.cid + '/' + ruleForm.tid + '/' + ruleForm.term,
  )
  .then(function (resp) {
    Object.assign(ruleForm, resp.data)
  })

function submitForm() {
  ruleFormRef.value.validate((valid) => {
    if (valid) {
      // Passed frontend validation
      const sid = ruleForm.sid
      const cid = ruleForm.cid
      const tid = ruleForm.tid
      const term = ruleForm.term
      const grade = ruleForm.grade
      axios
        .get('/SCT/updateById/' + sid + '/' + cid + '/' + tid + '/' + term + '/' + grade)
        .then(function (resp) {
          if (resp.data === true) {
            proxy.$message({
              showClose: true,
              message: 'Edit',
              type: 'success',
            })
          } else {
            proxy.$message.error('Edit,Management')
          }
          router.push('/queryGradeCourse')
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
