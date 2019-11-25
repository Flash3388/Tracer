package tracer.motion;

public class MotionParameters {
    private final double velocity;
    private final double acceleration;
    private final double jerk;

    public static MotionParameters constantVelocity(double velocity) {
        return new MotionParameters(velocity, 0, 0);
    }

    public static MotionParameters linearVelocity(double velocity, double acceleration) {
        return new MotionParameters(velocity, acceleration, 0);
    }

    public MotionParameters(double velocityMetersPerSecond, double accelerationMetersPerSecondPerSecond, double jerkMetersPerSecondPerSecondPerSecond) {
        this.velocity = velocityMetersPerSecond;
        this.acceleration = accelerationMetersPerSecondPerSecond;
        this.jerk = jerkMetersPerSecondPerSecondPerSecond;
    }

    public static MotionParameters stop() {
        return new MotionParameters(0, 0, 0);
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

    @Override
    public String toString() {
        return String.format("velocity: %f, acceleration: %f, jerk: %f", velocity, acceleration, jerk);
    }
}
