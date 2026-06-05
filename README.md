## Environment Requirements

Install the following software before running this project:

```shell
Node.js v24.16.0
npm 11.13.0
JDK 21
MySQL Server
```

Node.js was installed with `nvm-windows`. Download `nvm-windows` from:

```text
https://github.com/coreybutler/nvm-windows/releases
```

After installing `nvm-windows`, install and switch to the Node.js version used by this project:

```shell
nvm install 24.16.0
nvm use 24.16.0
node -v
npm -v
```

Current verified versions:

```shell
node -v
# v24.16.0

npm -v
# 11.13.0
```

## Frontend

### Run the Project

```shell
cd student_client

npm install

npm run dev
```

### Technology Stack

- Vue
- Vue Router
- Pinia
- Axios
- Element Plus
- @element-plus/icons-vue
- Vite
- sessionStorage

## Backend

### Technology Stack

- Spring Boot Web
- MyBatis Spring Boot Starter
- MySQL Connector/J
- Lombok
- Spring Boot Test
- MyBatis Spring Boot Test

The project includes Maven Wrapper, so Maven does not need to be installed separately on Windows. Run backend commands with `mvnw.cmd`.

### Port Configuration

The frontend API base URL is configured in `student_client/.env`:

```properties
VITE_API_BASE_URL=http://localhost:10086
```

Spring Boot uses port `8080` by default if `server.port` is not configured. Keep the frontend and backend ports consistent. You can either change `student_client/.env` to:

```properties
VITE_API_BASE_URL=http://localhost:8080
```

or add this backend port setting in `src/main/resources/application.properties`:

```properties
server.port=10086
```

## Database Design

Start MySQL Server before running the backend. The backend database connection is configured in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/studentms
spring.datasource.username=root
spring.datasource.password=root
```

Create the `studentms` database and tables before starting the project. Table creation SQL:

```sql
CREATE DATABASE studentms;

USE studentms;

CREATE TABLE `s` (
    `sid` INT AUTO_INCREMENT,
    `sname` VARCHAR(30) NOT NULL,
    `password` VARCHAR(30) NOT NULL,
    PRIMARY KEY (`sid`)
);

CREATE TABLE `c` (
    `cid` INT AUTO_INCREMENT,
    `cname` VARCHAR(30) NOT NULL,
    `ccredit` TINYINT,
    PRIMARY KEY (`cid`)
);

CREATE TABLE `t` (
    `tid` INT AUTO_INCREMENT,
    `tname` VARCHAR(30) NOT NULL,
    `password` VARCHAR(30) NOT NULL,
    PRIMARY KEY (`tid`)
);

CREATE TABLE `ct` (
    `ctid` INT AUTO_INCREMENT,
    `cid` INT,
    `tid` INT,
    `term` CHAR(6) NOT NULL,
    FOREIGN KEY (`cid`) REFERENCES c(`cid`),
    FOREIGN KEY (`tid`) REFERENCES t(`tid`),
    PRIMARY KEY (`ctid`)
);

CREATE TABLE `sct` (
    `sctid` INT AUTO_INCREMENT,
    `sid` INT,
    `cid` INT,
    `tid` INT,
    `grade` FLOAT,
    `term` CHAR(6),
    FOREIGN KEY (`sid`) REFERENCES s(`sid`),
    FOREIGN KEY (`tid`) REFERENCES ct(`tid`),
    FOREIGN KEY (`cid`) REFERENCES ct(`cid`),
    PRIMARY KEY (`sctid`)
);
```

Test data SQL for the `studentms` database.

```sql
USE studentms;

INSERT INTO `s` (`sid`, `sname`, `password`) VALUES
(1, 'Emma Johnson', 's123456'),
(2, 'Liam Smith', 's234567'),
(3, 'Olivia Brown', 's345678'),
(4, 'Noah Williams', 's456789'),
(5, 'Ava Jones', 's567890'),
(6, 'William Miller', 's678901'),
(7, 'Sophia Davis', 's789012'),
(8, 'James Wilson', 's890123'),
(9, 'Isabella Moore', 's901234'),
(10, 'Benjamin Taylor', 's012345');

INSERT INTO `c` (`cid`, `cname`, `ccredit`) VALUES
(1, 'Machine Learning', 4),
(2, 'Deep Learning', 4),
(3, 'Natural Language Processing', 3),
(4, 'Computer Vision', 3),
(5, 'Data Mining', 3),
(6, 'Reinforcement Learning', 3),
(7, 'Artificial Intelligence Ethics', 2),
(8, 'Neural Networks', 4),
(9, 'Big Data Analytics', 3),
(10, 'Robotics', 3);

INSERT INTO `t` (`tid`, `tname`, `password`) VALUES
(1, 'Dr. Michael Brown', 't123456'),
(2, 'Dr. Sarah Wilson', 't234567'),
(3, 'Dr. David Miller', 't345678'),
(4, 'Dr. Jennifer Davis', 't456789'),
(5, 'Dr. Robert Anderson', 't567890'),
(6, 'Dr. Emily Thompson', 't678901'),
(7, 'Dr. Daniel Clark', 't789012'),
(8, 'Dr. Laura Martinez', 't890123'),
(9, 'Dr. Matthew Lewis', 't901234'),
(10, 'Dr. Elizabeth Hall', 't012345');

INSERT INTO `ct` (`ctid`, `cid`, `tid`, `term`) VALUES
(1, 1, 1, '202401'),
(2, 2, 2, '202401'),
(3, 3, 3, '202401'),
(4, 4, 4, '202402'),
(5, 5, 5, '202402'),
(6, 6, 6, '202402'),
(7, 7, 7, '202501'),
(8, 8, 8, '202501'),
(9, 9, 9, '202501'),
(10, 10, 10, '202501');

INSERT INTO `sct` (`sctid`, `sid`, `cid`, `tid`, `grade`, `term`) VALUES
(1, 1, 1, 1, 92.5, '202401'),
(2, 2, 2, 2, 88.0, '202401'),
(3, 3, 3, 3, 91.0, '202401'),
(4, 4, 4, 4, 85.5, '202402'),
(5, 5, 5, 5, 89.0, '202402'),
(6, 6, 6, 6, 94.0, '202402'),
(7, 7, 7, 7, 86.5, '202501'),
(8, 8, 8, 8, 90.0, '202501'),
(9, 9, 9, 9, 87.5, '202501'),
(10, 10, 10, 10, 93.0, '202501');
```
