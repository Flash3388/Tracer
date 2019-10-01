package calculus.variables;

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

    public double modifier() {
        return modifier;
    }

    public double power() {
        return power;
    }

    public Variable derive() {
        return power == 0 ? numberDerivative() : normalDerivative();
    }

    public Variable integrate() {
        return new Variable(integralMultiplier(), power+1);
    }

    public double at(double value) {
        return modifier * Math.pow(value, power);
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
        return modifier + "X^" + power;
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

    private void checkIfMatching(Variable variable) throws NotMatchingPowersException{
        if(variable.power() != power) {
            throw new NotMatchingPowersException();
        }
    }
}
