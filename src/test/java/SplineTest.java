import Tracer.Position;
import calculus.CubicSpline;
import calculus.PolynomialFunction;
import calculus.Spline;
import calculus.Variable;

public class SplineTest {
    public static void main(String[] args) {
        Spline spline = new CubicSpline(new Position(0,0,0), new Position(1, 2, Math.PI));
        System.out.println(spline.getLength());
    }
}
