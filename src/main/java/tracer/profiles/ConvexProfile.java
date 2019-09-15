package tracer.profiles;

import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionParameters;
import util.TimeConversion;

public class ConvexProfile extends Profile {
    private final double maxAcceleration;
    private final double maxJerk;

    private final double initialVelocity;

    public ConvexProfile(Profile prevProfile, MotionParameters target) {
        this(prevProfile.absoluteLength(), prevProfile.endParameters().velocity(), prevProfile.endParameters().acceleration(), target, prevProfile.end());
    }

    public ConvexProfile(double initialDistance, double initialVelocity, double initialAcceleration, MotionParameters target, Time startTime) {
        super(initialDistance, MotionParameters.linearVelocity(initialVelocity, initialAcceleration), startTime, calcDuration(target));

        maxAcceleration = target.acceleration();
        maxJerk = target.jerk();

        this.initialVelocity = initialVelocity;
    }

    private static Time calcDuration(MotionParameters target) {
        return Time.seconds(target.acceleration()/target.jerk());
    }

    @Override
    protected double relativeVelocityAt(Time t) {
        double timeInSeconds = TimeConversion.toSeconds(t);
        return maxAcceleration * timeInSeconds -maxJerk * Math.pow(timeInSeconds, 2)/2;
    }

    @Override
    protected double relativeDistanceAt(Time t) {
        double timeInSeconds = TimeConversion.toSeconds(t);
        return initialVelocity * timeInSeconds + maxAcceleration * Math.pow(timeInSeconds, 2)/2 - maxJerk * Math.pow(timeInSeconds, 3)/6;
    }

    @Override
    protected double relativeAccelerationAt(Time t) {
        double timeInSeconds = TimeConversion.toSeconds(t);
        return timeInSeconds * -maxJerk;
    }

    @Override
    protected double relativeJerkAt(Time relativeTime) {
        return -maxJerk;
    }
}
