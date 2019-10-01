package calculus.functions;

import com.jmath.complex.Complex;

import java.util.List;

public class SumFunction extends MathFunction {
    private final MathFunction firstFunction;
    private final MathFunction secondFunction;

    public SumFunction(MathFunction firstFunction, MathFunction secondFunction) {
        this.firstFunction = firstFunction;
        this.secondFunction = secondFunction;
    }

    @Override
    public double at(double x) {
        return firstFunction.at(x) + secondFunction.at(x);
    }

    @Override
    public MathFunction derive() {
        return firstFunction.derive().add(secondFunction.derive());
    }

    @Override
    public MathFunction integrate() {
        return firstFunction.integrate().add(secondFunction.integrate());
    }

    @Override
    protected List<Complex> trySolve(double result) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
