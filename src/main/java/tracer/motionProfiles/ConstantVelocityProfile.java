package tracer.motionProfiles;

import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionParameters;
import util.TimeConversion;

public class ConstantVelocityProfile extends Profile {
    private final double initialVelocity;

    public ConstantVelocityProfile(Profile prevProfile, Time duration) {
        this(prevProfile.absoluteLength(), prevProfile.endParameters().velocity(), prevProfile.maxParameters(), prevProfile.end(), duration);
    }

    public ConstantVelocityProfile(double initialDistance, double initialVelocity, MotionParameters max, Time startTime, Time duration) {
        super(initialDistance, MotionParameters.constantVelocity(initialVelocity), max, startTime, duration);

        this.initialVelocity = initialVelocity;
    }

    @Override
    protected double relativeVelocityAt(Time t) {
        return 0;
    }

    @Override
    protected double relativeDistanceAt(Time t) {
        double timeInSeconds = TimeConversion.toSeconds(t);
        return initialVelocity * timeInSeconds;
    }

    @Override
    protected double relativeAccelerationAt(Time t) {
        return 0;
    }

    @Override
    protected double relativeJerkAt(Time relativeTime) {
        return 0;
    }

}
