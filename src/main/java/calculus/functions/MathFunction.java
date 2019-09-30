package calculus.functions;

import com.jmath.complex.Complex;

import java.util.List;

public interface MathFunction {
    MathFunction derive();
    MathFunction integrate();
    List<Complex> trySolve(double result) throws UnsupportedOperationException;
}
