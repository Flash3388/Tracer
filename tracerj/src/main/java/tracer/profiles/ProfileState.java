package tracer.profiles;

import com.flash3388.flashlib.time.Time;
import com.jmath.ExtendedMath;
import tracer.motion.MotionState;

public class ProfileState {
    private final static double DEFAULT_DELTA = 0.001;

    private final double distance;
    private final MotionState state;
    private final Time timestamp;

    public ProfileState() {
        this(0, MotionState.none(), Time.milliseconds(0));
    }

    public ProfileState(double distance, MotionState state, Time timestamp) {
        this.distance = distance;
        this.state = state;
        this.timestamp = timestamp;
    }

    public double distance() {
        return distance;
    }

    public MotionState motionState() {
        return state;
    }

    public Time timestamp() {
        return timestamp;
    }

    public double velocity() {
        return state.velocity();
    }

    public double acceleration() {
        return state.acceleration();
    }

    public double jerk() {
        return state.jerk();
    }

    public ProfileState add(ProfileState other) {
        return new ProfileState(distance + other.distance(), state.add(other.motionState()), timestamp.add(other.timestamp()));
    }

    public ProfileState sub(ProfileState other) {
        return new ProfileState(distance - other.distance(), state.sub(other.motionState()), timestamp.sub(other.timestamp()));
    }

    public boolean equals(ProfileState other) {
        return ExtendedMath.equals(distance, other.distance(), DEFAULT_DELTA) &&
                state.equals(other.motionState()) &&
                timestamp.equals(other.timestamp());
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof ProfileState && equals((ProfileState) o);
    }

    @Override
    public String toString() {
        return String.format("distance: %.4f, motion: %s, time: %s", distance, state, timestamp);
    }
}
