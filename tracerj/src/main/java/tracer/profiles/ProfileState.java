package tracer.profiles;

import com.flash3388.flashlib.time.Time;
import com.jmath.ExtendedMath;
import tracer.motion.MotionState;
import tracer.units.distance.Distance;
import tracer.units.morion.Acceleration;
import tracer.units.morion.Jerk;
import tracer.units.morion.Velocity;

public class ProfileState {
    private final Distance distance;
    private final MotionState state;
    private final Time timestamp;

    public ProfileState() {
        this(Distance.meters(0), MotionState.stop(), Time.milliseconds(0));
    }

    public ProfileState(Distance distance, MotionState state, Time timestamp) {
        this.distance = distance;
        this.state = state;
        this.timestamp = timestamp;
    }

    public Distance distance() {
        return distance;
    }

    public MotionState parameters() {
        return state;
    }

    public Time timestamp() {
        return timestamp;
    }

    public Velocity velocity() {
        return state.velocity();
    }

    public Acceleration acceleration() {
        return state.acceleration();
    }

    public Jerk jerk() {
        return state.jerk();
    }

    public ProfileState add(ProfileState other) {
        return new ProfileState(distance.add(other.distance()), state.add(other.parameters()), timestamp.add(other.timestamp()));
    }

    public ProfileState sub(ProfileState other) {
        return new ProfileState(distance.sub(other.distance()), state.sub(other.parameters()), timestamp.sub(other.timestamp()));
    }

    public boolean equals(ProfileState other) {
        return distance.equals(other.distance()) &&
                state.equals(other.parameters()) &&
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
