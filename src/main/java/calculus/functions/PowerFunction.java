package calculus.functions;

import com.jmath.complex.Complex;

import java.util.List;

public class PowerFunction extends MathFunction{
    private final MathFunction function;
    private final double degree;

    public PowerFunction(MathFunction function, double degree) {
        this.function = function;
        this.degree = degree;
    }

    @Override
    public double at(double x) {
        return Math.pow(function.at(x), degree);
    }

    @Override
    public MathFunction derive() {
        return function.derive().mul(degree).mul(function.pow(degree-1));
    }

    @Override
    public MathFunction integrate() {
        return function.pow(degree+1).div(function.derive().mul(degree+1));
    }

    @Override
    public List<Complex> solutionsTo(double result) throws UnsupportedOperationException, UnsolveableFunctionParametersException {
        if(result < 0 && (int) degree == degree)
            throw new UnsolveableFunctionParametersException();

        return function.solutionsTo(Math.pow(result, 1/degree));
    }

    @Override
    public String toString() {
        return "( " + function + " ) ^" + degree;
    }
}
