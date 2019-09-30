package calculus.functions;

import com.jmath.complex.Complex;

import java.util.List;
import java.util.function.Function;

public class Rational extends MathFunction {
    private final Function numerator;
    private final Function denominator;

    public Rational(Function numerator, Function denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    @Override
    public double at(double x) {
        return 0;
    }

    @Override
    public MathFunction derive() {
        return null;
    }

    @Override
    public MathFunction integrate() {
        return null;
    }

    @Override
    protected List<Complex> trySolve(double result) throws UnsupportedOperationException {
        return null;
    }
}
