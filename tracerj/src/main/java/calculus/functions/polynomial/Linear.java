package calculus.functions.polynomial;

import java.util.Arrays;
import java.util.List;

public class Linear extends PolynomialFunction {
    public Linear(double m, double x, double y) {
        this(m, y-x*m);
    }

    public Linear(double a, double b) {
        this(Arrays.asList(a, b));
    }

    public Linear(List<Double> constants) {
        super(constants, 1);
    }
}
