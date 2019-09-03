package tracer.motionProfiles;

import com.flash3388.flashlib.time.Time;
import tracer.MotionParameters;

public class LinearVelocityProfile extends Profile {
    private final double maxAcceleration;
    private final double initialVelocity;

    public LinearVelocityProfile(Profile prevProfile, MotionParameters max, Time duration) {
        this(prevProfile.getLength(), prevProfile.getFinalVelocity(), max, prevProfile.getAbsoluteFinalTime(), duration);
    }

    public LinearVelocityProfile(double initialDistance, double initialVelocity, MotionParameters max, Time startTime, Time duration) {
        super(initialDistance, initialVelocity, max, startTime, duration);

        maxAcceleration = getMaxAcceleration();
        this.initialVelocity = getInitialVelocity();
    }

    @Override
    public double relativeVelocityAt(double t) {
        return maxAcceleration * t;
    }

    @Override
    public double relativeDistanceAt(double t) {
        return initialVelocity * t + maxAcceleration * Math.pow(t, 2)/2;
    }
}
