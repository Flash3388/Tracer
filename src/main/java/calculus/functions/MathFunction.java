package calculus.functions;

import com.jmath.complex.Complex;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public MathFunction pow(double degree) {
        return new ExponentialFunction(this, degree);
    }

    public MathFunction root(double degree) {
        return pow(1.0/degree);
    }

    public MathFunction at(MathFunction mathFunction) {
        return new CompositeFunctions(this, mathFunction);
    }

    public double difference(double from, double to) {
        return at(to) - at(from);
    }

    public MathFunction integrate() throws UnsupportedOperationException{
        throw new UnsupportedOperationException();
    }

    public List<Double> realSolutionsTo(List<Double> that) throws UnsupportedOperationException {
        List<Complex> results = new ArrayList<>();
        that.forEach(result -> results.addAll(this.solutionsTo(result)));

        return toReal(results);
    }

    public List<Double> realSolutionsTo(double that) throws UnsupportedOperationException {
        return toReal(solutionsTo(that));
    }

    public List<Complex> solutionsTo(double that) throws UnsupportedOperationException, UnsolveableFunctionParametersException {
        throw new UnsupportedOperationException();
    }

    private List<Double> toReal(List<Complex> complex) {
        return complex.stream()
                .filter(solution -> solution.imaginary() == 0)
                .map(Complex::real)
                .collect(Collectors.toList());
    }
}
