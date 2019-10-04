package calculus.functions;

import com.jmath.complex.Complex;

import java.util.List;
import java.util.stream.Collectors;

public abstract class MathFunction {
    public abstract double at(double x);
    public abstract MathFunction derive();

    public double difference(double from, double to) {
        return at(to) - at(from);
    }

    public MathFunction integrate() throws UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }

    public List<Double> realSolutionsTo(double that) throws UnsupportedOperationException {
        return toReal(solutionsTo(that));
    }

    public List<Complex> solutionsTo(double that) throws UnsupportedOperationException, UnsolvableFunctionParametersException {
        throw new UnsupportedOperationException();
    }

    private List<Double> toReal(List<Complex> complex) {
        return complex.stream()
                .filter(solution -> solution.imaginary() == 0)
                .map(Complex::real)
                .collect(Collectors.toList());
    }
}
