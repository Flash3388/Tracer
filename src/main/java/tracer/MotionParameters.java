package tracer;

public class MotionParameters {
    private final static double DEF_MARGIN = 1E-4;

    private final double
            velocity,
            acceleration,
            jerk;

    public MotionParameters(double velocityCentimeters, double accelerationCentimeters, double jerkCentimeters) {
        this.velocity = velocityCentimeters;

        this.acceleration = accelerationCentimeters;
        this.jerk = jerkCentimeters;
    }

    public double getVelocity() {
        return velocity;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public double getJerk() {
        return jerk;
    }

    @Override
    public String toString() {
        return "velocity: "+velocity+" acceleration: " + acceleration + " jerk: " + jerk;
    }
}
