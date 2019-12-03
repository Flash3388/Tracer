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

    public TankCompositions(Motor frontRight, Motor frontLeft, Motor rearRight, Motor rearLeft, PhysicalCharacteristics physicalCharacteristics, DriveCompositionCharacteristics driveCharacteristics) {
        this.frontRight = frontRight;
        this.frontLeft = frontLeft;
        this.rearRight = rearRight;
        this.rearLeft = rearLeft;
        this.physicalCharacteristics = physicalCharacteristics;
        this.driveCharacteristics = driveCharacteristics;
    }

    @Override
    public void simulate() {
        frontRight.start();
        frontLeft.start();
        rearRight.start();
        rearLeft.start();
    }

    @Override
    public Vector2 force(double velocity) {
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
        return new Vector2();
    }

    private Vector2 rollingResistanceForce(double velocity) {
        return new Vector2();
    }

    private Vector2 gravityForce() {
        return new Vector2();
    }
}
