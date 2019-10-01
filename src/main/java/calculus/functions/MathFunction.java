package calculus.functions;

import calculus.functions.polynomialFunctions.Constant;
import com.jmath.complex.Complex;

import java.util.List;

public abstract class MathFunction {
    public abstract double at(double x);
    public abstract MathFunction derive();
    public abstract MathFunction integrate() throws UnsupportedOperationException;

    public MathFunction mul(double scalar) {
        return mul(new Constant(scalar));
    }

    public MathFunction mul(MathFunction other) {
        return new ProductFunction(this, other);
    }

    public MathFunction div(double scalar) {
        return div(new Constant(scalar));
    }

    public MathFunction div(MathFunction other) {
        return new RationalFunction(this, other);
    }

    public MathFunction add(double number) {
        return add(new Constant(number));
    }

    public MathFunction add(MathFunction other) {
        return new SumFunction(this, other);
    }

    public MathFunction pow(int number) {
        return new ExponentialFunction(this, number);
    }

    public MathFunction root(int number) {
        return new RootFunction(this, number);
    }

    protected abstract List<Complex> trySolve(double result) throws UnsupportedOperationException;
}
