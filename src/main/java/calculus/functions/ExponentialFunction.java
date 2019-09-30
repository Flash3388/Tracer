package calculus.functions;

import com.jmath.ExtendedMath;
import com.jmath.complex.Complex;

import java.util.List;

public class ExponentialFunction extends MathFunction{
    private final MathFunction function;
    private final int degree;

    public ExponentialFunction(MathFunction function, int degree) {
        this.function = function;
        this.degree = degree;
    }

    @Override
    public double at(double x) {
        return Math.pow(function.at(x), degree);
    }

    @Override
    public MathFunction derive() {
        return new ExponentialFunction(function.integrate().mul(degree), degree-1);
    }

    @Override
    public MathFunction integrate() {
        return new ExponentialFunction(function, degree+1).div(degree+1);
    }

    @Override
    public MathFunction mul(SimpleFunction function) {
        return null;
    }

    @Override
    public MathFunction add(SimpleFunction function) {
        return null;
    }

    @Override
    protected List<Complex> trySolve(double result) throws UnsupportedOperationException {
        return function.trySolve(ExtendedMath.root(result, degree));
    }
}
