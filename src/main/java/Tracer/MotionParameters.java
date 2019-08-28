package Tracer;

import com.jmath.ExtendedMath;

public class MotionParameters extends Parameters {
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

    public boolean almostEquals(Parameters parameters) {
        return almostEquals(parameters, DEF_MARGIN);
    }

    @Override
    public boolean almostEquals(Parameters parameters, double margin) {
        return almostEquals((MotionParameters) parameters, margin);
    }

    private boolean almostEquals(MotionParameters motionParameters, double margin) {
        return ExtendedMath.equals(velocity, motionParameters.getVelocity(), margin) &&
                ExtendedMath.equals(acceleration, motionParameters.getAcceleration(), margin) &&
                ExtendedMath.equals(jerk, motionParameters.getJerk(), margin);
    }
}
