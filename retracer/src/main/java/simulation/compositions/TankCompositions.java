package simulation.compositions;

import com.jmath.vectors.Vector2;
import simulation.motors.Motor;

public class TankCompositions implements Composition {
    private final Motor frontRight;
    private final Motor frontLeft;
    private final Motor rearRight;
    private final Motor rearLeft;
    private final PhysicalCharacteristics physicalCharacteristics;
    private final DriveCompositionCharacteristics driveCharacteristics;
    private final double gravityConstant;

    public TankCompositions(Motor frontRight, Motor frontLeft, Motor rearRight, Motor rearLeft, PhysicalCharacteristics physicalCharacteristics, DriveCompositionCharacteristics driveCharacteristics, final double gravityConstant) {
        this.frontRight = frontRight;
        this.frontLeft = frontLeft;
        this.rearRight = rearRight;
        this.rearLeft = rearLeft;
        this.physicalCharacteristics = physicalCharacteristics;
        this.driveCharacteristics = driveCharacteristics;
        this.gravityConstant = gravityConstant;
    }

    @Override
    public void simulate() {
        frontRight.start();
        frontLeft.start();
        rearRight.start();
        rearLeft.start();
    }

    @Override
    public Vector2 accelerationAt(double velocity) {
        Vector2 Ft = tractionForce();
        Vector2 Fd = dragResistanceForce(velocity);
        Vector2 Fr = rollingResistanceForce(velocity);
        Vector2 Fg = gravityForce();

        return Ft.add(Fd).add(Fr).add(Fg);
    }

    private Vector2 tractionForce() {
        return new Vector2();
    }

    private Vector2 dragResistanceForce(double velocity) {
        return Vector2.polar(-driveCharacteristics.drag() * velocity, 0);
    }

    private Vector2 rollingResistanceForce(double velocity) {
        return Vector2.polar(-driveCharacteristics.rr() * velocity, 0);
    }

    private Vector2 gravityForce() {
        return Vector2.polar(-physicalCharacteristics.mass() * gravityConstant * Math.sin(Math.toRadians(driveCharacteristics.slopeAngle())), 0);
    }

    private Vector2 tankMeanVector(double left, double right) {
        return new Vector2();
    }
}
