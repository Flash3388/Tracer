package calculus.functions;

public class MathUtil {
    public static double distance(double xStart, double yStart, double xEnd, double yEnd) {
        return Math.sqrt(Math.pow(xEnd - xStart, 2) + Math.pow(yEnd - yStart, 2));
    }
}
