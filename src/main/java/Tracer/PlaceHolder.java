package Tracer;

import com.flash3388.flashlib.time.Time;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class PlaceHolder {
    private final Trajectory trajectory;
    private final MotionParameters startMaxMotionParameters;
    private final MotionParameters endMaxMotionParameters;

    private final double linearStartVelocity;
    private final double linearStartDistance;
    private final double endSCurveStartVelocity;
    private final double endSCurveStartDistance;

    private final Time startOfConstantVelocity;
    private final Time startOfEndSCurve;

    private final Time startOfLinear;
    private final Time startOfConvex;

    private final double trajectoryLength;

    public PlaceHolder(Trajectory trajectory, MotionParameters maxMotionParameters) {
        this.trajectory = trajectory;
        this.startMaxMotionParameters = maxMotionParameters;
        endMaxMotionParameters = new MotionParameters(maxMotionParameters.getVelocity(), -maxMotionParameters.getAcceleration(), -maxMotionParameters.getJerk());

        startOfLinear = calcLinearStart(maxMotionParameters);
        startOfConvex = calcConvexStart(maxMotionParameters);

        startOfConstantVelocity = startOfConvex.add(Objects.requireNonNull(startOfLinear));
        startOfEndSCurve = calcEndSCurveLength(maxMotionParameters);

        trajectoryLength = trajectory.getLength();

        linearStartVelocity = calcLinearStartVelocity();
        linearStartDistance = calcLinearStartDistance();
        endSCurveStartVelocity = calcEndSCurveStartVelocity();
        endSCurveStartDistance = calcEndSCurveStartDistance();
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

    private double calcLinearStartVelocity() {
        return startMaxMotionParameters.getVelocity();
    }

    private double calcEndSCurveStartVelocity() {
        return startMaxMotionParameters.getVelocity();
    }

    private double calcLinearStartDistance() {
        return distanceAtStartSCurve(startOfLinear);
    }

    private double calcEndSCurveStartDistance() {
        return distanceAtConstantVelocity(startOfEndSCurve) + calcLinearStartDistance();
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
        return velocityAtSCurve(currentTime, startMaxMotionParameters);
    }

    private double velocityAtEndSCurve(Time currentTime) {
        return velocityAtSCurve(currentTime.sub(startOfEndSCurve), endMaxMotionParameters);
    }

    private double velocityAtSCurve(Time time, MotionParameters maxMotionParameters) {
        if(time.before(startOfLinear))
            return velocityAtConcave(time, maxMotionParameters);
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
        return distanceAtSCurve(currentTime, endMaxMotionParameters);
    }

    private double distanceAtEndSCurve(Time currentTime) {
        return distanceAtSCurve(currentTime.sub(startOfEndSCurve), endMaxMotionParameters);
    }

    private double distanceAtSCurve(Time time, MotionParameters maxMotionParameters) {
        if(time.before(startOfLinear))
            return distanceAtConcave(time, maxMotionParameters);
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
        return 0.0;
    }
}
