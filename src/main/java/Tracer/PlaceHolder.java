package Tracer;

import com.flash3388.flashlib.time.Time;

import java.util.Objects;

public class PlaceHolder {
    private final Trajectory trajectory;
    private final MotionParameters startMaxMotionParameters;
    private final MotionParameters endMaxMotionParameters;

    private final Time startOfConstantVelocity;
    private final Time startOfEndSCurve;

    private final Time startOfLinear;
    private final Time startOfConvex;

    private final double startSCurveLinearStartVelocity;
    private final double startSCurveLinearStartDistance;
    private final double startSCurveConvexStartVelocity;
    private final double startSCurveConvexStartDistance;

    private final double endSCurveLinearStartVelocity;
    private final double endSCurveLinearStartDistance;
    private final double endSCurveConvexStartVelocity;
    private final double endSCurveConvexStartDistance;

    private final double trajectoryLength;

    private final double linearStartDistance;
    private final double endSCurveStartVelocity;
    private final double endSCurveStartDistance;

    public PlaceHolder(Trajectory trajectory, MotionParameters maxMotionParameters) {
        this.trajectory = trajectory;
        this.startMaxMotionParameters = maxMotionParameters;
        endMaxMotionParameters = new MotionParameters(maxMotionParameters.getVelocity(), -maxMotionParameters.getAcceleration(), -maxMotionParameters.getJerk());

        startOfLinear = calcLinearStart(maxMotionParameters);
        startOfConvex = calcConvexStart(maxMotionParameters);

        startOfConstantVelocity = startOfConvex.add(Objects.requireNonNull(startOfLinear));
        startOfEndSCurve = calcEndSCurveLength(maxMotionParameters);

        startSCurveLinearStartVelocity = calcLinearStartVelocity(startMaxMotionParameters);
        startSCurveLinearStartDistance = calcLinearStartDistance(startMaxMotionParameters);
        startSCurveConvexStartVelocity = calcConvexStartVelocity(startMaxMotionParameters);
        startSCurveConvexStartDistance = calcConvexStartDistance(startMaxMotionParameters);

        endSCurveLinearStartVelocity = calcLinearStartVelocity(endMaxMotionParameters);
        endSCurveLinearStartDistance = calcLinearStartDistance(endMaxMotionParameters);
        endSCurveConvexStartVelocity = calcConvexStartVelocity(endMaxMotionParameters);
        endSCurveConvexStartDistance = calcConvexStartDistance(endMaxMotionParameters);

        trajectoryLength = trajectory.getLength();

        linearStartDistance = calcConstantVelocityStartDistance();
        endSCurveStartVelocity = calcEndSCurveStartVelocity();
        endSCurveStartDistance = calcEndSCurveStartDistance();
    }

    private double calcLinearStartVelocity(MotionParameters maxMotionParameters) {
        return Math.pow(maxMotionParameters.getAcceleration(), 2)/ (2 * maxMotionParameters.getJerk());
    }

    private double calcLinearStartDistance(MotionParameters maxMotionParameters) {
        double maxAccelerations = maxMotionParameters.getAcceleration();
        double maxJerk = maxMotionParameters.getJerk();

        return (Math.pow(maxAccelerations, 2)/ (6 * maxJerk)) * maxAccelerations/ maxJerk;
    }

    private double calcConvexStartVelocity(MotionParameters maxMotionParameters) {
        return maxMotionParameters.getVelocity() - Math.pow(maxMotionParameters.getAcceleration(), 2)/ (2 * maxMotionParameters.getJerk());
    }

    private double calcConvexStartDistance(MotionParameters maxMotionParameters) {
        double linearLength = startOfConvex.sub(startOfLinear).valueAsMillis() / 1000.0;

        return calcLinearStartDistance(maxMotionParameters) * linearLength +
                maxMotionParameters.getAcceleration() * Math.pow(linearLength, 2) / 2;
    }

    private Time calcLinearStart(MotionParameters maxMotionParameters) {
        return Time.seconds(maxMotionParameters.getAcceleration()/maxMotionParameters.getJerk());
    }

