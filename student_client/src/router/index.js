import { createRouter, createWebHistory } from 'vue-router'
import login from '../views/login/index.vue'
import register from '@/views/register/index.vue'
import admin from '../views/Admin/index.vue'
import adminHome from '../views/Admin/home.vue'
import studentManage from '../views/Admin/studentManage/index.vue'
import studentList from '@/views/Admin/studentManage/studentList.vue'
import editorStudent from '@/views/Admin/studentManage/editorStudent.vue'
import teacherManage from '@/views/Admin/teacherManage/index.vue'
import editorTeacher from '@/views/Admin/teacherManage/editorTeacher.vue'
import courseManage from '@/views/Admin/courseManage/index.vue'
import addCourse from '@/views/Admin/courseManage/addCourse.vue'
import teacher from '@/views/Teacher/index.vue'
import queryTeacher from '@/views/Admin/teacherManage/queryTeacher.vue'
import student from '@/views/Student/index.vue'
import editorCourse from '@/views/Admin/courseManage/editorCourse.vue'
import courseList from '@/views/Admin/courseManage/courseList.vue'
import queryCourse from '@/views/Admin/courseManage/queryCourse.vue'
import offerCourse from '@/views/Teacher/offerCourse.vue'
import teacherHome from '@/views/Teacher/home.vue'
import setCourse from '@/views/Teacher/setCourse.vue'
import studentHome from '@/views/Student/home.vue'
import myOfferCourse from '@/views/Teacher/myOfferCourse.vue'
import CourseTeacherManage from '@/views/Admin/selectCourseManage/index.vue'
import queryCourseTeacher from '@/views/Admin/selectCourseManage/queryCourseTeacher.vue'
import studentSelectCourseManage from '@/views/Student/selectCourse/index.vue'
import selectCourse from '@/views/Student/selectCourse/selectCourse.vue'
import querySelectedCourse from '@/views/Student/selectCourse/querySelectedCourse.vue'
import studentCourseGrade from '@/views/Student/courseGrade/index.vue'
import queryCourseGrade from '@/views/Student/courseGrade/queryCourseGrade.vue'
import queryGradeCourse from '@/views/Admin/gradeCourseManage/queryGradeCourse.vue'
import editorGradeCourse from '@/views/Admin/gradeCourseManage/editorGradeCourse.vue'
import teacherGradeCourseManage from '@/views/Teacher/teacherGradeCourseManage/index.vue'
import teacherQueryGradeCourse from '@/views/Teacher/teacherGradeCourseManage/teacherQueryGradeCourse.vue'
import teacherEditorGradeCourse from '@/views/Teacher/teacherGradeCourseManage/teacherEditorGradeCourse.vue'
import updateInfo from '@/components/updateInfo.vue'

