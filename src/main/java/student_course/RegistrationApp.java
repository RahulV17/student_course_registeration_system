package student_course;

import student_course.model.Course;
import student_course.model.Student;
import student_course.service.RegistrationService;
import student_course.util.DBConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RegistrationApp {
    public static void main(String[] args) {
        System.out.println("==============================================");
        System.out.println("  STUDENT COURSE REGISTRATION SYSTEM (JDBC)  ");
        System.out.println("==============================================");
        RegistrationService service = new RegistrationService();
        Scanner sc = new Scanner(System.in);

        while (true) {
            printMenu();
            System.out.print("Enter choice: ");
            int choice;
            try { choice = Integer.parseInt(sc.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.println("Invalid input."); continue; }

            try {
                switch (choice) {
                    case 1: { // register student
                        System.out.print("Student ID: "); int id = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Name: "); String n = sc.nextLine();
                        System.out.print("Email: "); String em = sc.nextLine();
                        System.out.print("Phone: "); String ph = sc.nextLine();
                        service.registerStudent(new Student(id, n, em, ph));
                        break;
                    }
                    case 2: { // add course
                        System.out.print("Course ID: "); int cid = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Course Name: "); String cn = sc.nextLine();
                        System.out.print("Credits: "); int cr = Integer.parseInt(sc.nextLine().trim());
                        service.addCourse(new Course(cid, cn, cr));
                        break;
                    }
                    case 3: { // enroll
                        System.out.print("Student ID: "); int sid = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Course ID: "); int cid = Integer.parseInt(sc.nextLine().trim());
                        service.enroll(sid, cid);
                        break;
                    }
                    case 4: { // batch enroll
                        System.out.print("Course ID: "); int cid = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Student IDs (comma separated): "); String line = sc.nextLine();
                        List<Integer> ids = new ArrayList<>();
                        for (String part : line.split(",")) ids.add(Integer.parseInt(part.trim()));
                        service.enrollMultiple(cid, ids);
                        break;
                    }
                    case 5: { // batch add courses
                        System.out.print("How many courses? "); int n = Integer.parseInt(sc.nextLine().trim());
                        List<Course> courses = new ArrayList<>();
                        for (int i = 0; i < n; i++) {
                            System.out.print("Course ID: "); int cid = Integer.parseInt(sc.nextLine().trim());
                            System.out.print("Course Name: "); String cn = sc.nextLine();
                            System.out.print("Credits: "); int cr = Integer.parseInt(sc.nextLine().trim());
                            courses.add(new Course(cid, cn, cr));
                        }
                        service.addCoursesBatch(courses);
                        break;
                    }
                    case 6: { // view student
                        System.out.print("Student ID: "); int sid = Integer.parseInt(sc.nextLine().trim());
                        service.viewStudent(sid); break;
                    }
                    case 7: service.viewAllStudents(); break;
                    case 8: service.viewAllCourses(); break;
                    case 9: { // students in course
                        System.out.print("Course ID: "); int cid = Integer.parseInt(sc.nextLine().trim());
                        service.studentsInCourse(cid); break;
                    }
                    case 10: { // courses by student (CallableStatement)
                        System.out.print("Student ID: "); int sid = Integer.parseInt(sc.nextLine().trim());
                        service.coursesByStudent(sid); break;
                    }
                    case 11: service.studentCourseReport(); break;
                    case 12: { // update student
                        System.out.print("Student ID: "); int sid = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Name: "); String n = sc.nextLine();
                        System.out.print("Email: "); String em = sc.nextLine();
                        System.out.print("Phone: "); String ph = sc.nextLine();
                        service.updateStudent(new Student(sid, n, em, ph));
                        break;
                    }
                    case 13: { // delete student
                        System.out.print("Student ID: "); int sid = Integer.parseInt(sc.nextLine().trim());
                        service.deleteStudent(sid); break;
                    }
                    case 14: { // delete enrollment
                        System.out.print("Student ID: "); int sid = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Course ID: "); int cid = Integer.parseInt(sc.nextLine().trim());
                        service.deleteEnrollment(sid, cid); break;
                    }
                    case 15: { // delete course
                        System.out.print("Course ID to delete: "); int cid = Integer.parseInt(sc.nextLine().trim());
                        service.deleteCourse(cid); break;
                    }
                    case 0:
                        System.out.println("Goodbye!");
                        DBConnection.closePool();
                        sc.close();
                        return;
                    default: System.out.println("Invalid choice.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number input: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n----- MENU -----");
        System.out.println("1.  Register Student");
        System.out.println("2.  Add Course");
        System.out.println("3.  Enroll Student in Course");
        System.out.println("4.  Batch Enroll Students in Course");
        System.out.println("5.  Batch Add Courses");
        System.out.println("6.  View Student Details");
        System.out.println("7.  View All Students");
        System.out.println("8.  View All Courses");
        System.out.println("9.  Students Enrolled in a Course");
        System.out.println("10. Courses Taken by a Student (stored proc)");
        System.out.println("11. Student-Course Report (JOIN)");
        System.out.println("12. Update Student");
        System.out.println("13. Delete Student");
        System.out.println("14. Delete Enrollment");
        System.out.println("15. Delete Course");
        System.out.println("0.  Exit");
    }
}
