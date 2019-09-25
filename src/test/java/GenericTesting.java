import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GenericTesting {
    public static void main(String[] args) {
        List<Double> solutions = new ArrayList<>();
        double length = 4;
        solutions.add(5.0);
        solutions.add(6.0);
        solutions.add(2.0);
        solutions.add(3.0);

        solutions.stream()
//                .map(solution -> length - 2 * solution)
                .sorted(Comparator.comparingDouble(x -> Math.abs(length - x)))
                .forEach(System.out::println);
    }
}
