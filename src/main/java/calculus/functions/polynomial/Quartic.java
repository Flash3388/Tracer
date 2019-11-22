package calculus.functions.polynomial;

import com.jmath.complex.Complex;

import java.util.*;
import java.util.stream.Collectors;

public class Quartic extends PolynomialFunction {
    public Quartic(double a, double b, double c, double d, double e) {
        this(Arrays.asList(a, b, c, d, e));
    }

    public Quartic(List<Double> constants) {
        super(constants, 4);
    }

    @Override
    protected Collection<Complex> trySolve(double result) {
        double a = get(0).modifier();
        double b = get(1).modifier();
        double c = get(2).modifier();
        double d = get(3).modifier();
        double e = get(4).modifier() - result;

        return roots(a, b, c, d, e);
    }

    private Collection<Complex> roots(double a, double b, double c, double d, double e) {
        b /= a;
        c /= a;
        d /= a;
        e /= a;
        a = 1;

        double g = d + Math.pow(b, 3)/8 - b*c/2;
        Cubic cubic = findCubic(b, c, d, e, g);
        List<Complex> fixedSolutions = sortByImaginary(cubic.solutionsTo(0));

        return finalRoots(a, b, g, fixedSolutions.get(0), fixedSolutions.get(1));
    }

    private Cubic findCubic(double b, double c, double d, double e, double g) {
        double f = c - 3*b*b/8;
        double h = e - 3*Math.pow(b, 4)/256 + b*b * c/16 - b*d/4;

        return new Cubic(1, f/2, (f*f - 4*h)/16, -g*g/64);
    }

    private List<Complex> sortByImaginary(Collection<Complex> solutions) {
        return solutions.stream()
                .sorted(Comparator.comparingDouble(Complex::real))
                .sorted(Comparator.comparingDouble(Complex::imaginary))
                .collect(Collectors.toList());
    }

    private Collection<Complex> finalRoots(double a, double b, double g, Complex firstSolution, Complex secondSolution) {
        Collection<Complex> results = new ArrayList<>(4);
        Complex p = firstSolution.roots(2).get(0);
        Complex q = secondSolution.roots(2).get(0);
        Complex r = new Complex(-g, 0).div(p.multiply(q).multiply(8));
        Complex s = new Complex(b/(4*a), 0);

        results.add(p.add(q).add(r).sub(s));
        results.add(p.sub(q).sub(r).sub(s));
        results.add(p.multiply(-1).add(q).sub(r).sub(s));
        results.add(p.multiply(-1).sub(q).add(r).sub(s));

        return results;
    }
}
