package calculus.splines;

import calculus.functions.polynomialFunctions.PolynomialFunction;

public class Spline {
    private final PolynomialFunction yFunction;
    private final PolynomialFunction xFunction;
    private final PolynomialFunction composite;
    private final double arcLength;

    public Spline(PolynomialFunction yFunction, PolynomialFunction xFunction) {
        this.yFunction = yFunction;
        this.xFunction = xFunction;
        composite = yFunction.at(xFunction);

        arcLength = lengthAt(1);

        System.out.println(yFunction);
        System.out.println(xFunction);
        System.out.println(composite);
        System.out.println(arcLength);
    }

    public double length() {
        return arcLength;
    }

    public double angleAt(double length) throws LengthOutsideOfFunctionBoundsException {
        checkLength(length);
        double x = xAtLength(length);

        return Math.atan2(yFunction.at(xFunction.realSolutionsTo(x).get(0)), x);
    }

    private double xAtLength(double length) {
        return composite.atLength(xFunction.at(0), length, 0.01);
    }

    private void checkLength(double length) throws LengthOutsideOfFunctionBoundsException {
        if(length < 0 || length > arcLength)
            throw new LengthOutsideOfFunctionBoundsException();
    }

    private double lengthAt(double percentage) {
        return composite.lengthAt(xFunction.at(0), xFunction.at(percentage), 0.01);
    }
}
