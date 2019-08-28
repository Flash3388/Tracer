package Tracer;

import com.jmath.ExtendedMath;

public class Position extends Parameters{
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

    public boolean almostEquals(Parameters parameters) {
        return almostEquals(parameters, DEF_MARGIN);
    }

    @Override
    public boolean almostEquals(Parameters parameters, double margin) {
        return almostEquals((Position) parameters, margin);
    }

    private boolean almostEquals(Position position, double margin) {
        return ExtendedMath.equals(x, position.getX(), margin) &&
                ExtendedMath.equals(y, position.getY(), margin) &&
                ExtendedMath.equals(heading, position.getHeading(), margin);
    }
}
