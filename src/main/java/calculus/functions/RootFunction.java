package calculus.functions;

import com.jmath.ExtendedMath;
import com.jmath.complex.Complex;

import java.util.List;

public class RootFunction extends MathFunction {
    private final MathFunction function;
    private final int degree;

    public RootFunction(MathFunction function, int degree) {
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
        return function.pow(degree+1).div(degree+1);
    }

    @Override
    protected List<Complex> trySolve(double result) throws UnsupportedOperationException {
        return function.trySolve(ExtendedMath.root(result, degree));
    }
}
