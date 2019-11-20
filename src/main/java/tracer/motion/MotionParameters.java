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

    public static MotionParameters stop() {
        return new MotionParameters(0, 0, 0);
    }

    public MotionParameters(double velocityCentimetersPerSecond, double accelerationCentimetersPerSecondPerSecond, double jerkCentimetersPerSecondPerSecondPerSecond) {
        this.velocity = velocityCentimetersPerSecond;
        this.acceleration = accelerationCentimetersPerSecondPerSecond;
        this.jerk = jerkCentimetersPerSecondPerSecondPerSecond;
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
