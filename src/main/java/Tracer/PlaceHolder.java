package Tracer;

import com.flash3388.flashlib.time.Time;

public class PlaceHolder {
    private final Trajectory trajectory;
    private final MotionParameters startMaxMotionParameters;
    private final MotionParameters endMaxMotionParameters;

    private final double stop;
    private final double afterLinearVelocity;

    private final Time startOfStartSCurve;
    private final Time startOfConstantVelocity;
    private final Time startOfEndSCurve;

    private final Time startOfConcave;
    private final Time startOfLinear;
    private final Time startOfConvex;

    public PlaceHolder(Trajectory trajectory, MotionParameters maxMotionParameters) {
        this.trajectory = trajectory;
        this.startMaxMotionParameters = maxMotionParameters;
        endMaxMotionParameters = new MotionParameters(maxMotionParameters.getVelocity(), -maxMotionParameters.getAcceleration(), -maxMotionParameters.getJerk());
        stop = 0.0;
        afterLinearVelocity = maxMotionParameters.getVelocity();

        startOfConcave = Time.seconds(0.0);
        startOfLinear = calcLinearStart(maxMotionParameters);
        startOfConvex = calcConvexStart(maxMotionParameters);

        startOfStartSCurve = Time.seconds(0.0);
        startOfConstantVelocity = startOfLinear.add(startOfConvex);
        startOfEndSCurve = startOfStartSCurve.add(calcEndSCurveLength(maxMotionParameters));
    }

    private Time calcLinearStart(MotionParameters maxMotionParameters) {
        return null;
    }

    private Time calcConvexStart(MotionParameters maxMotionParameters) {
        return null;
    }

    private Time calcEndSCurveLength(MotionParameters maxMotionParameters) {
        return null;
    }

    public double velocityAt(Time time) {
        return 0.0;
    }

    public double distanceAt(Time time) {
        return 0.0;
    }

    public double angleAt(Time time) {
        return trajectory.getAngleAt(distanceAt(time));
    }

    private double velocityAtStartSCurve(Time currentTime) {
        return velocityAtSCurve(currentTime, stop, startMaxMotionParameters);
    }

    private double velocityAtEndSCurve(Time currentTime) {
        return velocityAtSCurve(currentTime, afterLinearVelocity, endMaxMotionParameters);
    }

    private double velocityAtSCurve(Time time, double startVelocity, MotionParameters endState) {
        return 0.0;
    }

    private double velocityAtConcave(Time time, double startVelocity, MotionParameters endState) {
        return 0.0;
    }

    private double velocityAtLinear(Time time, double startVelocity, MotionParameters endState) {
        return 0.0;
    }

    private double velocityAtConvex(Time time, double startVelocity, MotionParameters endState) {
        return 0.0;
    }

    private double distanceAtStartSCurve(Time currentTime) {
        return 0.0;
    }

    private double distanceAtEndSCurve(Time currentTime) {
        return 0.0;
    }

    private double distanceAtSCurve(Time time, double startVelocity, State endState) {
        return 0.0;
    }

    private double distanceAtConcave(Time time, double startVelocity, State endState) {
        return 0.0;
    }

    private double distanceAtLinear(Time time, double startVelocity, State endState) {
        return 0.0;
    }

    private double distanceAtConvex(Time time, double startVelocity, State endState) {
        return 0.0;
    }

    private double distanceAtConstantVelocity(Time time) {
        return 0.0;
    }
}
