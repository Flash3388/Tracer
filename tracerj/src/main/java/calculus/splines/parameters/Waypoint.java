package calculus.splines.parameters;

import tracer.units.angle.Angle;
import tracer.units.distance.Distance;

public class Waypoint {
    private final Distance x;
    private final Distance y;
    private final Angle heading;

    public Waypoint() {
        this(Distance.meters(0), Distance.meters(0), Angle.degrees(0));
    }

    public Waypoint(Distance x, Distance y, Angle heading) {
        this.x = x;
        this.y = y;
        this.heading = heading;
    }

    public Waypoint shiftXY(Distance distance) {
        return new Waypoint(x.add(distance), y.add(distance), heading);
    }

    public Waypoint shiftX(Distance addition) {
        return new Waypoint(x.add(addition), y, heading);
    }

    public Waypoint shiftY(Distance addition) {
        return new Waypoint(x, y.add(addition), heading);
    }

    public Waypoint shiftHeading(Angle addition) {
        return new Waypoint(x, y, heading.add(addition));
    }

    public Distance x() {
        return x;
    }

    public Distance y() {
        return y;
    }

    public Angle heading() {
        return heading;
    }

    @Override
    public String toString() {
        return String.format("X: %s, Y: %s, Heading: %s",x,y,heading);
    }
}