const routes = [
  {
    // Default home page
    path: '/',
    name: 'index',
    component: login,
    redirect: '/login',
  },
  {
    // Login page
    path: '/login',
    name: 'login',
    component: login,
  },
  {
    path: '/studentRegister',
    name: 'studentRegister',
    component: register,
    meta: { registerType: 'student' },
  },
  {
    path: '/teacherRegister',
    name: 'teacherRegister',
    component: register,
    meta: { registerType: 'teacher' },
  },
  {
    // Admin routes
    path: '/admin',
    name: 'admin',
    redirect: '/adminHome',
    component: admin,
    meta: { requireAuth: true },
    children: [
      {
        path: '/adminHome',
        name: 'adminHomeLayout',
        component: adminHome,
        meta: { requireAuth: true, title: 'Hi! admin' },
        children: [
          {
            path: '/adminHome',
            name: 'adminHome',
            component: adminHome,
            meta: { requireAuth: true, title: 'Admin Home' },
          },
        ],
      },
      {
        path: '/studentManage',
        name: 'adminStudentManage',
        component: studentManage,
        meta: { requireAuth: true, title: 'Student Management' },
        children: [
          {
            path: '/studentList',
            name: 'adminStudentList',
            component: studentList,
            meta: { requireAuth: true, title: 'Student Management' },
          },
          {
            path: '/editorStudent',
            name: 'adminEditorStudent',
            component: editorStudent,
            meta: { requireAuth: true, title: 'EditStudent' },
          },
        ],
      },
      {
        path: '/teacherManage',
        name: 'adminTeacherManage',
        component: teacherManage,
        meta: { requireAuth: true, title: 'Teacher Management' },
        children: [
          {
            path: '/queryTeacher',
            name: 'adminQueryTeacher',
            component: queryTeacher,
            meta: { requireAuth: true, title: 'Teacher Management' },
            children: [],
          },
          {
            path: '/editorTeacher',
            name: 'adminEditorTeacher',
            component: editorTeacher,
            meta: { requireAuth: true, title: 'EditTeacher' },
          },
        ],
      },
      {
        path: '/courseManage',
        name: 'adminCourseManage',
        component: courseManage,
        meta: { requireAuth: true, title: 'Course Management' },
        children: [
          {
            path: '/addCourse',
            name: 'adminAddCourse',
            component: addCourse,
            meta: { requireAuth: true, title: 'Add' },
          },
          {
            path: '/queryCourse',
            name: 'adminQueryCourse',
            component: queryCourse,
            meta: { requireAuth: true, title: 'Search' },
            children: [
              {
                path: '/courseList',
                name: 'adminCourseList',
                component: courseList,
                meta: { requireAuth: true, title: 'Course Management' },
              },
            ],
          },
          {
            path: '/editorCourse',
            name: 'adminEditorCourse',
            component: editorCourse,
            meta: { requireAuth: true, title: 'Edit' },
          },
        ],
      },
      {
        path: '/CourseTeacher',
        name: 'adminCourseTeacherManage',
        component: CourseTeacherManage,
        meta: { requireAuth: true, title: 'Course-Teacher' },
        children: [
          {
            path: '/queryCourseTeacher',
            name: 'adminQueryCourseTeacher',
            component: queryCourseTeacher,
            meta: { requireAuth: true, title: 'Course-Teacher' },
          },
        ],
      },
      {
        name: 'adminGradeCourseManage',
        path: '/gradeCourseManage',
        component: studentManage,
        meta: { requireAuth: true, title: 'Grade Management' },
        children: [
          {
            path: '/queryGradeCourse',
            name: 'adminQueryGradeCourse',
            component: queryGradeCourse,
            meta: { requireAuth: true, title: 'GradeSearch' },
          },
          {
            path: '/editorGradeCourse',
            name: 'adminEditorGradeCourse',
            component: editorGradeCourse,
            meta: { requireAuth: true, title: 'Edit' },
          },
        ],
      },
    ],
  },
  {
    path: '/teacher',
    name: 'teacher',
    component: teacher,
    redirect: '/teacherHome',
    meta: { requireAuth: true },
    children: [
      {
        path: '/teacherHome',
        name: 'teacherHomeLayout',
        meta: { requireAuth: true, title: 'Hi! teacher' },
        component: teacherHome,
        children: [
          {
            path: '/teacherHome',
            name: 'teacherHome',
            meta: { requireAuth: true, title: 'Teacher Management' },
            component: teacherHome,
          },
        ],
      },
      {
        path: '/updateInfo',
        name: 'teacherEdit',
        component: updateInfo,
        meta: { requireAuth: true, title: 'TeacherEdit' },
        children: [
          {
            path: '/updateInfoHome',
            name: 'teacherUpdateInfoHome',
            component: updateInfo,
            meta: { requireAuth: true, title: 'EditTeacher Management' },
          },
        ],
      },
      {
        path: '/courseManage',
        name: 'teacherCourseManage',
        meta: { requireAuth: true, title: 'Course Management' },
        component: setCourse,
        children: [
          {
            path: '/myOfferCourse',
            name: 'teacherMyOfferCourse',
            component: myOfferCourse,
            meta: { requireAuth: true, title: 'Manage Course' },
          },
          {
            path: '/offerCourse',
            name: 'teacherOfferCourse',
            component: offerCourse,
            meta: { requireAuth: true, title: 'Offer Course' },
          },
        ],
      },
      {
        name: 'teacherGradeCourseManage',
        path: '/teacherQueryGradeCourseManage',
        component: teacherGradeCourseManage,
        meta: { requireAuth: true, title: 'TeacherGrade Management' },
        children: [
          {
            path: '/teacherQueryGradeCourseManage',
            name: 'teacherQueryGradeCourse',
            component: teacherQueryGradeCourse,
            meta: { requireAuth: true, title: 'Grade Management' },
          },
          {
            path: '/teacherEditorGradeCourse',
            name: 'teacherEditorGradeCourse',
            component: teacherEditorGradeCourse,
            meta: { requireAuth: true, title: 'EditGrade' },
          },
        ],
      },
    ],
  },
  {
    path: '/student',
    name: 'student',
    component: student,
    redirect: '/studentHome',
    meta: { requireAuth: true },
    children: [
      {
        path: '/studentHome',
        name: 'studentHome',
        component: studentHome,
        meta: { requireAuth: true, title: 'Student Home' },
      },
      {
        path: '/updateInfo',
        name: 'studentEdit',
        component: updateInfo,
        meta: { requireAuth: true, title: 'StudentEdit' },
        children: [
          {
            path: '/updateInfoHome',
            name: 'studentUpdateInfoHome',
            component: updateInfo,
            meta: { requireAuth: true, title: 'Edit Student' },
          },
        ],
      },
      {
        path: '/studentSelectCourseManage',
        name: 'studentSelectCourseManage',
        redirect: '/studentSelectCourse',
        component: studentSelectCourseManage,
        meta: { requireAuth: true, title: 'Course Management' },
        children: [
          {
            path: '/studentSelectCourse',
            name: 'studentSelectCourse',
            component: selectCourse,
            meta: { requireAuth: true, title: 'Course Management' },
          },
          {
            path: '/querySelectedCourse',
            name: 'studentQuerySelectedCourse',
            component: querySelectedCourse,
            meta: { requireAuth: true, title: 'Search' },
          },
        ],
      },
      {
        path: '/courseGrade',
        name: 'studentCourseGrade',
        redirect: '/queryCourseGrade',
        component: studentCourseGrade,
        meta: { requireAuth: true, title: 'Grade Management' },
        children: [
          {
            path: '/queryCourseGrade',
            name: 'studentQueryCourseGrade',
            component: queryCourseGrade,
            meta: { requireAuth: true, title: 'Grade Search' },
          },
        ],
      },
    ],
  },
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})

export default router

/*
  Session settings:
    1. token
    2. name
    3. type
    4. tid
    5. sid
    5. System info info
 */
router.beforeEach((to, from, next) => {
  if (to.matched.some((record) => record.meta.requireAuth)) {
    // Not logged in page
    if (sessionStorage.getItem('token') === 'true') {
      // Check whether a local token exists
      next()
    } else {
      // Not logged in,Not logged in page
      next({
        path: '/login',
        query: { redirect: to.fullPath },
      })
    }
  } else {
    // Not logged in page,Not logged in,Management
    if (sessionStorage.getItem('token') === 'true') {
      const t = sessionStorage.getItem('type')
      next('/' + t)
    } else {
      next()
    }
  }
})
