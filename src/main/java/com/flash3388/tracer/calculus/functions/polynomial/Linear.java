package com.flash3388.tracer.calculus.functions.polynomial;

import java.util.Arrays;
import java.util.List;

public class Linear extends PolynomialFunction {
    private final double a;
    private final double b;

    public Linear(double a, double b) {
        this(Arrays.asList(a, b));
    }

    public Linear(List<Double> constants) {
        super(constants, 1);
        a = constants.get(0);
        b = constants.get(1);
    }

    public static Linear fromTwoPoints(double x1, double y1, double x2, double y2) {
        return fromDerivativeAndPoint(calcDerivative(x1, y1, x2, y2), x1, y1);
    }

    public static Linear fromDerivativeAndPoint(double m, double x, double y) {
        return new Linear(m, y-x*m);
    }

    @Override
    public double applyAsDouble(double x) {
        return a * x + b;
    }

    private static double calcDerivative(double x1, double y1, double x2, double y2) {
        return (y2 - y1)/(x2 - x1);
    }
}
