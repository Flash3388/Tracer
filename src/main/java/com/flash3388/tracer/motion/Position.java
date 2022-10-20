package com.flash3388.tracer.motion;

import com.flash3388.flashlib.time.Time;

public class Position {
    private final Time timestamp;
    private final double distance;
    private final double angle;

    public Position(Time timestamp, double distanceMeters, double angleDegrees) {
        this.timestamp = timestamp;
        this.distance = distanceMeters;
        this.angle = angleDegrees;
    }

    public static Position start() {
        return new Position(Time.milliseconds(0), 0, 0);
    }

    public Time timestamp() {
        return timestamp;
    }

    public double distance() {
        return distance;
    }

    public double getAngle() {
        return angle;
    }
}
