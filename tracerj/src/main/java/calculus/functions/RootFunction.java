package calculus.functions;

import com.jmath.ExtendedMath;

public class RootFunction extends MathFunction {
    private final MathFunction inner;
    private final int degree;

    public RootFunction(MathFunction inner, int degree) {
        this.inner = inner;
        this.degree = degree;
    }

    @Override
    public MathFunction derive() {
        return null;
    }

    @Override
    public double applyAsDouble(double operand) {
        return ExtendedMath.root(inner.applyAsDouble(operand), degree);
    }
}
