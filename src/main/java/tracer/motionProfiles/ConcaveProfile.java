package tracer.motionProfiles;

import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionParameters;

public class ConcaveProfile extends Profile {
    private final double maxJerk;

    private final double initialVelocity;

    public ConcaveProfile(Profile prevProfile, MotionParameters max) {
        this(prevProfile.absoluteLength(), prevProfile.finalVelocity(), max, prevProfile.end());
    }

    public ConcaveProfile(double initialDistance, double initialVelocity, MotionParameters max, Time startTime) {
        super(initialDistance, initialVelocity, 0, max, startTime, calcDuration(max));

        maxJerk = maxJerk();
        this.initialVelocity = initialVelocity();
    }

    private static Time calcDuration(MotionParameters max) {
        return Time.seconds(max.getAcceleration()/max.getJerk());
    }

    @Override
    protected double relativeVelocityAt(double t) {
        return maxJerk * Math.pow(t, 2)/2;
    }

    @Override
    protected double relativeDistanceAt(double t) {
        return initialVelocity * t + maxJerk * Math.pow(t, 3)/6;
    }

    @Override
    protected double relativeAccelerationAt(double t) {
        return t * maxJerk;
    }
}
