package tracer.profiles;

import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionParameters;

public abstract class Profile {
    private final double initialDistance;
    private final MotionParameters initialParameters;

    private final Time duration;
    private final Time startTime;

    public Profile(Profile prevProfile, Time duration) {
        this(prevProfile.absoluteLength(), prevProfile.endParameters(), prevProfile.end(), duration);
    }

    public Profile(double initialDistance, MotionParameters initialParameters, Time startTime, Time duration) {
        this.initialDistance = initialDistance;
        this.initialParameters = initialParameters;

        this.duration = duration;
        this.startTime = startTime;
    }

    public boolean isCorresponding(Time currentTime) {
        return currentTime.largerThanOrEquals(startTime) && currentTime.lessThanOrEquals(end());
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
        return distanceAt(end());
    }

    public MotionParameters endParameters() {
        return parametersAt(end());
    }

    public MotionParameters parametersAt(Time currentTime) {
        return MotionParameters.centimeterUnits(velocityAt(currentTime), accelerationAt(currentTime), jerkAt(currentTime));
    }

    public double velocityAt(Time currentTime) {
        try {
            checkTime(currentTime);
        } catch (OutsideOfTimeBoundsException e) {
            return relativeVelocityAt(end());
        }
        return relativeVelocityAt(relativeTimeSeconds(currentTime)) + initialParameters.velocity();
    }

    public double distanceAt(Time currentTime) {
        try {
            checkTime(currentTime);
        } catch (OutsideOfTimeBoundsException e) {
            return relativeDistanceAt(end());
        }
        return relativeDistanceAt(relativeTimeSeconds(currentTime)) + initialDistance;
    }

    public double accelerationAt(Time currentTime) {
        try {
            checkTime(currentTime);
        } catch (OutsideOfTimeBoundsException e) {
            return relativeAccelerationAt(end());
        }
        return relativeAccelerationAt(relativeTimeSeconds(currentTime)) + initialParameters.acceleration();
    }

    public double jerkAt(Time currentTime) {
        try {
            checkTime(currentTime);
        } catch (OutsideOfTimeBoundsException e) {
            return relativeJerkAt(end());
        }
        return relativeJerkAt(relativeTimeSeconds(currentTime)) + initialParameters.jerk();
    }

    private Time relativeTimeSeconds(Time currentTime) {
        return currentTime.sub(startTime);
    }

    @Override
    public String toString() {
        return String.format("from: %s, to: %s\nfrom: %f, to: %f",startTime, end(), initialDistance, absoluteLength());
    }

    protected abstract double relativeVelocityAt(Time relativeTime);
    protected abstract double relativeDistanceAt(Time relativeTime);
    protected abstract double relativeAccelerationAt(Time relativeTime);
    protected abstract double relativeJerkAt(Time relativeTime);

    private void checkTime(Time t) {
        if(!isCorresponding(t))
                throw new OutsideOfTimeBoundsException(t);
    }
}
