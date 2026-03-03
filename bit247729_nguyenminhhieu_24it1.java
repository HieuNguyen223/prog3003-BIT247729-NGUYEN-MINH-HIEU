import java.util.*;

public class StudentManager {

    static Scanner sc = new Scanner(System.in);
    static ArrayList<Student> students = new ArrayList<>();

    public static void main(String[] args) {
        studentMenu();
    }

    static void studentMenu() {
        while (true) {
            System.out.println("\n--- QUẢN LÝ SINH VIÊN ---");
            System.out.println("1. Thêm");
            System.out.println("2. Hiển thị");
            System.out.println("3. Tìm theo tên");
            System.out.println("4. Xóa theo MSSV");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");

            int c = sc.nextInt();
            sc.nextLine();

            switch (c) {
                case 1: addStudent(); break;
                case 2: displayStudents(); break;
                case 3: searchStudent(); break;
                case 4: deleteStudent(); break;
                case 0: 
                    System.out.println("Thoát chương trình.");
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    static void addStudent() {
        System.out.print("MSSV: ");
        String id = sc.nextLine();
        System.out.print("Tên: ");
        String name = sc.nextLine();
        System.out.print("GPA: ");
        double gpa = sc.nextDouble();
        sc.nextLine();

        students.add(new Student(id, name, gpa));
        System.out.println("Thêm thành công!");
    }

    static void displayStudents() {
        if (students.isEmpty()) {
            System.out.println("Danh sách rỗng!");
            return;
        }

        for (Student s : students) {
            System.out.println(s);
        }
    }

    static void searchStudent() {
        System.out.print("Nhập tên cần tìm: ");
        String keyword = sc.nextLine().toLowerCase();

        boolean found = false;
        for (Student s : students) {
            if (s.name.toLowerCase().contains(keyword)) {
                System.out.println(s);
                found = true;
            }
        }

        if (!found)
            System.out.println("Không tìm thấy!");
    }

    static void deleteStudent() {
        System.out.print("Nhập MSSV cần xóa: ");
        String id = sc.nextLine();

        boolean removed = students.removeIf(s -> s.id.equals(id));

        if (removed)
            System.out.println("Xóa thành công!");
        else
            System.out.println("Không tìm thấy!");
    }

    static class Student {
        String id;
        String name;
        double gpa;

        Student(String id, String name, double gpa) {
            this.id = id;
            this.name = name;
            this.gpa = gpa;
        }

        public String toString() {
            return "MSSV: " + id + " | Tên: " + name + " | GPA: " + gpa;
        }
    }
}