package Tracer;

import com.flash3388.flashlib.time.Time;

public class PlaceHolder {
    private final Trajectory trajectory;
    private final MotionParameters maxMotionParameters;
    private final Time startOfStartSCurve;
    private final Time startOfEndSCurve;
    private final Time startOfConstantVelocity;
    private final Time startOfConcave;
    private final Time startOfLinear;
    private final Time startOfConvex;

    public PlaceHolder(Trajectory trajectory, MotionParameters maxMotionParameters) {
        this.trajectory = trajectory;
        this.maxMotionParameters = maxMotionParameters;

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

    public double VelocityAt(Time time) {
        return 0.0;
    }

    public double distanceAt(Time time) {
        return 0.0;
    }

    public double angleAt(Time time) {
        return 0.0;
    }

    private double velocityAtStartSCurve(Time currentTime) {
        return 0.0;
    }

    private double velocityAtEndSCurve(Time currentTime) {
        return 0.0;
    }

    private double velocityAtSCurve(Time currentTime) {
        return 0.0;
    }

    private double velocityAtSCurve(Time time, State startState, State endState) {
        return 0.0;
    }

    private double velocityAtConvex(Time time, State startState, State endState) {
        return 0.0;
    }

    private double velocityAtLinear(Time time, State startState, State endState) {
        return 0.0;
    }

    private double velocityAtConcave(Time time, State startState, State endState) {
        return 0.0;
    }

    private double distanceAtStartSCurve(Time currentTime) {
        return 0.0;
    }

    private double distanceAtEndSCurve(Time currentTime) {
        return 0.0;
    }

    private double distanceAtSCurve(Time currentTime) {
        return 0.0;
    }

    private double distanceAtSCurve(Time time, State startState, State endState) {
        return 0.0;
    }

    private double distanceAtConvex(Time time, State startState, State endState) {
        return 0.0;
    }

    private double distanceAtLinear(Time time, State startState, State endState) {
        return 0.0;
    }

    private double distanceAtConcave(Time time, State startState, State endState) {
        return 0.0;
    }

    private double distanceAtConstantVelocity(Time time) {
        return 0.0;
    }
}
