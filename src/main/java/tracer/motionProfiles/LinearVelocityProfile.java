package tracer.motionProfiles;

import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionParameters;
import util.TimeConversion;

public class LinearVelocityProfile extends Profile {
    private final double initialAcceleration;
    private final double initialVelocity;

    public LinearVelocityProfile(Profile prevProfile, MotionParameters max, Time duration) {
        this(prevProfile.absoluteLength(), prevProfile.endParameters().velocity(), prevProfile.endParameters().acceleration(), max, prevProfile.end(), duration);
    }

    public LinearVelocityProfile(double initialDistance, double initialVelocity, double initialAcceleration, MotionParameters max, Time startTime, Time duration) {
        super(initialDistance, MotionParameters.linearVelocity(initialVelocity, initialAcceleration), max, startTime, duration);

        this.initialAcceleration = initialAcceleration;
        this.initialVelocity = initialVelocity;
    }

    @Override
    protected double relativeVelocityAt(Time t) {
        double timeInSeconds = TimeConversion.toSeconds(t);
        return initialAcceleration * timeInSeconds;
    }

    @Override
    protected double relativeDistanceAt(Time t) {
        double timeInSeconds = TimeConversion.toSeconds(t);
        return initialVelocity * timeInSeconds + initialAcceleration * Math.pow(timeInSeconds, 2)/2;
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
