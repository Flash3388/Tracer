package tracer.profiles;

import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionState;

public class ProfileState {
    private final double distance;
    private final MotionState parameters;
    private final Time timestamp;

    public ProfileState() {
        this(0, MotionState.stop(), Time.milliseconds(0));
    }

    public ProfileState(double distance, MotionState parameters, Time timestamp) {
        this.distance = distance;
        this.parameters = parameters;
        this.timestamp = timestamp;
    }

    public double distance() {
        return distance;
    }

    public MotionState parameters() {
        return parameters;
    }

    public Time timestamp() {
        return timestamp;
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

    public ProfileState add(ProfileState other) {
        return new ProfileState(distance + other.distance(), parameters.add(other.parameters()), timestamp.add(other.timestamp()));
    }

    public ProfileState sub(ProfileState other) {
        return new ProfileState(distance - other.distance(), parameters.sub(other.parameters()), timestamp.sub(other.timestamp()));
    }
}
