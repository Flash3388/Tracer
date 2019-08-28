package Calculus;

public class AngleOperations {
    private final static double FULL_CIRCLE_RADIANS = 2*Math.PI;

    public static double boundRadians(double angle) {
        double roundedAngle = angle%FULL_CIRCLE_RADIANS;

        return roundedAngle < 0 ? FULL_CIRCLE_RADIANS + roundedAngle : roundedAngle;
    }
}
