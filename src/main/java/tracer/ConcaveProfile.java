package tracer;

import com.flash3388.flashlib.time.Time;

public class ConcaveProfile extends Profile {
    private final double maxJerk;

    private final double initialVelocity;

    public ConcaveProfile(Profile prevProfile, MotionParameters max) {
        this(prevProfile.getLength(), prevProfile.getFinalVelocity(), max, prevProfile.getAbsoluteFinalTime());
    }

    public ConcaveProfile(double initialDistance, double initialVelocity, MotionParameters max, Time startTime) {
        super(initialDistance, initialVelocity, max, startTime, calcDuration(max));

        maxJerk = getMaxJerk();
        this.initialVelocity = getInitialVelocity();
    }

    private static Time calcDuration(MotionParameters max) {
        return Time.seconds(max.getAcceleration()/max.getJerk());
    }

    @Override
    public double relativeVelocityAt(double t) {
        return maxJerk * Math.pow(t, 2)/2;
    }

    @Override
    public double relativeDistanceAt(double t) {
        return initialVelocity * t + maxJerk * Math.pow(t, 3)/6;
    }
}
