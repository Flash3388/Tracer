package com.flash3388.tracer.calculus.variables;

public class Variable {
    private final double modifier;
    private final int power;

    public Variable(double modifier, int power) {
        this.modifier = modifier;
        this.power = power;
    }

    public static Variable modifier(double modifier) {
        return new Variable(modifier, 0);
    }

    public static Variable zero() {
        return new Variable(0,0);
    }

    public double modifier() {
        return modifier;
    }

    public int power() {
        return power;
    }

    public Variable derive() {
        return power == 0 ? numberDerivative() : normalDerivative();
    }

    public Variable integrate() {
        return new Variable(integralMultiplier(), power+1);
    }

    public double at(double value) {
        double result = modifier;

        for (int i = 0; i < Math.abs(power); i++) {
            result *= value;
        }

        if(power < 0)
            result = modifier/(result/modifier);

        return result;
    }

    public Variable add(Variable variable) {
        checkIfMatching(variable);
        return new Variable(modifier + variable.modifier(), power);
    }

    public Variable subtract(Variable variable) {
        checkIfMatching(variable);
        return new Variable(modifier - variable.modifier(), power);
    }

    public Variable mul(Variable variable) {
        return new Variable(modifier * variable.modifier(), power + variable.power());
    }

    public Variable mul(double val) {
        return mul(new Variable(val, 0));
    }

    public Variable square() {
        return mul(this);
    }

    @Override
    public String toString() {
        return String.format("%.4f X^%d", modifier, power);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Variable && equals((Variable) obj);
    }

    public boolean equals(Variable other) {
        return modifier == other.modifier() && power == other.power();
    }

    private Variable numberDerivative() {
        return new Variable(0,0);
    }

    private Variable normalDerivative() {
        return new Variable(derivativeMultiplier(), power-1);
    }

    private double derivativeMultiplier() {
        return modifier * power;
    }

    private double integralMultiplier() {
        return modifier /(power+1);
    }

    private void checkIfMatching(Variable variable) {
        if(variable.power() != power) {
            throw new IllegalArgumentException("other variable's power is not compatible with the original");
        }
    }
}
