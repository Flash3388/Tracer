package calculus;

import com.flash3388.flashlib.math.Mathf;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Operations {
    private final static double FULL_CIRCLE_RADIANS = 2*Math.PI;

    public static double boundRadians(double angle) {
        return Mathf.translateInRange(angle, FULL_CIRCLE_RADIANS, true);
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
                                .filter(innerIndex -> innerIndex!=outerIndex)
                                .mapToObj(innerIndex -> variables.get(innerIndex)
                                        .multiply(variables.get(outerIndex)))
                                .collect(Collectors.toList())));
        return result;
    }
}
