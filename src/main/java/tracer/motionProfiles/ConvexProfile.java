package tracer.motionProfiles;

import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionParameters;
import util.TimeConversion;

public class ConvexProfile extends Profile {
    private final double maxAcceleration;
    private final double maxJerk;

    private final double initialVelocity;

    public ConvexProfile(Profile prevProfile, MotionParameters max) {
        this(prevProfile.absoluteLength(), prevProfile.finalVelocity(), max, prevProfile.end());
    }

    public ConvexProfile(double initialDistance, double initialVelocity, MotionParameters max, Time startTime) {
        super(initialDistance, initialVelocity, max.getAcceleration(), max, startTime, calcDuration(max));

        maxAcceleration = maxAcceleration();
        maxJerk = maxJerk();

        this.initialVelocity = initialVelocity();
    }

    private static Time calcDuration(MotionParameters max) {
        return Time.seconds(max.getAcceleration()/max.getJerk());
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
}
