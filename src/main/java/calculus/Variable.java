package calculus;

public class Variable {
    private final double modifier;
    private final double power;

    public Variable(double modifier, double power) {
        this.modifier = modifier;
        this.power = power;
    }

    public static Variable modifier(double modifier) {
        return new Variable(modifier, 0);
    }

    public static Variable zero() {
        return new Variable(0,0);
    }

    public double getModifier() {
        return modifier;
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
        return modifier * power;
    }

    public Variable integral() {
        return new Variable(integralMultiplier(), power+1);
    }

    private double integralMultiplier() {
        return modifier /(power+1);
    }

    public double calcValue(double value) {
        return modifier * Math.pow(value, power);
    }

    public Variable add(Variable variable) {
        checkIfMatching(variable);
        return new Variable(modifier + variable.getModifier(), power);
    }

    public Variable subtract(Variable variable) {
        checkIfMatching(variable);
        return new Variable(modifier - variable.getModifier(), power);
    }

    private void checkIfMatching(Variable variable) throws NotMatchingPowersException{
        if(variable.getPower() != power) {
            throw new NotMatchingPowersException();
        }
    }

    public Variable multiply(Variable variable) {
        return new Variable(modifier * variable.getModifier(), power + variable.getPower());
    }

    public Variable multiply(double val) {
        return multiply(new Variable(val, 0));
    }

    public Variable square() {
        return multiply(this);
    }

    @Override
    public String toString() {
        return modifier + "X^" + power;
    }
}
