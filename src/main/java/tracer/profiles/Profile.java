package tracer.profiles;

import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionParameters;
import tracer.motion.basic.Acceleration;
import tracer.motion.basic.Distance;
import tracer.motion.basic.Jerk;
import tracer.motion.basic.Velocity;

public abstract class Profile {
    private final Distance initialDistance;
    private final MotionParameters initialParameters;

    private final Time duration;
    private final Time startTime;

    public Profile(Profile prevProfile, Time duration) {
        this(prevProfile.absoluteLength(), prevProfile.endParameters(), prevProfile.end(), duration);
    }

    public Profile(Distance initialDistance, MotionParameters initialParameters, Time startTime, Time duration) {
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

    public Distance initialDistance() {
        return initialDistance;
    }

    public Time duration() {
        return duration;
    }

    public Distance length() {
        return absoluteLength().sub(initialDistance);
    }

    public Distance absoluteLength() {
        return distanceAt(end());
    }

    public MotionParameters endParameters() {
        return parametersAt(end());
    }

    public MotionParameters parametersAt(Time currentTime) {
        return new MotionParameters(velocityAt(currentTime), accelerationAt(currentTime), jerkAt(currentTime));
    }

    public Velocity velocityAt(Time currentTime) {
        try {
            checkTime(currentTime);
        } catch (OutsideOfTimeBoundsException e) {
            return relativeVelocityAt(end());
        }
        return relativeVelocityAt(relativeTimeSeconds(currentTime)).add(initialParameters.velocity());
    }

    public Distance distanceAt(Time currentTime) {
        try {
            checkTime(currentTime);
        } catch (OutsideOfTimeBoundsException e) {
            return relativeDistanceAt(end());
        }
        return relativeDistanceAt(relativeTimeSeconds(currentTime)).add(initialDistance);
    }

    public Acceleration accelerationAt(Time currentTime) {
        try {
            checkTime(currentTime);
        } catch (OutsideOfTimeBoundsException e) {
            return relativeAccelerationAt(end());
        }
        return relativeAccelerationAt(relativeTimeSeconds(currentTime)).add(initialParameters.acceleration());
    }

    public Jerk jerkAt(Time currentTime) {
        try {
            checkTime(currentTime);
        } catch (OutsideOfTimeBoundsException e) {
            return relativeJerkAt(end());
        }
        return relativeJerkAt(relativeTimeSeconds(currentTime)).add(initialParameters.jerk());
    }

    private Time relativeTimeSeconds(Time currentTime) {
        return currentTime.sub(startTime);
    }

    @Override
    public String toString() {
        return "from: " + startTime + " to: " + end() + "\n" + "from: " + initialDistance + " to: " + absoluteLength();
    }

    protected abstract Velocity relativeVelocityAt(Time relativeTime);
    protected abstract Distance relativeDistanceAt(Time relativeTime);
    protected abstract Acceleration relativeAccelerationAt(Time relativeTime);
    protected abstract Jerk relativeJerkAt(Time relativeTime);

    private void checkTime(Time t) throws OutsideOfTimeBoundsException {
        if(!isCorresponding(t))
                throw new OutsideOfTimeBoundsException(t);
    }
}
