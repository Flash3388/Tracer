package tracer.motion;

public class MotionParameters {
    private final double
            velocity,
            acceleration,
            jerk;

    public MotionParameters(double velocityCentimeters, double accelerationCentimeters, double jerkCentimeters) {
        this.velocity = velocityCentimeters;

        this.acceleration = accelerationCentimeters;
        this.jerk = jerkCentimeters;
    }

    public static MotionParameters constantVelocity(double velocity) {
        return new MotionParameters(velocity, 0, 0);
    }

    public static MotionParameters linearVelocity(double velocity, double acceleration) {
        return new MotionParameters(velocity, acceleration, 0);
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
        return "velocity: "+velocity+" acceleration: " + acceleration + " jerk: " + jerk;
    }
}