    private Time calcConvexStart(MotionParameters maxMotionParameters) {
        double maxAcceleration = maxMotionParameters.getAcceleration();
        double maxJerk =  maxMotionParameters.getJerk();
        double maxVelocity = maxMotionParameters.getVelocity();
        double velocityAtLinearStart = Math.pow(maxAcceleration, 2) / (2 * maxJerk);

        return Time.seconds((maxVelocity - velocityAtLinearStart)/maxAcceleration);
    }

    private Time calcEndSCurveLength(MotionParameters maxMotionParameters) {
        double distancePassedInSCurves = distanceAtStartSCurve(startOfConstantVelocity) * 2;

        return startOfConstantVelocity.add(Time.seconds((trajectoryLength - distancePassedInSCurves) / maxMotionParameters.getVelocity()));
    }

    private double calcEndSCurveStartVelocity() {
        return startMaxMotionParameters.getVelocity();
    }

    private double calcConstantVelocityStartDistance() {
        return distanceAtStartSCurve(startOfLinear);
    }

    private double calcEndSCurveStartDistance() {
        return distanceAtConstantVelocity(startOfEndSCurve) + calcConstantVelocityStartDistance();
    }

    public double velocityAt(Time time) {
        if(time.before(startOfLinear))
            return velocityAtStartSCurve(time);
        else if(time.before(startOfEndSCurve))
            return startMaxMotionParameters.getVelocity();
        else
            return velocityAtEndSCurve(time);
    }

    public double distanceAt(Time time) {
        if(time.before(startOfLinear))
            return distanceAtStartSCurve(time);
        else if(time.before(startOfEndSCurve))
            return distanceAtConstantVelocity(time.sub(startOfLinear));
        else
            return distanceAtEndSCurve(time.sub(startOfConstantVelocity));
    }

    public double angleAt(Time time) {
        return trajectory.getAngleAt(distanceAt(time));
    }

    private double velocityAtStartSCurve(Time currentTime) {
        return velocityAtSCurve(currentTime, 0.0, startMaxMotionParameters);
    }

    private double velocityAtEndSCurve(Time currentTime) {
        return velocityAtSCurve(currentTime.sub(startOfEndSCurve), endSCurveStartVelocity, endMaxMotionParameters);
    }

    private double velocityAtSCurve(Time time, double startVelocity, MotionParameters maxMotionParameters) {
        if(time.before(startOfLinear))
            return velocityAtConcave(time, maxMotionParameters) + startVelocity;
        else if(time.before(startOfConvex))
            return velocityAtLinear(time.sub(startOfLinear), maxMotionParameters);
        else
            return velocityAtConvex(time.sub(startOfConvex), maxMotionParameters);
    }

    private double velocityAtConcave(Time time, MotionParameters maxMotionParameters) {
        return 0.0;
    }

    private double velocityAtLinear(Time time, MotionParameters maxMotionParameters) {
        return 0.0;
    }

    private double velocityAtConvex(Time time, MotionParameters maxMotionParameters) {
        return 0.0;
    }

    private double distanceAtStartSCurve(Time currentTime) {
        return distanceAtSCurve(currentTime, 0.0, startMaxMotionParameters);
    }

    private double distanceAtEndSCurve(Time currentTime) {
        return distanceAtSCurve(currentTime.sub(startOfEndSCurve), endSCurveStartDistance, endMaxMotionParameters);
    }

    private double distanceAtSCurve(Time time, double startDistance, MotionParameters maxMotionParameters) {
        if(time.before(startOfLinear))
            return distanceAtConcave(time, maxMotionParameters) + startDistance;
        else if(time.before(startOfConvex))
            return distanceAtLinear(time.sub(startOfLinear), maxMotionParameters);
        else
            return distanceAtConvex(time.sub(startOfConvex), maxMotionParameters);
    }

    private double distanceAtConcave(Time time, MotionParameters maxMotionParameters) {
        return 0.0;
    }

    private double distanceAtLinear(Time time, MotionParameters maxMotionParameters) {
        return 0.0;
    }

    private double distanceAtConvex(Time time, MotionParameters maxMotionParameters) {
        return 0.0;
    }

    private double distanceAtConstantVelocity(Time time) {
        return 0.0 + linearStartDistance;
    }
}
