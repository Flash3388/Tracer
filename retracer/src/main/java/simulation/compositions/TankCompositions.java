package simulation.compositions;

import simulation.motors.Motor;
import tracer.motion.MotionParameters;
import tracer.motion.Position;

public class TankCompositions implements Composition {
    private final Motor frontRight;
    private final Motor frontLeft;
    private final Motor rearRight;
    private final Motor rearLeft;

    public TankCompositions(Motor frontRight, Motor frontLeft, Motor rearRight, Motor rearLeft, final double surfaceFriction) {
        this.frontRight = frontRight;
        this.frontLeft = frontLeft;
        this.rearRight = rearRight;
        this.rearLeft = rearLeft;
    }

    @Override
    public void simulate() {
        frontRight.start();
        frontLeft.start();
        rearRight.start();
        rearLeft.start();
    }

    public Position position() {
        return Position.start();
    }

    public MotionParameters motionParameters() {
        return MotionParameters.stop();
    }
}
