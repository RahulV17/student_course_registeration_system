package student_course.service;

import student_course.dao.CourseDAO;
import student_course.dao.EnrollmentDAO;
import student_course.dao.StudentDAO;
import student_course.model.Course;
import student_course.model.Student;

import java.sql.SQLException;
import java.util.List;

public class RegistrationService {
    private final StudentDAO studentDAO = new StudentDAO();
    private final CourseDAO courseDAO = new CourseDAO();
    private final EnrollmentDAO enrollmentDAO = new EnrollmentDAO();

    public void registerStudent(Student s) {
        try { studentDAO.registerStudent(s); }
        catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void addCourse(Course c) {
        try { courseDAO.addCourse(c); }
        catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void addStudentsBatch(List<Student> students) {
        try { studentDAO.addStudentsBatch(students); }
        catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void addCoursesBatch(List<Course> courses) {
        try { courseDAO.addCoursesBatch(courses); }
        catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void viewStudent(int id) {
        try {
            Student s = studentDAO.getStudentById(id);
            if (s == null) System.out.println("Student not found.");
            else System.out.println(s);
        } catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void viewAllStudents() {
        try {
            List<Student> list = studentDAO.getAllStudents();
            if (list.isEmpty()) { System.out.println("No students."); return; }
            for (Student s : list) System.out.println(s);
        } catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void viewAllCourses() {
        try {
            List<Course> list = courseDAO.getAllCourses();
            if (list.isEmpty()) { System.out.println("No courses."); return; }
            for (Course c : list) System.out.println(c);
        } catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void enroll(int studentId, int courseId) {
        try { enrollmentDAO.enrollStudent(studentId, courseId); }
        catch (SQLException e) { /* already printed */ }
    }

    public void enrollMultiple(int courseId, List<Integer> studentIds) {
        try { enrollmentDAO.enrollMultiple(courseId, studentIds); }
        catch (SQLException e) { /* already printed */ }
    }

    public void studentsInCourse(int courseId) {
        try {
            List<String> list = enrollmentDAO.getStudentsInCourse(courseId);
            if (list.isEmpty()) System.out.println("No students enrolled.");
            else for (String n : list) System.out.println(" - " + n);
        } catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void coursesByStudent(int studentId) {
        try {
            List<String> list = enrollmentDAO.getCoursesByStudent(studentId);
            if (list.isEmpty()) System.out.println("No courses found.");
            else for (String c : list) System.out.println(" - " + c);
        } catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void studentCourseReport() {
        try {
            List<String> list = enrollmentDAO.studentCourseReport();
            if (list.isEmpty()) System.out.println("No enrollments.");
            else for (String r : list) System.out.println(r);
        } catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void updateStudent(Student s) {
        try { studentDAO.updateStudent(s); }
        catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void deleteStudent(int id) {
        try { studentDAO.deleteStudent(id); }
        catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void updateCourse(Course c) {
        try { courseDAO.updateCourse(c); }
        catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void deleteCourse(int id) {
        try { courseDAO.deleteCourse(id); }
        catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }

    public void deleteEnrollment(int studentId, int courseId) {
        try { enrollmentDAO.deleteEnrollment(studentId, courseId); }
        catch (SQLException e) { System.out.println("DB error: " + e.getMessage()); }
    }
}
