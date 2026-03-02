import java.util.*;
import java.util.concurrent.*;

// ====================== MAIN ======================
public class Main {

    static Scanner sc = new Scanner(System.in);
    static ArrayList<Student> students = new ArrayList<>();

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n===== MENU CHÍNH =====");
            System.out.println("1. Quản lý sinh viên");
            System.out.println("2. Payment Processing (Factory)");
            System.out.println("3. Bank Async (CompletableFuture)");
            System.out.println("0. Thoát");
            System.out.print("Chọn: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1: studentMenu(); break;
                case 2: paymentDemo(); break;
                case 3: bankDemo(); break;
                case 0: System.exit(0);
                default: System.out.println("Sai lựa chọn!");
            }
        }
    }

    // ====================== BÀI 1 ======================

    static void studentMenu() {
        while (true) {
            System.out.println("\n--- QUẢN LÝ SINH VIÊN ---");
            System.out.println("1. Thêm");
            System.out.println("2. Hiển thị");
            System.out.println("3. Tìm theo tên");
            System.out.println("4. Xóa theo MSSV");
            System.out.println("0. Quay lại");
            System.out.print("Chọn: ");

            int c = sc.nextInt();
            sc.nextLine();

            switch (c) {
                case 1: addStudent(); break;
                case 2: displayStudents(); break;
                case 3: searchStudent(); break;
                case 4: deleteStudent(); break;
                case 0: return;
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

        for (Student s : students) {
            if (s.name.toLowerCase().contains(keyword)) {
                System.out.println(s);
            }
        }
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

    // ====================== BÀI 2 ======================

    static void paymentDemo() {
        System.out.println("\n--- PAYMENT DEMO ---");
        System.out.println("Chọn phương thức: credit / paypal / cash");
        String type = sc.nextLine();

        Payment payment = PaymentFactory.createPayment(type);
        payment.pay(1000);
    }

    // ====================== BÀI 3 ======================

    static void bankDemo() {

        ExecutorService executor = Executors.newFixedThreadPool(3);

        CompletableFuture<Boolean> auth =
                CompletableFuture.supplyAsync(() -> {
                    sleep(1000);
                    System.out.println("Xác thực OK");
                    return true; // đổi false để test lỗi
                }, executor);

        CompletableFuture<Boolean> balance =
                auth.thenCompose(ok -> {
                    if (!ok) throw new RuntimeException("Xác thực thất bại");
                    return CompletableFuture.supplyAsync(() -> {
                        sleep(1500);
                        System.out.println("Kiểm tra số dư OK");
                        return true; // đổi false để test lỗi
                    }, executor);
                });

        CompletableFuture<Void> transfer =
                balance.thenAccept(ok -> {
                    if (!ok) throw new RuntimeException("Không đủ tiền");
                    sleep(2000);
                    System.out.println("Chuyển tiền thành công!");
                });

        transfer.exceptionally(ex -> {
            System.out.println("Giao dịch bị hủy: " + ex.getMessage());
            return null;
        }).join();

        executor.shutdown();
    }

    static void sleep(int ms) {
        try { Thread.sleep(ms); }
        catch (Exception e) {}
    }
}

// ====================== STUDENT CLASS ======================

class Student {
    String id;
    String name;
    double gpa;

    Student(String id, String name, double gpa) {
        this.id = id;
        this.name = name;
        this.gpa = gpa;
    }

    public String toString() {
        return id + " - " + name + " - GPA: " + gpa;
    }
}

// ====================== FACTORY PATTERN ======================

interface Payment {
    void pay(double amount);
}

class CreditCardPayment implements Payment {
    public void pay(double amount) {
        System.out.println("Thanh toán " + amount + " bằng Credit Card");
    }
}

class PayPalPayment implements Payment {
    public void pay(double amount) {
        System.out.println("Thanh toán " + amount + " bằng PayPal");
    }
}

class CashPayment implements Payment {
    public void pay(double amount) {
        System.out.println("Thanh toán " + amount + " bằng tiền mặt");
    }
}

class PaymentFactory {
    public static Payment createPayment(String type) {
        switch (type.toLowerCase()) {
            case "credit": return new CreditCardPayment();
            case "paypal": return new PayPalPayment();
            case "cash": return new CashPayment();
            default: throw new IllegalArgumentException("Phương thức không hợp lệ");
        }
    }
}