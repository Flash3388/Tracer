package simulation.physics.compositions;

import com.jmath.vectors.Vector2;
import simulation.physics.motors.DriveSide;

public class TankCompositions implements Composition {
    private final DriveSide right;
    private final DriveSide left;
    private final PhysicalCharacteristics physicalCharacteristics;
    private final DriveCompositionCharacteristics driveCharacteristics;
    private final double gravityConstant;

    public TankCompositions(DriveSide right, DriveSide left, PhysicalCharacteristics physicalCharacteristics, DriveCompositionCharacteristics driveCharacteristics, final double gravityConstant) {
        this.right = right;
        this.left = left;
        this.physicalCharacteristics = physicalCharacteristics;
        this.driveCharacteristics = driveCharacteristics;
        this.gravityConstant = gravityConstant;
    }

    @Override
    public void start() {
        right.start();
        left.start();
    }

    @Override
    public Vector2 accelerationAt(Vector2 velocity) {
        Vector2 Ft = tractionForce(velocity);
        Vector2 Fd = dragResistanceForce(velocity);
        Vector2 Fr = rollingResistanceForce(velocity);
        Vector2 Fg = gravityForce();

        return Ft.add(Fd).add(Fr).add(Fg).div(physicalCharacteristics.mass());
    }

    @Override
    public boolean isDone() {
        return right.isDone() && left.isDone();
    }

    private Vector2 tractionForce(Vector2 velocity) {
        double rightTorque = right.torqueAt((velocity.y() - velocity.x()) / physicalCharacteristics.wheelRadius());
        double leftTorque = left.torqueAt((velocity.y() + velocity.x()) / physicalCharacteristics.wheelRadius());

        double rightForce = rightTorque / physicalCharacteristics.wheelRadius();
        double leftForce = leftTorque / physicalCharacteristics.wheelRadius();

        double yComponent = (rightForce + leftForce)/2;
        double xComponent = leftForce - yComponent;

        return new Vector2(xComponent, yComponent);
    }

    private Vector2 dragResistanceForce(Vector2 velocity) {
        return velocity.multiply(-driveCharacteristics.drag());
    }

    private Vector2 rollingResistanceForce(Vector2 velocity) {
        return velocity.multiply(-driveCharacteristics.rr());
    }

    private Vector2 gravityForce() {
        return Vector2.polar(-physicalCharacteristics.mass() * gravityConstant * Math.sin(Math.toRadians(driveCharacteristics.slopeAngle())), 0);
    }
}