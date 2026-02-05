package laptrinhjv;

public class chap22 {
    
}

// ===================== MAIN =====================
public class Main {
    public static void main(String[] args) {

        // ===== BÀI 1: Singleton =====
        System.out.println("=== BÀI 1: Singleton ===");
        DatabaseConnection db1 = DatabaseConnection.getInstance();
        DatabaseConnection db2 = DatabaseConnection.getInstance();
        System.out.println(db1 == db2); // true

        // ===== BÀI 2: Factory =====
        System.out.println("\n=== BÀI 2: Factory ===");
        NotificationFactory.createNotification("SMS").notifyUser();
        NotificationFactory.createNotification("EMAIL").notifyUser();

        // ===== BÀI 3: Facade =====
        System.out.println("\n=== BÀI 3: Facade ===");
        HomeTheaterFacade homeTheater = new HomeTheaterFacade();
        homeTheater.watchMovie();

        // ===== BÀI 4: Strategy =====
        System.out.println("\n=== BÀI 4: Strategy ===");
        ShoppingCart cart = new ShoppingCart();
        cart.setPaymentStrategy(new CreditCardPayment());
        cart.checkout(500);
        cart.setPaymentStrategy(new PayPalPayment());
        cart.checkout(300);

        // ===== BÀI 5: Dependency Injection =====
        System.out.println("\n=== BÀI 5: Dependency Injection ===");
        SimpleNotification notify = new SimpleNotification();
        notify.setMessageService(new EmailService());
        notify.send("Xin chào Email");
        notify.setMessageService(new SMSService());
        notify.send("Xin chào SMS");
    }
}

// ===================== BÀI 1: SINGLETON =====================
class DatabaseConnection {
    private static DatabaseConnection instance;

    private DatabaseConnection() {
        System.out.println("Tạo kết nối Database");
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
}

// ===================== BÀI 2: FACTORY =====================
interface Notification {
    void notifyUser();
}

class SMSNotification implements Notification {
    public void notifyUser() {
        System.out.println("Gửi thông báo qua SMS");
    }
}

class EmailNotification implements Notification {
    public void notifyUser() {
        System.out.println("Gửi thông báo qua Email");
    }
}

class NotificationFactory {
    public static Notification createNotification(String channel) {
        if (channel.equalsIgnoreCase("SMS"))
            return new SMSNotification();
        if (channel.equalsIgnoreCase("EMAIL"))
            return new EmailNotification();
        return null;
    }
}

// ===================== BÀI 3: FACADE =====================
class TV {
    public void on() {
        System.out.println("TV bật");
    }
}

class SoundSystem {
    public void on() {
        System.out.println("Loa bật");
    }
}

class DVDPlayer {
    public void on() {
        System.out.println("DVD bật");
    }
}

class HomeTheaterFacade {
    private TV tv = new TV();
    private SoundSystem sound = new SoundSystem();
    private DVDPlayer dvd = new DVDPlayer();

    public void watchMovie() {
        System.out.println("Chuẩn bị xem phim...");
        tv.on();
        sound.on();
        dvd.on();
        System.out.println("Đang xem phim 🍿");
    }
}

// ===================== BÀI 4: STRATEGY =====================
interface PaymentStrategy {
    void pay(int amount);
}

class CreditCardPayment implements PaymentStrategy {
    public void pay(int amount) {
        System.out.println("Thanh toán " + amount + " bằng thẻ tín dụng");
    }
}

class PayPalPayment implements PaymentStrategy {
    public void pay(int amount) {
        System.out.println("Thanh toán " + amount + " bằng PayPal");
    }
}

class ShoppingCart {
    private PaymentStrategy paymentStrategy;

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void checkout(int amount) {
        paymentStrategy.pay(amount);
    }
}

// ===================== BÀI 5: DEPENDENCY INJECTION =====================
interface MessageService {
    void sendMessage(String message);
}

class EmailService implements MessageService {
    public void sendMessage(String message) {
        System.out.println("Email: " + message);
    }
}

class SMSService implements MessageService {
    public void sendMessage(String message) {
        System.out.println("SMS: " + message);
    }
}

class SimpleNotification {
    private MessageService messageService;

    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    public void send(String message) {
        messageService.sendMessage(message);
    }
}
