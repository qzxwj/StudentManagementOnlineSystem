<template>
  <div>
    <el-container>
      <el-header >
        <div style="text-align: center; font-size: 25px; font-weight: bolder">
          <el-icon style="margin-right: 25px"><House /></el-icon>
          Course Selection Management System
        </div>
      </el-header>
      <el-main>
        <el-card class="login-module" shadow="hover">
          <template #header>
          <div class="clearfix">
            <span style="text-align: center; font-size: 20px; font-family: 'Microsoft YaHei'">
              <p><el-icon style="margin-right: 18px"><OfficeBuilding /></el-icon>Login</p>
            </span>
          </div>
          </template>
          <div>
            <el-form :model="ruleForm" :rules="rules" ref="ruleFormRef" label-width="100px" class="demo-ruleForm">
              <el-form-item label="User ID" prop="id">
                <el-input v-model.number="ruleForm.id" :prefix-icon="Lollipop"></el-input>
              </el-form-item>
              <el-form-item label="Password" prop="password">
                <el-input v-model="ruleForm.password" placeholder="Please enterPassword" show-password :prefix-icon="IceCreamRound"></el-input>
              </el-form-item>
              <el-form-item label="User Type" prop="type">
                <el-radio-group v-model="ruleForm.type">
                  <el-radio label="student" value="student">Student</el-radio>
                  <el-radio label="teacher" value="teacher">Teacher</el-radio>
                  <el-radio label="admin" value="admin">admin</el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="submitForm">Login</el-button>
                <el-button @click="resetForm">Reset</el-button>
                <el-button @click="test">test</el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-card>
      </el-main>
    </el-container>
  </div>
</template>

<script setup>
import { getCurrentInstance, reactive, ref, toRefs } from 'vue'
import { IceCreamRound, Lollipop } from '@element-plus/icons-vue'

import { useRouter } from 'vue-router'

const router = useRouter()

const { proxy } = getCurrentInstance()

const ruleFormRef = ref(null)

const state = reactive({
      ruleForm: {
        id: null,
        password: null,
        type: null,
      },
      rules: {
        id: [
          { required: true, message: 'Please enterUser ID', trigger: 'blur' },
          { type: 'number', message: 'Please enter a number', trigger: 'blur' },
        ],
        password: [
          { required: true, message: 'Please enterPassword', trigger: 'blur' }
        ],
        type: [
          { required: true, message: 'Please select', trigger: 'change' }
        ],
      }
    })

const { ruleForm, rules } = toRefs(state)

function submitForm(formName) {
ruleFormRef.value.validate((valid) => {
        if (valid) {
          let check = false
          let name = null

          axios.get('/info/getCurrentTerm').then(function (resp) {
            sessionStorage.setItem("currentTerm", resp.data)
          })

          axios.get('/info/getForbidCourseSelection').then(function (resp) {
            sessionStorage.setItem("ForbidCourseSelection", resp.data)
          })

          if (state.ruleForm.type === 'admin' || state.ruleForm.type === 'teacher') {
            let form = {tid: state.ruleForm.id, password: state.ruleForm.password}
            axios.post("/teacher/login", form).then(function (resp) {
              check = resp.data
              if (check === true) {
                axios.get("/teacher/findById/" + state.ruleForm.id).then(function (resp) {
                  name = resp.data.tname

                  sessionStorage.setItem("token", 'true')
                  sessionStorage.setItem("type", state.ruleForm.type)
                  sessionStorage.setItem("name", name)
                  sessionStorage.setItem("tid", resp.data.tid)


                  if (state.ruleForm.type === 'admin' && name === 'admin') {
                    proxy.$message({
                      showClose: true,
                      message: 'Login page,Welcome ' + name + '!',
                      type: 'success'
                    });
                    router.push('/admin')
                  }
                  else if(state.ruleForm.type === 'teacher' && name !== 'admin') {
                    proxy.$message({
                      showClose: true,
                      message: 'Login page,Welcome ' + name + '!',
                      type: 'success'
                    });
                    router.push('/teacher')
                  }
                  else {
                    proxy.$message({
                      showClose: true,
                      message: 'admin Login page,Not logged in page',
                      type: 'error'
                    });
                  }
                })
              }
              else {
                proxy.$message({
                  showClose: true,
                  message: 'Login page,Password',
                  type: 'error'
                });
              }
            })
          }
          else if (state.ruleForm.type === 'student') {
            let form = {sid: state.ruleForm.id, password: state.ruleForm.password}
            axios.post("/student/login", form).then(function (resp) {
              check = resp.data
              if (check === true) {
                axios.get("/student/findById/" + state.ruleForm.id).then(function (resp) {
                  name = resp.data.sname

                  sessionStorage.setItem("token", 'true')
                  sessionStorage.setItem("type", state.ruleForm.type)
                  sessionStorage.setItem("name", name)
                  sessionStorage.setItem("sid", resp.data.sid)

                  proxy.$message({
                    showClose: true,
                    message: 'Login page,Welcome ' + name + '!',
                    type: 'success'
                  });


                  // 3. Route redirect
                  router.push({
                    path: '/' + state.ruleForm.type,
                    query: {}
                  })
                })
              }
              else {
                proxy.$message({
                  showClose: true,
                  message: 'Password error,Contact the administrator',
                  type: 'error'
                });
              }
            })
          }
          else {
          }
        } else {
          return false;
        }
      });
}

function resetForm(formName) {
ruleFormRef.value.resetFields();
}

function test(forName) {
}
</script>

<style>
.login-module {
  /*width: 380px;*/
  /*height: 325px;*/
  margin-top: 60px;
  /*border: none;*/
  position: absolute;
  right: 500px;
  text-align: center;
  width: 30%;
}
.el-header {
  background-color: #B3C0D1;
  color: #333;
  line-height: 60px;
}
</style>
