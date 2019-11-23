package calculus.splines.parameters;

public class Waypoint {
    private final double x;
    private final double y;
    private final double heading;

    public Waypoint(double xMeters, double yMeters, double headingRadians) {
        this.x = xMeters;
        this.y = yMeters;
        this.heading = headingRadians;
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public double heading() {
        return heading;
    }

    public double headingDegrees() {
        return Math.toDegrees(heading);
    }

    @Override
    public String toString() {
        return String.format("X: %f, Y: %f, Heading: %f",x,y,heading);
    }
}
