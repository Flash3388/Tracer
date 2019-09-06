package tracer.motionProfiles;

import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionParameters;

public class ConstantVelocityProfile extends Profile {
    private final double maxVelocity;

    public ConstantVelocityProfile(Profile prevProfile, Time duration) {
        this(prevProfile.absoluteLength(), prevProfile.getMax(), prevProfile.end(), duration);
    }

    public ConstantVelocityProfile(double initialDistance, MotionParameters max, Time startTime, Time duration) {
        super(initialDistance, max.getVelocity(), 0, max, startTime, duration);

        maxVelocity = maxVelocity();
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
