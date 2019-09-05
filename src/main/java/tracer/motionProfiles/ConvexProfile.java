package tracer.motionProfiles;

import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionParameters;

public class ConvexProfile extends Profile {
    private final double maxAcceleration;
    private final double maxJerk;

    private final double initialVelocity;

    public ConvexProfile(Profile prevProfile, MotionParameters max) {
        this(prevProfile.length(), prevProfile.finalVelocity(), max, prevProfile.getAbsoluteFinalTime());
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
    protected double relativeVelocityAt(double t) {
        return maxAcceleration * t -maxJerk * Math.pow(t, 2)/2;
    }

    @Override
    protected double relativeDistanceAt(double t) {
        return initialVelocity * t + maxAcceleration * Math.pow(t, 2)/2 -maxJerk * Math.pow(t, 3)/6;
    }

    @Override
    protected double relativeAccelerationAt(double t) {
        return t * -maxJerk;
    }
}
