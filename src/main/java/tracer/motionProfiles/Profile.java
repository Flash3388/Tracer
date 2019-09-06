package tracer.motionProfiles;

import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionParameters;

public abstract class Profile {
    private final double initialDistance;
    private final double initialVelocity;
    private final double initialAcceleration;
    
    private final MotionParameters max;
    private final Time duration;
    private final Time startTime;

    public Profile(Profile prevProfile, MotionParameters max, Time duration) {
        this(prevProfile.absoluteLength(), prevProfile.finalVelocity(), prevProfile.finalAcceleration(), max, prevProfile.end(), duration);
    }

    public Profile(double initialDistance, double initialVelocity, double initAcceleration, MotionParameters max, Time startTime, Time duration) {
        this.initialDistance = initialDistance;
        this.initialVelocity = initialVelocity;
        this.initialAcceleration = initAcceleration;

        this.max = max;

        this.duration = duration;
        this.startTime = startTime;
    }

    public boolean isCorrespondingRelative(Time relative) {
        return isCorresponding(relative.add(startTime));
    }

    public boolean isCorresponding(Time currentTime) {
        return currentTime.largerThanOrEquals(startTime) && currentTime.lessThanOrEquals(startTime.add(duration));
    }

    public Time start() {
        return startTime;
    }

    public Time end() {
        return startTime.add(duration);
    }

    public MotionParameters getMax() {
        return max;
    }

    public double maxVelocity() {
        return max.getVelocity();
    }

    public double maxAcceleration() {
        return max.getAcceleration();
    }

    public double maxJerk() {
        return max.getJerk();
    }

    public double initialDistance() {
        return initialDistance;
    }

    public double initialVelocity() {
        return initialVelocity;
    }

    public double initialAcceleration() {
        return initialAcceleration;
    }

    public Time duration() {
        return duration;
    }

    private double relativeTimeSeconds(Time currentTime) {
        return currentTime.sub(startTime).valueAsMillis() / 1000.0;
    }

    public double length() {
        return absoluteLength() - initialDistance;
    }

    public double absoluteLength() {
        try {
            return distanceAt(end());
        } catch (OutsideOfTimeBoundsException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public double finalVelocity() {
        try {
            return velocityAt(end());
        } catch (OutsideOfTimeBoundsException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public double finalAcceleration() {
        try {
            return accelerationAt(end());
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
        return relativeVelocityAt(relativeTimeSeconds(currentTime));
    }
    private double relativeDistanceAt(Time currentTime) {
        return relativeDistanceAt(relativeTimeSeconds(currentTime));
    }
    
    private double relativeAccelerationAt(Time currentTime) {
        return relativeAccelerationAt(relativeTimeSeconds(currentTime));
    }

    protected abstract double relativeVelocityAt(double t);
    protected abstract double relativeDistanceAt(double t);
    protected abstract double relativeAccelerationAt(double t);

    private void checkTime(Time t) throws OutsideOfTimeBoundsException {
        if(!isCorresponding(t))
            throw new OutsideOfTimeBoundsException(t);
    }

    @Override
    public String toString() {
        return "from: " + startTime + " to: " + end() + "\n" + "from: " + initialDistance + " to: " + absoluteLength();
    }
}
