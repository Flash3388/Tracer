package Tracer;

import com.jmath.ExtendedMath;

public class Position {
    private final static double DEF_MARGIN = 1E-4;

    private final double
            x,
            y,
            heading;

    public Position(double x, double y, double heading) {
        this.x = x;
        this.y = y;
        this.heading = heading;
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

    public boolean almostEquals(Position position) {
        return almostEquals(position, DEF_MARGIN);
    }

    private boolean almostEquals(Position position, double margin) {
        return ExtendedMath.equals(x, position.getX(), margin) &&
                ExtendedMath.equals(y, position.getY(), margin) &&
                ExtendedMath.equals(heading, position.getHeading(), margin);
    }
}
