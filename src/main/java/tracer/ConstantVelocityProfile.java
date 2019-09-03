package tracer;

import com.flash3388.flashlib.time.Time;

public class ConstantVelocityProfile extends Profile {
    private final double maxVelocity;

    public ConstantVelocityProfile(Profile prevProfile, Time duration) {
        this(prevProfile.getLength(), prevProfile.getMax(), prevProfile.getAbsoluteFinalTime(), duration);
    }

    public ConstantVelocityProfile(double initialDistance, MotionParameters max, Time startTime, Time duration) {
        super(initialDistance, max.getVelocity(), max, startTime, duration);

        maxVelocity = getMaxVelocity();
    }

    @Override
    public double relativeVelocityAt(double t) {
        return 0;
    }

    @Override
    public double relativeDistanceAt(double t) {
        return maxVelocity * t;
    }

}
