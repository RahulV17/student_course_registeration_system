-- Student Course Registration System - Database Setup Script
-- Run: mysql -u root -p < student_setup.sql

CREATE DATABASE IF NOT EXISTS student_db;
USE student_db;

CREATE TABLE IF NOT EXISTS students (
    student_id INT PRIMARY KEY,
    name       VARCHAR(100) NOT NULL,
    email      VARCHAR(100),
    phone      VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS courses (
    course_id   INT PRIMARY KEY,
    course_name VARCHAR(150) NOT NULL,
    credits     INT
);

CREATE TABLE IF NOT EXISTS enrollments (
    enrollment_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id    INT,
    course_id     INT,
    enroll_date   DATE,
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
    FOREIGN KEY (course_id)  REFERENCES courses(course_id)  ON DELETE CASCADE,
    UNIQUE (student_id, course_id)
);

DELIMITER //

DROP PROCEDURE IF EXISTS sp_get_student_courses //
CREATE PROCEDURE sp_get_student_courses(IN p_student_id INT)
BEGIN
    SELECT c.course_id, c.course_name, c.credits
    FROM courses c
    JOIN enrollments e ON c.course_id = e.course_id
    WHERE e.student_id = p_student_id;
END //

DELIMITER ;

-- (No sample data inserted; tables start empty.)
