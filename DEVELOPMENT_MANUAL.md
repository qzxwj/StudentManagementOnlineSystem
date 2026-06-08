# StudentManagementOnlineSystem 开发手册

本手册用于指导 StudentManagementOnlineSystem 下一阶段版本迭代。当前项目已经完成第一代版本，后续开发重点放在两个方向：优化前端网页样式，完善后端代码逻辑和功能。

## 1. 项目当前状态

本项目是前后端分离的学生选课管理系统。

- 前端：Vue、Vue Router、Pinia、Axios、Element Plus、Vite。
- 后端：Spring Boot、MyBatis、MySQL、Lombok。
- 数据库：MySQL 数据库 `studentms`。
- 接口调用：前端 Axios 使用 `VITE_API_BASE_URL`，如果该环境变量为空，则使用相对路径请求后端接口。
- Mapper XML：后端 SQL 映射文件位于 `src/main/resources/mapper/`。

主要目录说明：

```text
student_client/        前端 Vue 项目
src/main/java/         后端 Java 代码
src/main/resources/    后端配置文件和 MyBatis XML
src/test/java/         后端测试代码
docs/                  项目辅助文档
```

## 2. 下一阶段开发目标

### 2.1 前端样式优化

下一阶段前端重点是让页面更加统一、清晰、易用。

- 统一登录、注册、管理端、学生端、教师端页面风格。
- 优化表格、表单、按钮、侧边栏、顶部栏等常用组件。
- 减少重复样式，把公共样式集中到 `student_client/src/assets/main.css`。
- 优先使用 Element Plus 现有组件，不随意手写复杂控件。
- 保持页面信息密度适中，后台管理页面以清晰、稳定、易扫描为主。
- 保证移动端和小屏幕下文字不溢出、按钮不重叠、表格不破版。

### 2.2 后端逻辑和功能完善

下一阶段后端重点是让代码职责更清楚，接口行为更稳定。

- Controller 只负责接收请求、参数校验、返回结果。
- Service 负责业务逻辑，不把复杂业务判断放在 Controller。
- Mapper 只负责数据库访问，不写业务判断。
- DTO 只用于请求或响应结构确实不同于实体类的场景，例如注册响应。
- 对新增、删除、更新、查询接口补充必要的参数校验。
- 对失败场景给出清楚的返回值或提示，避免前端只能看到模糊错误。
- 涉及数据库修改时，先确认表结构、外键关系和现有功能影响。

## 3. 本地开发流程

### 3.1 开发前准备

每次开始开发前，先确认当前分支和工作区状态：

```bash
git status
git branch
```

从稳定主分支开始新功能：

```bash
git checkout main
git pull origin main
git checkout -b feature/your-feature-name
```

如果是修复问题，可以使用：

```bash
git checkout -b fix/your-bug-name
```

### 3.2 启动数据库

启动 MySQL Server，并确认存在数据库 `studentms`。后端数据库连接配置位于：

```text
src/main/resources/application.properties
```

