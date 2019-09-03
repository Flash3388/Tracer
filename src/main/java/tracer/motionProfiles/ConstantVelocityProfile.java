package tracer.motionProfiles;

import com.flash3388.flashlib.time.Time;
import tracer.MotionParameters;

public class ConstantVelocityProfile extends Profile {
    private final double maxVelocity;

    public ConstantVelocityProfile(Profile prevProfile, Time duration) {
        this(prevProfile.getLength(), prevProfile.getMax(), prevProfile.getAbsoluteFinalTime(), duration);
    }

    public ConstantVelocityProfile(double initialDistance, MotionParameters max, Time startTime, Time duration) {
        super(initialDistance, max.getVelocity(), 0, max, startTime, duration);

        maxVelocity = getMaxVelocity();
    }

    @Override
    protected double relativeVelocityAt(double t) {
        return 0;
    }

    @Override
    protected double relativeDistanceAt(double t) {
        return maxVelocity * t;
    }

    @Override
    protected double relativeAccelerationAt(double t) {
        return 0;
    }

}
