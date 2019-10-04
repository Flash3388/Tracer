package calculus.functions;

import calculus.functions.polynomialFunctions.PolynomialFunction;
import com.jmath.complex.Complex;

import java.util.List;

public class CompositeFunctions extends MathFunction{
    private final PolynomialFunction outer;
    private final PolynomialFunction inner;

    public CompositeFunctions(PolynomialFunction outer, PolynomialFunction inner) {
        this.outer = outer;
        this.inner = inner;
    }


    @Override
    public double at(double x) {
        return outer.at(inner.at(x));
    }

    @Override
    public MathFunction derive() {
        return outer.derive().at(inner).mul(inner.derive());
    }

    @Override
    public List<Complex> solutionsTo(double result) throws UnsupportedOperationException {//only for real solutions to outer
        return inner.solutionsTo(outer.realSolutionsTo(result));
    }
}