当前后端使用的主要配置包括：

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/studentms?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=202316@xwj
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
mybatis.mapper-locations=classpath:mapper/*.xml
mybatis.type-aliases-package=com.example.studentmanagementonlinesystem.entity
```

如果在其他电脑运行项目，需要根据本机 MySQL 用户名和密码调整配置。

### 3.3 启动后端

项目包含 Maven Wrapper，可以不依赖全局 Maven。

运行测试：

```bash
sh mvnw test
```

启动后端：

```bash
sh mvnw spring-boot:run
```

Spring Boot 默认端口为 `8080`，除非在 `application.properties` 中额外配置 `server.port`。

### 3.4 启动前端

进入前端目录：

```bash
cd student_client
```

首次运行或依赖变化后安装依赖：

```bash
npm install
```

启动开发服务器：

```bash
npm run dev
```

构建前端：

```bash
npm run build
```

如果接口请求没有命中后端，优先检查 `student_client/.env` 中的 `VITE_API_BASE_URL`，以及 Vite 代理或后端端口是否一致。

## 4. 前端开发规范

### 4.1 页面和路由

- 路由配置集中维护在 `student_client/src/router/index.js`。
- 新增页面时，按角色放入对应目录，例如 `Admin`、`Student`、`Teacher`。
- 页面命名要表达功能，不使用模糊名称。
- 登录后依赖 `sessionStorage` 保存用户类型和用户 ID，修改相关逻辑时要同步检查路由守卫。

### 4.2 组件复用

- 公共头部、侧边栏、信息卡片、退出登录等组件放在 `student_client/src/components/`。
- 多个页面重复出现的布局或操作，应优先提取为组件。
- 组件内部只处理自己的显示和交互，不承担跨页面业务流程。

### 4.3 样式维护

- 全局基础样式优先放在 `student_client/src/assets/main.css`。
- 页面独有样式可以写在对应 `.vue` 文件的 `<style scoped>` 中。
- 避免大量重复颜色、间距、字体大小；优先复用已有 CSS 变量和通用类。
- 后台管理页面不要做过度装饰，重点保证表格、表单、操作按钮清楚易用。
- 按钮文案、表单标签、提示消息尽量统一中英文风格，避免同一页面混乱。

### 4.4 Axios 请求

- Axios 插件位于 `student_client/src/plugins/axios.js`。
- 请求路径优先使用后端已有前缀，例如 `/student`、`/teacher`、`/course`、`/courseTeacher`、`/SCT`。
- 新增接口后，要同步检查前端请求路径、请求方法、请求体字段和后端 Controller 是否一致。
- 前端收到失败响应时，应给用户明确提示，不只在控制台输出。

## 5. 后端开发规范

### 5.1 分层职责

后端代码按以下职责维护：

- `controller`：处理 HTTP 请求和响应。
- `service`：定义业务接口。
- `service/impl`：实现业务逻辑。
- `mapper`：定义 MyBatis 数据访问接口。
- `entity`：对应数据库表或查询结果对象。
- `dto`：用于特定请求或响应结构，不直接代表数据库表。

新增功能时，不要把所有逻辑都写在 Controller 中。Controller 应该尽量简洁，复杂判断放入 Service。

### 5.2 接口返回值

- 查询接口可以返回实体、实体列表或查询结果对象。
- 新增、删除、更新接口当前可以继续返回 `boolean`，后续如需更清楚的错误原因，可以逐步改为统一响应对象。
- 注册接口保留 `RegisterResponse`，避免直接返回带有 `password` 的实体对象。
- 对外返回数据时，不返回密码等敏感字段。

### 5.3 数据库和 Mapper

- Java Mapper 接口位于 `src/main/java/com/example/studentmanagementonlinesystem/mapper/`。
- XML SQL 文件位于 `src/main/resources/mapper/`。
- Mapper 方法名、XML `id`、参数类型和返回类型必须保持一致。
- 修改数据库表字段后，要同步修改 Entity、Mapper XML、Service 和前端展示字段。
- 涉及 `s`、`t`、`c`、`ct`、`sct` 等关联表时，要先确认外键关系，避免删除或更新造成数据不一致。

### 5.4 参数校验和错误处理

- 对新增和更新接口检查必要字段是否为空。
- 对登录接口检查用户是否存在，再检查密码。
- 对根据 ID 查询、删除、更新的接口，检查 ID 是否为空或非法。
- 对选课、退课、成绩录入等业务，检查重复选课、容量限制、课程是否存在、教师是否开课等条件。
- 不把异常直接暴露给前端页面，应返回前端能理解的结果。

## 6. Git 迭代规范

本项目采用轻量单人开发流程。

### 6.1 分支规则

- `main`：稳定可运行版本。
- `feature/<name>`：新功能开发。
- `fix/<name>`：问题修复。

开发新功能：

```bash
git checkout main
git pull origin main
git checkout -b feature/your-feature-name
```

提交修改：

```bash
git status
git add .
git commit -m "feat: describe your change"
```

合并回主分支：

```bash
git checkout main
git pull origin main
git merge feature/your-feature-name
git push origin main
```

### 6.2 版本标签

稳定版本使用 tag 标记：

```bash
git tag -a v1.1.0 -m "Version 1.1.0"
git push origin v1.1.0
```

版本建议：

- 功能迭代：`v1.1.0`、`v1.2.0`。
- 修复版本：`v1.0.1`、`v1.0.2`。
- 大版本升级：`v2.0.0`。

### 6.3 回滚方式

查看历史：

```bash
git log --oneline --decorate --graph --all
git tag
```

临时查看旧版本：

```bash
git checkout v1.0.0
```

从旧版本创建修复分支：

```bash
git checkout -b fix/from-v1.0.0 v1.0.0
```

撤销主分支上的某次提交，优先使用：

```bash
git checkout main
git revert <commit>
git push origin main
```

不要随意使用 `git reset --hard` 或强制推送，除非明确知道会改写远端历史。

## 7. 开发检查清单

### 7.1 开发前

- 已从 `main` 拉取最新代码。
- 已创建 `feature/*` 或 `fix/*` 分支。
- MySQL 已启动，`studentms` 数据库可用。
- 后端配置中的数据库用户名和密码适合当前电脑。
- 前端依赖已安装。

### 7.2 开发中

- 每完成一个小功能就及时提交。
- 不提交 `.DS_Store`、`node_modules/`、`dist/`、`target/`、`.idea/`。
- 前端修改后检查页面是否有文字重叠、按钮错位、表格溢出。
- 后端修改后检查 Controller、Service、Mapper、XML 是否同步。
- 修改接口后同步检查前端请求参数和响应处理。

### 7.3 合并或发布前

后端测试：

```bash
sh mvnw test
```

前端构建：

```bash
cd student_client
npm run build
```

Git 状态检查：

```bash
git status
```

发布稳定版本前确认：

- 登录功能正常。
- 学生端核心功能正常。
- 教师端核心功能正常。
- 管理员端核心功能正常。
- 课程、开课、选课、成绩相关流程至少手动走通一次。
- `main` 分支没有未提交修改。
- 必要时创建新的版本 tag。

## 8. 下一阶段建议开发顺序

建议按照从低风险到高价值的顺序推进。

1. 统一前端全局样式、颜色、间距和按钮风格。
2. 整理登录、注册、首页、侧边栏等通用页面体验。
3. 优化管理端表格和查询页面。
4. 完善注册、登录、更新、删除等后端参数校验。
5. 完善选课、退课、开课、成绩管理中的业务规则。
6. 根据需要补充更具体的后端测试和前端构建检查。

每次只做一个清晰主题，完成后测试、提交，再进入下一个主题。
