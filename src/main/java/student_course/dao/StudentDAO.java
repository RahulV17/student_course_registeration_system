package student_course.dao;

import student_course.model.Student;
import student_course.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public void registerStudent(Student s) throws SQLException {
        String sql = "INSERT INTO students (student_id, name, email, phone) VALUES (?,?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, s.getStudentId());
            ps.setString(2, s.getName());
            ps.setString(3, s.getEmail());
            ps.setString(4, s.getPhone());
            int r = ps.executeUpdate();
            System.out.println(r > 0 ? "Student registered successfully." : "Failed to register student.");
        }
    }

    public Student getStudentById(int id) throws SQLException {
        String sql = "SELECT * FROM students WHERE student_id = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Student s = new Student();
                    s.setStudentId(rs.getInt("student_id"));
                    s.setName(rs.getString("name"));
                    s.setEmail(rs.getString("email"));
                    s.setPhone(rs.getString("phone"));
                    return s;
                }
            }
        }
        return null;
    }

    public List<Student> getAllStudents() throws SQLException {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Student s = new Student();
                s.setStudentId(rs.getInt("student_id"));
                s.setName(rs.getString("name"));
                s.setEmail(rs.getString("email"));
                s.setPhone(rs.getString("phone"));
                list.add(s);
            }
        }
        return list;
    }

    public void updateStudent(Student s) throws SQLException {
        String sql = "UPDATE students SET name=?, email=?, phone=? WHERE student_id=?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getEmail());
            ps.setString(3, s.getPhone());
            ps.setInt(4, s.getStudentId());
            int r = ps.executeUpdate();
            System.out.println(r > 0 ? "Student updated successfully." : "No student found with id " + s.getStudentId());
        }
    }

    public void deleteStudent(int id) throws SQLException {
        String sql = "DELETE FROM students WHERE student_id = ?";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            int r = ps.executeUpdate();
            System.out.println(r > 0 ? "Student deleted successfully." : "No student found with id " + id);
        }
    }

    public void addStudentsBatch(List<Student> students) throws SQLException {
        String sql = "INSERT INTO students (student_id, name, email, phone) VALUES (?,?,?,?)";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            for (Student s : students) {
                ps.setInt(1, s.getStudentId());
                ps.setString(2, s.getName());
                ps.setString(3, s.getEmail());
                ps.setString(4, s.getPhone());
                ps.addBatch();
            }
            int[] res = ps.executeBatch();
            System.out.println("Batch inserted " + res.length + " students.");
        }
    }
}
