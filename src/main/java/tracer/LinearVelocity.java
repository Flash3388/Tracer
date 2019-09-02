package tracer;

import com.flash3388.flashlib.time.Time;

public class LinearVelocity extends Profile {
    private final double maxVelocity;
    private final double maxAcceleration;
    private final double maxJerk;

    private final double initialDistance;

    public LinearVelocity(double initialDistance, MotionParameters max, Time startTime) {
        super(initialDistance, max.getVelocity(), max, startTime);

        maxVelocity = getMaxVelocity();
        maxAcceleration = getMaxAcceleration();
        maxJerk = getMaxJerk();

        this.initialDistance = getInitialDistance();
    }

    @Override
    protected Time calcDuration(MotionParameters max) {
        return null;
    }

    @Override
    public double relativeVelocityAt(double t) {
        return 0;
    }

    @Override
    public double relativeDistanceAt(double t) {
        return maxVelocity * t;
    }

}
