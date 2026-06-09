<template>
  <div>
    <div class="page-header">
      <div>
        <p class="page-header__eyebrow">Admin · Teachers</p>
        <h1 class="page-header__title">Find Teachers</h1>
        <p class="page-header__subtitle">Search by ID or name. Toggle fuzzy match for partial lookups.</p>
      </div>
      <div class="page-header__actions">
        <el-button type="primary" class="press" @click="resetForm">
          <el-icon><Refresh /></el-icon><span>Reset search</span>
        </el-button>
      </div>
    </div>

    <el-card class="app-query-card">
      <el-form
        :inline="true"
        :model="ruleForm"
        :rules="rules"
        ref="ruleFormRef"
        class="query-form"
      >
        <el-form-item label="Teacher ID" prop="tid">
          <el-input v-model.number="ruleForm.tid" placeholder="e.g. 1001" clearable />
        </el-form-item>
        <el-form-item label="Teacher name" prop="tname">
          <el-input v-model="ruleForm.tname" placeholder="Search by name" clearable />
        </el-form-item>
        <el-form-item label="Fuzzy match" prop="fuzzy">
          <el-switch v-model="ruleForm.fuzzy" />
        </el-form-item>
      </el-form>
    </el-card>

    <teacher-list :ruleForm="ruleForm"></teacher-list>
  </div>
</template>
<script setup>
import { reactive, ref, toRefs } from 'vue'
import { Refresh } from '@element-plus/icons-vue'
import TeacherList from '@/views/Admin/teacherManage/teacherList.vue'

const ruleFormRef = ref(null)

const state = reactive({
  ruleForm: {
    tid: null,
    tname: null,
    fuzzy: true,
  },
  rules: {
    tid: [{ type: 'number', message: 'Must be a number' }],
    tname: [],
  },
})

const { ruleForm, rules } = toRefs(state)

function resetForm() {
  ruleFormRef.value.resetFields()
}
</script>

<style scoped>
.query-form {
  max-width: none;
  display: flex;
  flex-wrap: wrap;
  gap: 16px 24px;
  align-items: flex-end;
}
.query-form :deep(.el-form-item) {
  margin-bottom: 0;
  margin-right: 0;
}
</style>
