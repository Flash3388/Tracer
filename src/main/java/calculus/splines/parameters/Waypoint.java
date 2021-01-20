package calculus.splines.parameters;

public class Waypoint {
    private final double x;
    private final double y;
    private final double heading;

    public Waypoint() {
        this(0, 0, 0);
    }

    public Waypoint(double xMeters, double yMeters, double headingRadians) {
        this.x = xMeters;
        this.y = yMeters;
        this.heading = headingRadians;
    }

    public Waypoint shiftXY(double meters) {
        return new Waypoint(x+meters, y+meters, heading);
    }

    public Waypoint shiftX(double xMeters) {
        return new Waypoint(x+xMeters, y, heading);
    }

    public Waypoint shiftY(double yMeters) {
        return new Waypoint(x, y+yMeters, heading);
    }

    public Waypoint shiftHeading(double headingRadians) {
        return new Waypoint(x, y, heading+headingRadians);
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
