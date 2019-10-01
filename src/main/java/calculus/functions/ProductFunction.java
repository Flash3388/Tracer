package calculus.functions;

import com.jmath.complex.Complex;

import java.util.List;

public class ProductFunction extends MathFunction{
    private final MathFunction firstFunction;
    private final MathFunction secondFunction;

    public ProductFunction(MathFunction firstFunction, MathFunction secondFunction) {
        this.firstFunction = firstFunction;
        this.secondFunction = secondFunction;
    }

    @Override
    public double at(double x) {
        return firstFunction.at(x) * secondFunction.at(x);
    }

    @Override
    public MathFunction derive() {
        return firstFunction.derive().mul(secondFunction)
                .add(firstFunction.mul(secondFunction.derive()));
    }

    @Override
    public MathFunction integrate() throws UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }

    @Override
    protected List<Complex> trySolve(double result) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }
}
