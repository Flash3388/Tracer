package calculus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Operations {
    private final static double FULL_CIRCLE_RADIANS = 2*Math.PI;
    private final static Variable ZERO = new Variable(0,1);

    public static double boundRadians(double angle) {
        double roundedAngle = angle%FULL_CIRCLE_RADIANS;

        return roundedAngle < 0 ? FULL_CIRCLE_RADIANS + roundedAngle : roundedAngle;
    }

    public static List<Variable> square(Variable... variables) {
        return square(Arrays.asList(variables));
    }

    public static  List<Variable> square(List<Variable> variables) {
        List<Variable> result = sumOfSquares(variables);
        result.addAll(allPossiblePairsSum(variables));

        return  result;
    }

    private static List<Variable> sumOfSquares(List<Variable> variables) {
        return variables.stream()
                .map(Variable::square)
                .collect(Collectors.toList());
    }

    private static List<Variable> allPossiblePairsSum(List<Variable> variables) {
        List<Variable> result = new ArrayList<>();

        IntStream.range(0, variables.size())
                .forEach(outerIndex -> result.addAll(
                        IntStream.range(0, variables.size())
                        .mapToObj(innerIndex -> outerIndex == innerIndex ?  ZERO:
                                variables.get(innerIndex)
                                        .multiply(variables.get(outerIndex))
                                        .multiply(2.0))
                        .collect(Collectors.toList())));

        return result;
    }
}
