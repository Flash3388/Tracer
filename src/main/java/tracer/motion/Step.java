package tracer.motion;

import com.flash3388.flashlib.time.Time;

public class Step {
    private final PhysicalPosition position;
    private final MotionParameters parameters;

    public Step(PhysicalPosition position, MotionParameters parameters) {
        this.position = position;
        this.parameters = parameters;
    }

    public static Step start() {
        return new Step(PhysicalPosition.start(), MotionParameters.stop());
    }

    public MotionParameters getParameters() {
        return parameters;
    }

    public PhysicalPosition getPosition() {
        return position;
    }

    public double distance() {
        return position.getDistance();
    }

    public double angle() {
        return position.getAngle();
    }

    public double velocity() {
        return parameters.velocity();
    }

    public double acceleration() {
        return parameters.acceleration();
    }

    public double jerk() {
        return parameters.jerk();
    }

    public Time getTiming() {
        return position.getTiming();
    }
}