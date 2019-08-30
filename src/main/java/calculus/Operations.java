package calculus;

import java.util.*;
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
        Map<Variable,List<Variable>> combinations = new HashMap<>();

        IntStream.range(0, variables.size())
                .forEach(outerIndex -> {
                    combinations.put(variables.get(outerIndex), new ArrayList<>());
                    result.addAll(
                            IntStream.range(0, variables.size())
                                    .filter(innerIndex -> innerIndex!=outerIndex && !hasOccurred(combinations, variables.get(innerIndex), variables.get(outerIndex)))
                                    .mapToObj(innerIndex -> {
                                        combinations.get(variables.get(outerIndex)).add(variables.get(innerIndex));

                                        return variables.get(innerIndex)
                                                .multiply(variables.get(outerIndex))
                                                .multiply(2.0);
                                    })
                                    .collect(Collectors.toList()));
                });

        return result;
    }

    private static boolean hasOccurred(Map<Variable,List<Variable>> combinations, Variable variable1 , Variable variable2) {
        return (combinations.containsKey(variable1) && combinations.get(variable1).contains(variable2)) ||
                (combinations.containsKey(variable2) && combinations.get(variable2).contains(variable1));
    }
}
