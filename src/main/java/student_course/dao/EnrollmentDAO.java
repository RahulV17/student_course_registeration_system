package student_course.dao;

import student_course.util.StudentDBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDAO {

    public void enrollStudent(int studentId, int courseId) throws SQLException {
        Connection c = StudentDBConnection.getConnection();
        try {
            c.setAutoCommit(false);
            // check student exists
            try (PreparedStatement ps = c.prepareStatement("SELECT 1 FROM students WHERE student_id=?")) {
                ps.setInt(1, studentId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) { System.out.println("Student not found."); c.rollback(); return; }
                }
            }
            // check course exists
            try (PreparedStatement ps = c.prepareStatement("SELECT 1 FROM courses WHERE course_id=?")) {
                ps.setInt(1, courseId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) { System.out.println("Course not found."); c.rollback(); return; }
                }
            }
            // check not already enrolled
            try (PreparedStatement ps = c.prepareStatement("SELECT 1 FROM enrollments WHERE student_id=? AND course_id=?")) {
                ps.setInt(1, studentId);
                ps.setInt(2, courseId);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) { System.out.println("Student already enrolled in this course."); c.rollback(); return; }
                }
            }
            try (PreparedStatement ps = c.prepareStatement("INSERT INTO enrollments (student_id, course_id, enroll_date) VALUES (?,?,CURDATE())")) {
                ps.setInt(1, studentId);
                ps.setInt(2, courseId);
                ps.executeUpdate();
            }
            c.commit();
            System.out.println("Enrollment successful (transaction committed).");
        } catch (SQLException e) {
            try { c.rollback(); } catch (SQLException ignored) {}
            System.out.println("Enrollment failed, rolled back: " + e.getMessage());
            throw e;
        } finally {
            try { c.setAutoCommit(true); c.close(); } catch (SQLException ignored) {}
        }
    }

    public void enrollMultiple(int courseId, List<Integer> studentIds) throws SQLException {
        String sql = "INSERT INTO enrollments (student_id, course_id, enroll_date) VALUES (?,?,CURDATE())";
        Connection c = StudentDBConnection.getConnection();
        try {
            c.setAutoCommit(false);
            try (PreparedStatement ps = c.prepareStatement(sql)) {
                for (int sid : studentIds) {
                    ps.setInt(1, sid);
                    ps.setInt(2, courseId);
                    ps.addBatch();
                }
                ps.executeBatch();
            }
            c.commit();
            System.out.println("Batch enrollment committed for " + studentIds.size() + " students.");
        } catch (SQLException e) {
            try { c.rollback(); } catch (SQLException ignored) {}
            System.out.println("Batch enrollment rolled back: " + e.getMessage());
            throw e;
        } finally {
            try { c.setAutoCommit(true); c.close(); } catch (SQLException ignored) {}
        }
    }

    public List<String> getStudentsInCourse(int courseId) throws SQLException {
        List<String> list = new ArrayList<>();
        String sql = "SELECT s.name FROM students s JOIN enrollments e ON s.student_id = e.student_id WHERE e.course_id = ?";
        try (Connection c = StudentDBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, courseId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(rs.getString("name"));
            }
        }
        return list;
    }

    public List<String> getCoursesByStudent(int studentId) throws SQLException {
        // CallableStatement: stored procedure sp_get_student_courses
        List<String> list = new ArrayList<>();
        String sql = "{CALL sp_get_student_courses(?)}";
        try (Connection c = StudentDBConnection.getConnection();
             CallableStatement cs = c.prepareCall(sql)) {
            cs.setInt(1, studentId);
            try (ResultSet rs = cs.executeQuery()) {
                while (rs.next()) list.add(rs.getString("course_name") + " (" + rs.getInt("credits") + " credits)");
            }
        }
        return list;
    }

    public List<String> studentCourseReport() throws SQLException {
        List<String> list = new ArrayList<>();
        String sql = "SELECT s.name, c.course_name FROM students s " +
                "JOIN enrollments e ON s.student_id = e.student_id " +
                "JOIN courses c ON e.course_id = c.course_id ORDER BY s.name, c.course_name";
        try (Connection c = StudentDBConnection.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(rs.getString("name") + " -> " + rs.getString("course_name"));
            }
        }
        return list;
    }

    public void deleteEnrollment(int studentId, int courseId) throws SQLException {
        String sql = "DELETE FROM enrollments WHERE student_id=? AND course_id=?";
        try (Connection c = StudentDBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, courseId);
            int r = ps.executeUpdate();
            System.out.println(r > 0 ? "Enrollment deleted." : "Enrollment not found.");
        }
    }
}
