import Calculus.CubicSpline;
import Calculus.PolynomialFunction;
import Calculus.Spline;
import Calculus.Variable;
import Tracer.Position;

public class SplineTest {
    public static void main(String[] args) {
        PolynomialFunction func = new PolynomialFunction(new Variable(1/6.0, 3), new Variable(1/2.0, -1));
        double result = func.calcArcLength(1, 2);
        System.out.println(result);

        Variable var =
    }
}
