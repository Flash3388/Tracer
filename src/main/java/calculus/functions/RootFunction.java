package calculus.functions;

import com.jmath.ExtendedMath;
import com.jmath.complex.Complex;

import java.util.List;

public class RootFunction extends MathFunction {
    private final SimpleFunction function;
    private final int degree;

    public RootFunction(SimpleFunction function, int degree) {
        this.function = function;
        this.degree = degree;
    }

    @Override
    public double at(double x) {
        return ExtendedMath.root(function.at(x), degree); //fix minus problem
    }

    @Override
    public MathFunction derive() {
        return function.derive().div(this.mul(degree));
    }

    @Override
    public MathFunction integrate() {
        return new ExponentialFunction(function, degree+1).div(degree+1);
    }

    @Override
    public RootFunction mul(SimpleFunction function) {
        return ;
    }

    @Override
    public RootFunction add(SimpleFunction function) {
        return null;
    }

    @Override
    protected List<Complex> trySolve(double result) throws UnsupportedOperationException {
        return function.trySolve(ExtendedMath.root(result, degree));
    }
}
