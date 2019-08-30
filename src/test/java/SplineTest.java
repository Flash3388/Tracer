import calculus.PolynomialFunction;
import calculus.Variable;

public class SplineTest {
    public static void main(String[] args) {
        PolynomialFunction func = new PolynomialFunction(new Variable(1/6.0, 3), new Variable(1/2.0, -1));

        System.out.println(func);
        System.out.println(func.derivative());
        System.out.println(func.derivative().square());

        double result = func.calcArcLength(1, 2);
        System.out.println(result);

        Variable var = new Variable(1/2.0, -1);
    }
}
