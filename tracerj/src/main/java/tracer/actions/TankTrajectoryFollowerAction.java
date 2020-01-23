package tracer.actions;

import com.flash3388.flashlib.robot.systems.drive.TankDriveSystem;
import com.flash3388.flashlib.time.Clock;
import com.flash3388.flashlib.time.Time;
import tracer.controllers.TankTrajectoryController;
import tracer.motion.Position;

import java.util.concurrent.TimeUnit;
import java.util.function.DoubleSupplier;

public class TankTrajectoryFollowerAction extends FollowerAction {
    private final TankDriveSystem tankSystem;
    private final TankTrajectoryController controller;
    private final DoubleSupplier rightDistanceSupplier;
    private final DoubleSupplier leftDistanceSupplier;
    private final DoubleSupplier angleSupplier;

    public TankTrajectoryFollowerAction(TankDriveSystem tankSystem, TankTrajectoryController controller, DoubleSupplier rightDistanceSupplier, DoubleSupplier leftDistanceSupplier, DoubleSupplier angleSupplier, Clock clock) {
        super(tankSystem, controller, clock);
        this.tankSystem = tankSystem;
        this.controller = controller;
        this.rightDistanceSupplier = rightDistanceSupplier;
        this.leftDistanceSupplier = leftDistanceSupplier;
        this.angleSupplier = angleSupplier;

        requires(tankSystem);
    }
    @Override
    void setValues(Time relativeTime) {
        System.out.println(relativeTime.valueAsMillis()/1000.0);
        Position rightPos = rightPosition(relativeTime);
        Position leftPos = leftPosition(relativeTime);

        double right = controller.calcForRight(rightPos);
        double left = controller.calcForLeft(leftPos);

        tankSystem.tankDrive(right, left);
    }


    private Position rightPosition(Time relativeTime) {
        return new Position(relativeTime, rightDistanceSupplier.getAsDouble(), angleSupplier.getAsDouble());
    }

    private Position leftPosition(Time relativeTime) {
        return new Position(relativeTime, leftDistanceSupplier.getAsDouble(), angleSupplier.getAsDouble());
    }
}
