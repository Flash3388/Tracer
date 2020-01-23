package tracer.motion;

public class MotionState {
    private final double velocity;
    private final double acceleration;
    private final double jerk;

    public MotionState(double velocityMetersPerSecond, double accelerationMetersPerSecondSquared, double jerkMetersPerSecondCubed) {
        this.velocity = velocityMetersPerSecond;
        this.acceleration = accelerationMetersPerSecondSquared;
        this.jerk = jerkMetersPerSecondCubed;
    }

    public static MotionState stop() {
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

    @Override
    public String toString() {
        return String.format("velocity: %f, acceleration: %f, jerk: %f", velocity, acceleration, jerk);
    }
}
