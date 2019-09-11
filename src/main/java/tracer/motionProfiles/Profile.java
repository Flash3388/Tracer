package tracer.motionProfiles;

import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionParameters;

public abstract class Profile {
    private final double initialDistance;
    private final MotionParameters initialParameters;
    
    private final MotionParameters max;
    private final Time duration;
    private final Time startTime;

    public Profile(Profile prevProfile, MotionParameters max, Time duration) {
        this(prevProfile.absoluteLength(), prevProfile.endParameters(), max, prevProfile.end(), duration);
    }

    public Profile(double initialDistance, MotionParameters initialParameters, MotionParameters max, Time startTime, Time duration) {
        this.initialDistance = initialDistance;
        this.initialParameters = initialParameters;

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

    public MotionParameters initialParameters() {
        return initialParameters;
    }

    public MotionParameters maxParameters() {
        return max;
    }

    public double initialDistance() {
        return initialDistance;
    }

    public Time duration() {
        return duration;
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

    public MotionParameters endParameters() {
        try {
            return parametersAt(end());
        } catch (OutsideOfTimeBoundsException e) {
            System.out.println(e.getMessage());
            return MotionParameters.stop();
        }
    }

    public MotionParameters parametersAt(Time currentTime) throws OutsideOfTimeBoundsException {
        return new MotionParameters(velocityAt(currentTime), accelerationAt(currentTime), jerkAt(currentTime));
    }

    public double velocityAt(Time currentTime) throws OutsideOfTimeBoundsException {
        checkTime(currentTime);
        return relativeVelocityAt(relativeTimeSeconds(currentTime)) + initialParameters.velocity();
    }

    public double distanceAt(Time currentTime) throws OutsideOfTimeBoundsException {
        checkTime(currentTime);
        return relativeDistanceAt(relativeTimeSeconds(currentTime)) + initialDistance;
    }

    public double accelerationAt(Time currentTime) throws OutsideOfTimeBoundsException {
        checkTime(currentTime);
        return relativeAccelerationAt(relativeTimeSeconds(currentTime)) + initialParameters.acceleration();
    }

    public double jerkAt(Time currentTime) throws OutsideOfTimeBoundsException {
        checkTime(currentTime);
        return relativeJerkAt(relativeTimeSeconds(currentTime)) + initialParameters.jerk();
    }

    private Time relativeTimeSeconds(Time currentTime) {
        return currentTime.sub(startTime);
    }

    protected abstract double relativeVelocityAt(Time relativeTime);
    protected abstract double relativeDistanceAt(Time relativeTime);
    protected abstract double relativeAccelerationAt(Time relativeTime);
    protected abstract double relativeJerkAt(Time relativeTime);

    private void checkTime(Time t) throws OutsideOfTimeBoundsException {
        if(!isCorresponding(t))
            throw new OutsideOfTimeBoundsException(t);
    }

    @Override
    public String toString() {
        return "from: " + startTime + " to: " + end() + "\n" + "from: " + initialDistance + " to: " + absoluteLength();
    }
}
