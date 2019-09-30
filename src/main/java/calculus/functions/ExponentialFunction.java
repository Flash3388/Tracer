package calculus.functions;

import calculus.functions.polynomialFunctions.PolynomialFunction;
import com.jmath.complex.Complex;

import java.util.List;

public class ExponentialFunction implements MathFunction{
    private final PolynomialFunction function;

    public ExponentialFunction(PolynomialFunction function, double degree) {
        this.function = function;
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
    public List<Complex> trySolve(double result) throws UnsupportedOperationException {
        return null;
    }
}
