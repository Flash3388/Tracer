package Tracer;

import com.flash3388.flashlib.time.Time;

public class Step {
    private final Time timestamp;
    private final Position position;
    private final MotionParameters motionParameters;

    public Step(Time timestamp, double x, double y, double heading, double velocity, double acceleration, double jerk) {
        this.timestamp = new Time(timestamp.value(), timestamp.unit());
        position = new Position(x, y, heading);
        motionParameters = new MotionParameters(velocity, acceleration, jerk);
    }

    public Time getTimestamp() {
        return timestamp;
    }

    public Position getPosition() {
        return position;
    }

    public MotionParameters getMotionParameters() {
        return motionParameters;
    }
}
