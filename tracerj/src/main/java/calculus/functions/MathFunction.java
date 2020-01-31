package calculus.functions;

import java.util.function.DoubleUnaryOperator;

public interface MathFunction extends DoubleUnaryOperator {
    MathFunction derive();
    default double xAt(double v) {
        return v;
    }
}