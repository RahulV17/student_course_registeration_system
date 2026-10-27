package student_course.dao;

import student_course.model.Course;
import student_course.util.StudentDBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    public void addCourse(Course c) throws SQLException {
        String sql = "INSERT INTO courses (course_id, course_name, credits) VALUES (?,?,?)";
        try (Connection con = StudentDBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, c.getCourseId());
            ps.setString(2, c.getCourseName());
            ps.setInt(3, c.getCredits());
            int r = ps.executeUpdate();
            System.out.println(r > 0 ? "Course added successfully." : "Failed to add course.");
        }
    }

    public void addCoursesBatch(List<Course> courses) throws SQLException {
        String sql = "INSERT INTO courses (course_id, course_name, credits) VALUES (?,?,?)";
        try (Connection con = StudentDBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            for (Course c : courses) {
                ps.setInt(1, c.getCourseId());
                ps.setString(2, c.getCourseName());
                ps.setInt(3, c.getCredits());
                ps.addBatch();
            }
            int[] res = ps.executeBatch();
            System.out.println("Batch inserted " + res.length + " courses.");
        }
    }

    public Course getCourseById(int id) throws SQLException {
        String sql = "SELECT * FROM courses WHERE course_id = ?";
        try (Connection con = StudentDBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Course c = new Course();
                    c.setCourseId(rs.getInt("course_id"));
                    c.setCourseName(rs.getString("course_name"));
                    c.setCredits(rs.getInt("credits"));
                    return c;
                }
            }
        }
        return null;
    }

    public List<Course> getAllCourses() throws SQLException {
        List<Course> list = new ArrayList<>();
        String sql = "SELECT * FROM courses";
        try (Connection con = StudentDBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Course c = new Course();
                c.setCourseId(rs.getInt("course_id"));
                c.setCourseName(rs.getString("course_name"));
                c.setCredits(rs.getInt("credits"));
                list.add(c);
            }
        }
        return list;
    }

    public void updateCourse(Course c) throws SQLException {
        String sql = "UPDATE courses SET course_name=?, credits=? WHERE course_id=?";
        try (Connection con = StudentDBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getCourseName());
            ps.setInt(2, c.getCredits());
            ps.setInt(3, c.getCourseId());
            int r = ps.executeUpdate();
            System.out.println(r > 0 ? "Course updated successfully." : "No course found with id " + c.getCourseId());
        }
    }

    public void deleteCourse(int id) throws SQLException {
        String delEnr = "DELETE FROM enrollments WHERE course_id = ?";
        String sql = "DELETE FROM courses WHERE course_id = ?";
        try (Connection con = StudentDBConnection.getConnection()) {
            try (PreparedStatement ps = con.prepareStatement(delEnr)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);
                int r = ps.executeUpdate();
                System.out.println(r > 0 ? "Course deleted successfully." : "No course found with id " + id);
            }
        }
    }
}
