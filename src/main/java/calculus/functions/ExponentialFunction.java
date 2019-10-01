package calculus.functions;

import com.jmath.complex.Complex;

import java.util.List;

public class ExponentialFunction extends MathFunction{
    private final MathFunction function;
    private final double degree;

    public ExponentialFunction(MathFunction function, double degree) {
        this.function = function;
        this.degree = degree;
    }

    @Override
    public double at(double x) {
        return Math.pow(function.at(x), degree);
    }

    @Override
    public MathFunction derive() {
        return function.mul(degree).pow(degree-1);
    }

    @Override
    public MathFunction integrate() {
        return function.pow(degree+1).div(function.derive().mul(degree+1));
    }

    @Override
    protected List<Complex> trySolve(double result) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return "( " + function + " ) ^" + degree;
    }
}
