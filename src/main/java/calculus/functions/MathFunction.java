package calculus.functions;

import calculus.functions.polynomialFunctions.Constant;
import com.jmath.complex.Complex;

import java.util.List;

public abstract class MathFunction {
    public abstract double at(double x);
    public abstract MathFunction derive();
    public abstract MathFunction integrate();

    public  MathFunction mul(double scalar) {
        return mul(new Constant(scalar));
    }

    public abstract MathFunction mul(SimpleFunction function);

    public MathFunction add(double number) {
        return add(new Constant(number));
    }

    public abstract MathFunction add(SimpleFunction function);

    public MathFunction div(double scalar) {
        return div(new Constant(scalar));
    }

    public MathFunction div(MathFunction other) {
        return new RationalFunction(this, other);
    }

    protected abstract List<Complex> trySolve(double result) throws UnsupportedOperationException;
}
