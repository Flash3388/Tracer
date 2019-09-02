package tracer;

import com.flash3388.flashlib.time.Time;

public abstract class Profile {
    private final double initialDistance;
    private final double initialVelocity;
    private final MotionParameters max;
    private final Time duration;

    public Profile(double startDistance, double startVelocity, MotionParameters max) {
        this.initialDistance = startDistance;
        this.initialVelocity = startVelocity;
        this.max = max;

        duration = calcDuration(startVelocity, max);
    }

    protected abstract Time calcDuration(double startVelocity, MotionParameters max);

    public double getMaxVelocity() {
        return max.getVelocity();
    }

    public double getMaxAcceleration() {
        return max.getAcceleration();
    }

    public double getMaxJerk() {
        return max.getJerk();
    }

    public double getInitialVelocity() {
        return initialVelocity;
    }

    public double getInitialDistance() {
        return initialDistance;
    }

    public Time getDuration() {
        return duration;
    }

    public abstract double velocityAt(Time time);
    public abstract double distanceAt(Time time);
}
