package tracer.profiles;

import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionParameters;

public abstract class Profile {
    private final double initialDistance;
    private final MotionParameters initialParameters;

    private final Time duration;
    private final Time startTime;

    public Profile(Profile prevProfile, Time duration) {
        this(prevProfile.absoluteLength(), prevProfile.finalParameters(), prevProfile.finaltimestamp(), duration);
    }

    public Profile(double initialDistance, MotionParameters initialParameters, Time startTime, Time duration) {
        this.initialDistance = initialDistance;
        this.initialParameters = initialParameters;

        this.duration = duration;
        this.startTime = startTime;
    }

    public boolean hasValuesOn(Time currentTime) {
        return currentTime.largerThanOrEquals(startTime) && currentTime.lessThanOrEquals(finaltimestamp());
    }

    public Time initialtimestamp() {
        return startTime;
    }

    public Time finaltimestamp() {
        return startTime.add(duration);
    }

    public MotionParameters initialParameters() {
        return initialParameters;
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
        return distanceAt(finaltimestamp());
    }

    public MotionParameters finalParameters() {
        return parametersAt(finaltimestamp());
    }

    public MotionParameters parametersAt(Time currentTime) {
        return new MotionParameters(velocityAt(currentTime), accelerationAt(currentTime), jerkAt(currentTime));
    }

    public double velocityAt(Time currentTime) {
        currentTime = correctTime(currentTime);
        return relativeVelocityAt(relativeTime(currentTime)) + initialParameters.velocity();
    }

    public double distanceAt(Time currentTime) {
        currentTime = correctTime(currentTime);
        return relativeDistanceAt(relativeTime(currentTime)) + initialDistance;
    }

    public double accelerationAt(Time currentTime) {
        currentTime = correctTime(currentTime);
        return relativeAccelerationAt(relativeTime(currentTime)) + initialParameters.acceleration();
    }

    public double jerkAt(Time currentTime) {
        currentTime = correctTime(currentTime);
        return relativeJerkAt(relativeTime(currentTime)) + initialParameters.jerk();
    }

    private Time correctTime(Time time) {
        if(time.after(finaltimestamp()))
            return finaltimestamp();
        else if(time.before(initialtimestamp()))
            return initialtimestamp();
        return time;
    }

    private Time relativeTime(Time currentTime) {
        return currentTime.sub(startTime);
    }

    @Override
    public String toString() {
        return String.format("from: %s, to: %s\nfrom: %f, to: %f",startTime, finaltimestamp(), initialDistance, absoluteLength());
    }

    protected abstract double relativeVelocityAt(Time relativeTime);
    protected abstract double relativeDistanceAt(Time relativeTime);
    protected abstract double relativeAccelerationAt(Time relativeTime);
    protected abstract double relativeJerkAt(Time relativeTime);
}
