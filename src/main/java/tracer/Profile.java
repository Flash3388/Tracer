package tracer;

import com.flash3388.flashlib.time.Time;

public abstract class Profile {
    private final double initialDistance;
    private final double initialVelocity;
    private final MotionParameters max;
    private final Time duration;
    private final Time startTime;

    public Profile(double initialDistance, double initialVelocity, MotionParameters max, Time startTime, Time duration) {
        this.initialDistance = initialDistance;
        this.initialVelocity = initialVelocity;
        this.max = max;

        this.duration = duration;
        this.startTime = startTime;
    }

    public boolean isCorresponding(Time currentTime) {
        return currentTime.after(startTime) && currentTime.before(startTime.add(duration));
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
            return distanceAt(duration);
        } catch (OutsideOfTimeBoundsException e) {
            System.out.println(e.getCause().getMessage());
            return 0;
        }
    }

    public double getFinalVelocity() {
        try {
            return velocityAt(duration);
        } catch (OutsideOfTimeBoundsException e) {
            System.out.println(e.getCause().getMessage());
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

    private double relativeVelocityAt(Time currentTime) {
        return relativeVelocityAt(getRelativeTimeSeconds(currentTime));
    }
    private double relativeDistanceAt(Time currentTime) {
        return relativeDistanceAt(getRelativeTimeSeconds(currentTime));
    }

    public abstract double relativeVelocityAt(double t);
    public abstract double relativeDistanceAt(double t);

    private void checkTime(Time t) throws OutsideOfTimeBoundsException {
        if(!isCorresponding(t))
            throw new OutsideOfTimeBoundsException(t);
    }
}
