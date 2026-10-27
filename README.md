# Student Course Registration System (JDBC)

Console-based Java application for student-course registration using JDBC + MySQL, with connection pooling (HikariCP).

## Features
- Register students, add courses
- Enroll student in course (transactional, commit/rollback)
- Batch enroll / batch add courses (addBatch/executeBatch)
- View student details, all students, all courses
- Students enrolled in a course
- Courses taken by a student (via stored procedure, CallableStatement)
- Student-course report (SQL JOIN)
- Update / delete student, course, enrollment

## Tech
- Java 17, JDBC, MySQL 8, HikariCP 6.2.1

## Setup
1. Run `mysql -u root -p < src/main/resources/student_setup.sql`
2. Edit `src/main/resources/db.properties` with your DB credentials.
3. Build: `mvn compile`
4. Run: `java -cp target/classes;mysql-connector-j-8.0.33.jar;HikariCP-6.2.1.jar;slf4j-api-1.7.36.jar student_course.RegistrationApp`

## Architecture
- `model/Student`, `model/Course` — entities
- `dao/StudentDAO`, `dao/CourseDAO`, `dao/EnrollmentDAO` — SQL ops
- `service/RegistrationService` — business logic
- `util/DBConnection` — HikariCP pool (reads db.properties)
- `RegistrationApp` — menu / main

## Package layout
```
student_course/
├── RegistrationApp.java
├── model/ { Student, Course }
├── dao/ { StudentDAO, CourseDAO, EnrollmentDAO }
├── service/RegistrationService.java
└── util/DBConnection.java
```