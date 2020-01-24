package tracer.motion;

import tracer.units.morion.Acceleration;
import tracer.units.morion.Jerk;
import tracer.units.morion.Velocity;

import java.util.Objects;

public class MotionState {
    private final Velocity velocity;
    private final Acceleration acceleration;
    private final Jerk jerk;

    public MotionState(Velocity velocity, Acceleration acceleration, Jerk jerk) {
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.jerk = jerk;
    }

    public static MotionState stop() {
        return meterUnits(0, 0, 0);
    }

    public static MotionState meterUnits(double velocity, double acceleration, double jerk) {
        return new MotionState(Velocity.metersPerSecond(velocity), Acceleration.metersPerSecondSquared(acceleration), Jerk.metersPerSecondCubed(jerk));
    }

    public static MotionState constantVelocity(Velocity velocity) {
        return new MotionState(velocity, Acceleration.metersPerSecondSquared(0), Jerk.metersPerSecondCubed(0));
    }

    public static MotionState linearVelocity(Velocity velocity, Acceleration acceleration) {
        return new MotionState(velocity, acceleration, Jerk.metersPerSecondCubed(0));
    }

    public static MotionState frcKitOfPartsParameters(Velocity velocity) {
        return new MotionState(velocity, FrcParameters.DEFAULT_ACCELERATION_METERS_PER_SECOND_SQUARED, FrcParameters.DEFAULT_JERK_METERS_PER_SECOND_CUBED);
    }

    public MotionState reverse() {
        return stop().sub(this);
    }

    public Velocity velocity() {
        return velocity;
    }

    public Acceleration acceleration() {
        return acceleration;
    }

    public Jerk jerk() {
        return jerk;
    }

    public MotionState add(MotionState other) {
        return new MotionState(velocity.add(other.velocity()), acceleration.add(other.acceleration()), jerk.add(other.jerk()));
    }

    public MotionState sub(MotionState other) {
        return new MotionState(velocity.sub(other.velocity()), acceleration.sub(other.acceleration()), jerk.sub(other.jerk()));
    }

    public boolean equals(MotionState other) {
        return velocity.equals(other.velocity()) &&
                acceleration.equals(other.acceleration()) &&
                jerk.equals(other.jerk());
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof MotionState && equals((MotionState)o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(velocity, acceleration, jerk);
    }

    @Override
    public String toString() {
        return String.format("velocity: %s, acceleration: %s, jerk: %s", velocity, acceleration, jerk);
    }
}
