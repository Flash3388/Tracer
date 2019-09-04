package tracer.motionProfiles;

import com.flash3388.flashlib.time.Time;
import tracer.MotionParameters;

public abstract class Profile {
    private final double initialDistance;
    private final double initialVelocity;
    private final double initialAcceleration;
    
    private final MotionParameters max;
    private final Time duration;
    private final Time startTime;

    public Profile(Profile prevProfile, MotionParameters max, Time duration) {
        this(prevProfile.getLength(), prevProfile.getFinalVelocity(), prevProfile.getFinalAcceleration(), max, prevProfile.getAbsoluteFinalTime(), duration);
    }

    public Profile(double initialDistance, double initialVelocity, double initAcceleration, MotionParameters max, Time startTime, Time duration) {
        this.initialDistance = initialDistance;
        this.initialVelocity = initialVelocity;
        this.initialAcceleration = initAcceleration;

        this.max = max;

        this.duration = duration;
        this.startTime = startTime;
    }

    public boolean isCorresponding(Time currentTime) {
        return currentTime.largerThanOrEquals(startTime) && currentTime.lessThanOrEquals(startTime.add(duration));
    }

    public Time getAbsoluteFinalTime() {
        return startTime.add(duration);
    }

    public MotionParameters getMax() {
        return max;
    }

    public double getMaxVelocity() {
        return max.getVelocity();
    }

    public double getMaxAcceleration() {
        return max.getAcceleration();
    }

    public double getMaxJerk() {
        return max.getJerk();
    }

    public double getInitialVelocity() {
        return initialVelocity;
    }

    public Time getDuration() {
        return duration;
    }

    private double getRelativeTimeSeconds(Time currentTime) {
        return currentTime.sub(startTime).valueAsMillis() / 1000.0;
    }

    public double getLength() {
        try {
            return distanceAt(getAbsoluteFinalTime());
        } catch (OutsideOfTimeBoundsException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public double getFinalVelocity() {
        try {
            return velocityAt(getAbsoluteFinalTime());
        } catch (OutsideOfTimeBoundsException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public double getFinalAcceleration() {
        try {
            return accelerationAt(getAbsoluteFinalTime());
        } catch (OutsideOfTimeBoundsException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public double velocityAt(Time currentTime) throws OutsideOfTimeBoundsException {
        checkTime(currentTime);
        return relativeVelocityAt(currentTime) + initialVelocity;
    }

    public double distanceAt(Time currentTime) throws OutsideOfTimeBoundsException {
        checkTime(currentTime);
        return relativeDistanceAt(currentTime) + initialDistance;
    }
    
    public double accelerationAt(Time currentTime) throws OutsideOfTimeBoundsException {
        checkTime(currentTime);
        return relativeAccelerationAt(currentTime) + initialAcceleration;
    }

    private double relativeVelocityAt(Time currentTime) {
        return relativeVelocityAt(getRelativeTimeSeconds(currentTime));
    }
    private double relativeDistanceAt(Time currentTime) {
        return relativeDistanceAt(getRelativeTimeSeconds(currentTime));
    }
    
    private double relativeAccelerationAt(Time currentTime) {
        return relativeAccelerationAt(getRelativeTimeSeconds(currentTime));
    }

    protected abstract double relativeVelocityAt(double t);
    protected abstract double relativeDistanceAt(double t);
    protected abstract double relativeAccelerationAt(double t);

    private void checkTime(Time t) throws OutsideOfTimeBoundsException {
        if(!isCorresponding(t))
            throw new OutsideOfTimeBoundsException(t);
    }
}
