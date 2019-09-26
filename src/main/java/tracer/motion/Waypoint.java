package tracer.motion;

public class Waypoint {
    private final double
            x,
            y,
            heading;

    public static Waypoint centimetersRadians(double x, double y, double heading) {
        return new Waypoint(x, y, heading);
    }

    private Waypoint(double xCentimeters, double yCentimeters, double headingRadians) {
        this.x = xCentimeters;
        this.y = yCentimeters;
        this.heading = headingRadians;
    }

    public double x() {
        return x;
    }

    public double y() {
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