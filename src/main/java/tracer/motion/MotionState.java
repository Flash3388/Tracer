package tracer.motion;

import com.jmath.ExtendedMath;

import java.util.Objects;

public class MotionState {
    private final static double DEFAULT_DELTA = 0.001;

    private final double velocity;
    private final double acceleration;
    private final double jerk;

    public MotionState(double velocityMetersPerSecond, double accelerationMetersPerSecondSquared, double jerkMetersPerSecondCubed) {
        this.velocity = velocityMetersPerSecond;
        this.acceleration = accelerationMetersPerSecondSquared;
        this.jerk = jerkMetersPerSecondCubed;
    }

    public static MotionState none() {
        return new MotionState(0, 0, 0);
    }

    public static MotionState constantVelocity(double velocity) {
        return new MotionState(velocity, 0, 0);
    }

    public static MotionState linearVelocity(double velocity, double acceleration) {
        return new MotionState(velocity, acceleration, 0);
    }

    public static MotionState frcKitOfPartsParameters(double velocityMetersPerSecond) {
        return new MotionState(velocityMetersPerSecond, FrcParameters.DEFAULT_ACCELERATION_METERS_PER_SECOND_SQUARED, FrcParameters.DEFAULT_JERK_METERS_PER_SECOND_CUBED);
    }

    public double velocity() {
        return velocity;
    }

    public double acceleration() {
        return acceleration;
    }

    public double jerk() {
        return jerk;
    }

    public MotionState add(MotionState other) {
        return new MotionState(velocity+other.velocity(), acceleration+other.acceleration(), jerk+other.jerk());
    }

    public MotionState sub(MotionState other) {
        return new MotionState(velocity-other.velocity(), acceleration-other.acceleration(), jerk-other.jerk());
    }

    public MotionState mul(double val) {
        return new MotionState(velocity * val, acceleration * val, jerk * val);
    }

    public boolean equals(MotionState other) {
        return ExtendedMath.equals(velocity, other.velocity(), DEFAULT_DELTA) &&
                ExtendedMath.equals(acceleration, other.acceleration(), DEFAULT_DELTA) &&
                ExtendedMath.equals(jerk, other.jerk(), DEFAULT_DELTA);
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
        return String.format("velocity: %f, acceleration: %f, jerk: %f", velocity, acceleration, jerk);
    }
}
