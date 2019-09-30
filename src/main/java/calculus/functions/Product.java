package calculus.functions;

import com.jmath.complex.Complex;

import java.util.List;

public class Product extends MathFunction {
    private final MathFunction firstFunction;
    private final MathFunction secondFunction;

    public Product(MathFunction firstFunction, MathFunction secondFunction) {
        this.firstFunction = firstFunction;
        this.secondFunction = secondFunction;
    }

    @Override
    public double at(double x) {
        return 0;
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
    protected List<Complex> trySolve(double result) throws UnsupportedOperationException {
        return null;
    }
}
