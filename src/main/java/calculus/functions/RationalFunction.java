package calculus.functions;

public class RationalFunction extends MathFunction {
    private final MathFunction numerator;
    private final MathFunction denominator;

    public RationalFunction(MathFunction numerator, MathFunction denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    @Override
    public double at(double x) {
        return numerator.at(x)/denominator.at(x); // check for 0
    }

    @Override
    public MathFunction derive() {
        return denominator.mul(numerator.derive())
                .add(denominator.derive().mul(numerator).mul(-1))
                .div(denominator.pow(2));
    }

    @Override
    public String toString() {
        return "(" + numerator + ") / " + "(" + denominator + " )";
    }
}
