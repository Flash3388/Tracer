package calculus.functions;

import com.jmath.complex.Complex;

import java.util.List;

public class Exponential extends MathFunction{
    private final MathFunction function;
    private final double degree;

    public Exponential(MathFunction function, double degree) {
        this.function = function;
        this.degree = degree;
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
