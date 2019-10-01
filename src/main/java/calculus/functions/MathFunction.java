package calculus.functions;

import calculus.functions.polynomialFunctions.Constant;
import com.jmath.complex.Complex;

import java.util.List;

public abstract class MathFunction {
    public abstract double at(double x);
    public abstract MathFunction derive();

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

    public MathFunction pow(double number) {
        return new ExponentialFunction(this, number);
    }

    public MathFunction root(int number) {
        return pow(1.0/number);
    }

    public double difference(double from, double to) {
        return at(to) - at(from);
    }

    public MathFunction integrate() throws UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }

    protected List<Complex> trySolve(double result) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
