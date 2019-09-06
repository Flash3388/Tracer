package tracer.motionProfiles;

import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionParameters;
import util.TimeConversion;

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
    protected double relativeVelocityAt(Time t) {
        return 0;
    }

    @Override
    protected double relativeDistanceAt(Time t) {
        double timeInSeconds = TimeConversion.toSeconds(t);
        return maxVelocity * timeInSeconds;
    }

    @Override
    protected double relativeAccelerationAt(Time t) {
        return 0;
    }

}
