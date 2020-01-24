package tracer.actions;

import com.flash3388.flashlib.robot.systems.drive.TankDriveSystem;
import com.flash3388.flashlib.time.Clock;
import com.flash3388.flashlib.time.Time;
import tracer.controllers.TankTrajectoryController;
import tracer.motion.Position;
import tracer.units.distance.Distance;

import java.util.concurrent.TimeUnit;
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public class TankTrajectoryFollowerAction extends FollowerAction {
    private final TankDriveSystem tankSystem;
    private final TankTrajectoryController controller;
    private final Supplier<Distance> rightDistanceSupplier;
    private final Supplier<Distance> leftDistanceSupplier;
    private final DoubleSupplier angleSupplier;

    public TankTrajectoryFollowerAction(TankDriveSystem tankSystem, TankTrajectoryController controller, Supplier<Distance> rightDistanceSupplier, Supplier<Distance> leftDistanceSupplier, DoubleSupplier angleSupplier, Clock clock) {
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
        return new Position(relativeTime, rightDistanceSupplier.get(), angleSupplier.getAsDouble());
    }

    private Position leftPosition(Time relativeTime) {
        return new Position(relativeTime, leftDistanceSupplier.get(), angleSupplier.getAsDouble());
    }
}
