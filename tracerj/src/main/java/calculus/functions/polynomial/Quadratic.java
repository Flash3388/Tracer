package calculus.functions.polynomial;

import calculus.splines.parameters.Waypoint;

public class Quadratic extends PolynomialFunction{
    private final double a;
    private final double b;
    private final double c;

    public Quadratic(double a, double b, double c) {
        super(a, b, c);
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public static Quadratic fromThreePoints(Waypoint a, Waypoint b, Waypoint c) {
        double a1 = -a.x()*a.x() + b.x()*b.x();
        double b1 = -a.x() + b.x();
        double c1 = -a.y() + b.y();

        double a2 = -b.x()*b.x() + c.x()*c.x();
        double b2 = -b.x() + c.x();
        double c2 = -b.y() + c.y();

        double bM = -b2/b1;
        double a3 = bM * a1 + a2;
        double c3 = bM * c1 + c2;

        double finalA = c3/a3;
        double finalB = (c1 - a1 * finalA)/b1;
        double finalC = a.y() - finalA*a.x()*a.x() - finalB*a.x();

        return new Quadratic(finalA, finalB, finalC);
    }

    @Override
    public double integrate(double from, double to) {
        return generalIntegralAt(to) - generalIntegralAt(from);
    }

    private double generalIntegralAt(double value) {
        return a * Math.pow(value, 3)/3 + b * Math.pow(value, 2)/2 + c * value;
    }
}
