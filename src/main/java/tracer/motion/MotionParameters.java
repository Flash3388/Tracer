package tracer.motion;

import tracer.motion.basic.Acceleration;
import tracer.motion.basic.Jerk;
import tracer.motion.basic.Velocity;

public class MotionParameters {
    private final Velocity velocity;
    private final Acceleration acceleration;
    private final Jerk jerk;

    public static MotionParameters stop() {
        return constantVelocity(Velocity.centimetersPerSecond(0));
    }

    public static MotionParameters constantVelocity(Velocity velocity) {
        return linearVelocity(velocity, Acceleration.centimetersPerSecondPerSecond(0));
    }

    public static MotionParameters linearVelocity(Velocity velocity, Acceleration acceleration) {
        return new MotionParameters(velocity, acceleration, Jerk.centimetersPerSecondPerSecondPerSecond(0));
    }

    public MotionParameters(Velocity velocity, Acceleration acceleration, Jerk jerk) {
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.jerk = jerk;
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

    @Override
    public String toString() {
        return "velocity: "+velocity+" acceleration: " + acceleration + " jerk: " + jerk;
    }
}
