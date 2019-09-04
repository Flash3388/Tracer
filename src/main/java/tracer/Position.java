package tracer;

public class Position {
    private final static double DEF_MARGIN = 1E-4;

    private final double
            x,
            y,
            heading;

    public Position(double xCentimeters, double yCentimeters, double headingRadians) {
        this.x = xCentimeters;
        this.y = yCentimeters;
        this.heading = headingRadians;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getHeading() {
        return heading;
    }

    public double getHeadingDegrees() {
        return Math.toDegrees(heading);
    }

    @Override
    public String toString() {
        return "X: " + x + " Y: " + y + " Heading: " + heading;
    }
}
