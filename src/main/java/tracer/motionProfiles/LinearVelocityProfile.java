package tracer.motionProfiles;

import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionParameters;

public class LinearVelocityProfile extends Profile {
    private final double maxAcceleration;
    private final double initialVelocity;

    public LinearVelocityProfile(Profile prevProfile, MotionParameters max, Time duration) {
        this(prevProfile.length(), prevProfile.finalVelocity(), max, prevProfile.getAbsoluteFinalTime(), duration);
    }

    public LinearVelocityProfile(double initialDistance, double initialVelocity, MotionParameters max, Time startTime, Time duration) {
        super(initialDistance, initialVelocity, max.getAcceleration(), max, startTime, duration);

        maxAcceleration = maxAcceleration();
        this.initialVelocity = initialVelocity();
    }

    @Override
    protected double relativeVelocityAt(double t) {
        return maxAcceleration * t;
    }

    @Override
    protected double relativeDistanceAt(double t) {
        return initialVelocity * t + maxAcceleration * Math.pow(t, 2)/2;
    }

    @Override
    protected double relativeAccelerationAt(double t) {
        return 0;
    }
}
