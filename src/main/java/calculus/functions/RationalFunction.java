package calculus.functions;

import com.jmath.complex.Complex;

import java.util.List;

public class RationalFunction extends MathFunction {
    private final MathFunction numerator;
    private final MathFunction denominator;

    public RationalFunction(MathFunction numerator, MathFunction denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    @Override
    public double at(double x) {
        return numerator.at(x)/denominator.at(x); // check for 0
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
