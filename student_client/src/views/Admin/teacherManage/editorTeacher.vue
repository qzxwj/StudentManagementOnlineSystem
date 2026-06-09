<template>
  <div>
    <div class="page-header">
      <div>
        <p class="page-header__eyebrow">Admin · Teachers</p>
        <h1 class="page-header__title">Edit Teacher</h1>
        <p class="page-header__subtitle">Update the teacher's name or reset their password.</p>
      </div>
    </div>

    <el-card class="app-panel edit-panel">
      <el-form
        :model="ruleForm"
        :rules="rules"
        ref="ruleFormRef"
        label-position="top"
        class="edit-form"
      >
        <el-form-item label="Teacher name" prop="tname">
          <el-input v-model="ruleForm.tname" :value="ruleForm.tname" />
        </el-form-item>
        <el-form-item label="Initial password" prop="password">
          <el-input v-model="ruleForm.password" :value="ruleForm.password" show-password />
        </el-form-item>
      </el-form>
      <div class="glass-bar">
        <el-button text @click="router.push('/queryTeacher')">Cancel</el-button>
        <el-button @click="resetForm">Reset</el-button>
        <el-button type="primary" class="press" @click="submitForm">Save changes</el-button>
      </div>
    </el-card>
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
    password: null,
  },
  rules: {
    tname: [
      { required: true, message: 'Please enter a name', trigger: 'blur' },
      { min: 2, max: 5, message: 'Length must be 2 to 5 characters', trigger: 'blur' },
    ],
    password: [{ required: true, message: 'Please enter a password', trigger: 'change' }],
  },
})

const { ruleForm, rules } = toRefs(state)

if (route.query.tid === undefined) {
  state.ruleForm.tid = 6
} else {
  state.ruleForm.tid = route.query.tid
}
axios.get('/teacher/findById/' + state.ruleForm.tid).then(function (resp) {
  Object.assign(state.ruleForm, resp.data)
})

function submitForm() {
  ruleFormRef.value.validate((valid) => {
    if (valid) {
      if (state.ruleForm.tname === 'admin') {
        proxy.$message({ message: 'Cannot edit the built-in admin account.', type: 'error' })
        router.push('/queryTeacher')
        return
      }
      axios.post('/teacher/updateTeacher', state.ruleForm).then(function (resp) {
        if (resp.data === true) {
          proxy.$message({ message: 'Teacher updated', type: 'success' })
        } else {
          proxy.$message({ message: 'Update failed. Please contact the administrator.', type: 'error' })
        }
        router.push('/queryTeacher')
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

<style scoped>
.edit-panel {
  padding: 32px;
}
.edit-form {
  max-width: 720px;
}
</style>
