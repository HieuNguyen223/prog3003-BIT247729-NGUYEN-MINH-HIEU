import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

@FunctionalInterface
interface MathOperation {
    int compute(int a, int b);
}

public class Main {

    public static void main(String[] args) {

        // =========================
        // BÀI 1: Functional Interface + Lambda
        // =========================
        System.out.println("=== Bài 1 ===");

        MathOperation add = (a, b) -> a + b;
        MathOperation subtract = (a, b) -> a - b;
        MathOperation multiply = (a, b) -> a * b;
        MathOperation divide = (a, b) -> a / b;

        System.out.println("Cộng: " + add.compute(10, 5));
        System.out.println("Trừ: " + subtract.compute(10, 5));
        System.out.println("Nhân: " + multiply.compute(10, 5));
        System.out.println("Chia: " + divide.compute(10, 5));


        // =========================
        // BÀI 2: Sort với Lambda
        // =========================
        System.out.println("\n=== Bài 2 ===");

        List<String> cities = new ArrayList<>(
                Arrays.asList("Hanoi", "Ho Chi Minh", "Da Nang", "Hue")
        );

        Collections.sort(cities, (c1, c2) -> c1.length() - c2.length());

        System.out.println("Danh sách sau khi sort theo độ dài:");
        System.out.println(cities);


        // =========================
        // BÀI 3: Predicate kiểm tra số chẵn
        // =========================
        System.out.println("\n=== Bài 3 ===");

        Predicate<Integer> isEven = n -> n % 2 == 0;

        System.out.println("4 là số chẵn? " + isEven.test(4));
        System.out.println("7 là số chẵn? " + isEven.test(7));


        // =========================
        // BÀI 4: Function chuyển "$10" → 10
        // =========================
        System.out.println("\n=== Bài 4 ===");

        List<String> money = Arrays.asList("$10", "$20", "$50");

        Function<String, Integer> convert =
                s -> Integer.parseInt(s.substring(1));

        List<Integer> numbers = money.stream()
                .map(convert)
                .collect(Collectors.toList());

        System.out.println("Danh sách sau khi chuyển:");
        System.out.println(numbers);


        // =========================
        // BÀI 5: Supplier + Consumer
        // =========================
        System.out.println("\n=== Bài 5 ===");

        Supplier<Double> supplier =
                () -> Math.random() * 100;

        Consumer<Double> consumer =
                num -> System.out.println("Số may mắn: " + num);

        Double randomNumber = supplier.get();

        consumer.accept(randomNumber);


        // =========================
        // BÀI 6: Stream API chain
        // =========================
        System.out.println("\n=== Bài 6 ===");

        int sum = Arrays.asList(1, 2, 3, 4, 5, 6)
                .stream()
                .filter(n -> n % 2 == 0)
                .map(n -> n * n)
                .reduce(0, Integer::sum);

        System.out.println("Tổng bình phương các số chẵn: " + sum);

    }
}
