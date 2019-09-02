package Tracer;

import com.flash3388.flashlib.time.Time;

public class State {
    private final Time time;
    private final Position position;
    private final MotionParameters motionParameters;
    private final double passedDistance;

    public State(Time time, double x, double y, double heading, double velocity, double acceleration, double jerk, double passedDistance) {
        this.time = new Time(time.value(), time.unit());
        position = new Position(x, y, heading);
        motionParameters = new MotionParameters(velocity, acceleration, jerk);
        this.passedDistance = passedDistance;
    }

    public Time getTime() {
        return time;
    }

    public Position getPosition() {
        return position;
    }

    public MotionParameters getMotionParameters() {
        return motionParameters;
    }

    public double getPassedDistance() {
        return passedDistance;
    }
}
