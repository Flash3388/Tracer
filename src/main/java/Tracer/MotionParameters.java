package Tracer;

import com.jmath.ExtendedMath;

public class MotionParameters {
    private final static double DEF_MARGIN = 1E-4;

    private final double
            velocity,
            acceleration,
            jerk;

    public MotionParameters(double velocity, double acceleration, double jerk) {
        this.velocity = velocity;

        this.acceleration = acceleration;
        this.jerk = jerk;
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

    public boolean almostEquals(MotionParameters motionParameters) {
        return almostEquals(motionParameters, DEF_MARGIN);
    }

    private boolean almostEquals(MotionParameters motionParameters, double margin) {
        return ExtendedMath.equals(velocity, motionParameters.getVelocity(), margin) &&
                ExtendedMath.equals(acceleration, motionParameters.getAcceleration(), margin) &&
                ExtendedMath.equals(jerk, motionParameters.getJerk(), margin);
    }
}
