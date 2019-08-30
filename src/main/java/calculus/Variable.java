package calculus;

public class Variable {
    private final double multiplier;
    private final double power;

    public Variable(double multiplier, double power) {
        this.multiplier = multiplier;
        this.power = power;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public double getPower() {
        return power;
    }

    public Variable derivative() {
        return power == 0 ? numberDerivative() : normalDerivative();
    }

    private Variable numberDerivative() {
        return new Variable(0,0);
    }

    private Variable normalDerivative() {
        return new Variable(derivativeMultiplier(), power-1);
    }

    private double derivativeMultiplier() {
        return multiplier * power;
    }

    public Variable integral() {
        return new Variable(integralMultiplier(), power+1);
    }

    private double integralMultiplier() {
        return multiplier/(power+1);
    }

    public double calcValue(double value) {
        return multiplier * Math.pow(value, power);
    }

    public Variable add(Variable variable) {
        checkIfMatching(variable);
        return new Variable(multiplier + variable.getMultiplier(), power);
    }

    public Variable subtract(Variable variable) {
        checkIfMatching(variable);
        return new Variable(multiplier - variable.getMultiplier(), power);
    }

    private void checkIfMatching(Variable variable) throws NotMatchingPowersException{
        if(variable.getPower() != power) {
            throw new NotMatchingPowersException();
        }
    }

    public Variable multiply(Variable variable) {
        return new Variable(multiplier * variable.getMultiplier(), power + variable.getPower());
    }

    public Variable multiply(double val) {
        return multiply(new Variable(val, 0));
    }

    public Variable square() {
        return multiply(this);
    }

    @Override
    public String toString() {
        return multiplier + "X^" + power;
    }
}
