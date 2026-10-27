package student_course.model;

public class Student {
    private int studentId;
    private String name;
    private String email;
    private String phone;

    public Student() {}
    public Student(int studentId, String name, String email, String phone) {
        this.studentId = studentId; this.name = name; this.email = email; this.phone = phone;
    }
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    @Override
    public String toString() {
        return "Student{id=" + studentId + ", name='" + name + "', email='" + email + "', phone='" + phone + "'}";
    }
}
