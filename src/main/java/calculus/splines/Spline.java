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
//        composite = yFunction.at(xFunction);
        composite = new PolynomialFunction(50.0, 30.0, 30.0);
        System.out.println(new PolynomialFunction(1.0, 3.0, 0.0));
        System.out.println(new PolynomialFunction(1.0, 1.0));
        System.out.println(new PolynomialFunction(1.0, 3.0, 0.0).at(new PolynomialFunction(1.0, 1.0)));

        arcLength = lengthAt(1);

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
        return composite.pointAtLength(0, arcLength, length, 0.01);
    }

    private void checkLength(double length) throws LengthOutsideOfFunctionBoundsException {
        if(length < 0 || length > arcLength)
            throw new LengthOutsideOfFunctionBoundsException();
    }

    private double lengthAt(double percentage) {
        return composite.lengthAt(0, percentage);
    }
}
