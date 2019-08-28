import Calculus.CubicSpline;
import Tracer.Position;

public class test {
    public static void main(String[] args) {
        CubicSpline hermite = new CubicSpline(
                new Position(0, 0, 0),
                new Position(1, 1, Math.PI)
        );
    }
}
