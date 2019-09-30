package calculus.functions;

import calculus.functions.polynomialFunctions.Constant;
import com.jmath.complex.Complex;

import java.util.List;

public abstract class MathFunction {
    public abstract double at(double x);
    public abstract MathFunction derive();
    public abstract MathFunction integrate();

    public MathFunction scale(double scalar) {
        return mul(Constant.fromConstants(scalar));
    }

    public MathFunction mul(MathFunction other) {
        return new Product(this, other);
    }

    protected abstract List<Complex> trySolve(double result) throws UnsupportedOperationException;
}
