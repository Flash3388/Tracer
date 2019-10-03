package calculus.functions.polynomialFunctions;

import com.jmath.ExtendedMath;
import com.jmath.complex.Complex;
import com.jmath.complex.ComplexMath;

import java.util.ArrayList;
import java.util.List;

public class Cubic extends PolynomialFunction {
    public Cubic(List<Double> constants) {
        super(constants, 3);
    }

    @Override
    protected List<Complex> trySolve(double result) throws UnsupportedOperationException {
        double a = get(0).modifier();
        double b = get(1).modifier();
        double c = get(2).modifier();
        double d = get(3).modifier() - result;

        return roots(a, b, c, d);
    }

    private List<Complex> roots(double a, double b, double c, double d) {
        List<Complex> results;
        double f = (3*c/a - b*b/(a*a))/3;
        double g = (2*Math.pow(b, 3)/Math.pow(a, 3) - 9*b*c/Math.pow(a, 2) + 27*d/a)/27;
        double h = g*g/4 + f*f*f/27;

        if(f == 0 && g == 0 && h == 0)
            results = allRealAndEqual(a, d);
        else if(h <= 0)
            results = threeRealRoots(a, b, g, h);
        else
            results = oneRealRoot(a, b, g, h);

        return results;
    }

    private List<Complex> threeRealRoots(double a, double b, double g, double h) {
        List<Complex> results = new ArrayList<>(3);
        double i = Math.sqrt(g*g/4 - h);
        double j = ExtendedMath.root(i, 3);
        double k = Math.acos(-g/(2*i));
        double L = -j;
        double M = Math.cos(k/3);
        double N = Math.sqrt(3) * Math.sin(k/3);
        double P = -b/(3*a);

        results.add(new Complex(2*j * M + P, 0));
        results.add(new Complex(L * (M + N) + P, 0));
        results.add(new Complex(L * (M - N) + P, 0));

        return results;
    }

    private List<Complex> oneRealRoot(double a, double b, double g, double h) {
        List<Complex> results = new ArrayList<>(3);
        double R = -g/2 + Math.sqrt(h);
        double S = ExtendedMath.root(R, 3);
        double T = -g/2 - Math.sqrt(h);
        double U = -ComplexMath.complexRoot(T, 3).imaginary();
        double P = -b/(3*a);

        results.add(new Complex(S + U + P, 0));
        results.add(new Complex(-(S + U)/2 + P, (S - U) * Math.sqrt(3)/2));
        results.add(new Complex(-(S + U)/2 + P, -(S - U) * Math.sqrt(3)/2));

        return results;
    }

    private List<Complex> allRealAndEqual(double a, double d) {
        List<Complex> results = new ArrayList<>(3);
        Complex result = new Complex( -ExtendedMath.root(d/a, 3), 0);

        results.add(result);
        results.add(result);
        results.add(result);

        return results;
    }
}