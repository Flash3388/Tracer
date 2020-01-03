package tracer.actions;

import com.flash3388.flashlib.time.Clock;
import com.flash3388.flashlib.time.Time;
import tracer.controllers.TankTrajectoryController;
import tracer.following.TankFollower;
import tracer.motion.Position;

public class TankTrajectoryFollowerAction extends FollowerAction {
    private final TankFollower tankFollower;
    private final TankTrajectoryController controller;

    public TankTrajectoryFollowerAction(TankFollower tankFollower, TankTrajectoryController controller, Clock clock) {
        super(tankFollower, controller, clock);
        this.tankFollower = tankFollower;
        this.controller = controller;
    }

    @Override
    void setValues(Time relativeTime) {
        Position rightPos = rightPosition(relativeTime);
        Position leftPos = leftPosition(relativeTime);

        double right = controller.calcForRight(rightPos);
        double left = controller.calcForLeft(leftPos);

        tankFollower.tankDrive(right, left);
    }


    private Position rightPosition(Time relativeTime) {
        return new Position(relativeTime, tankFollower.passedDistanceRightM(), tankFollower.angle());
    }

    private Position leftPosition(Time relativeTime) {
        return new Position(relativeTime, tankFollower.passedDistanceLeftM(), tankFollower.angle());
    }
}
