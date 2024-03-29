package tracer.actions;

import com.flash3388.flashlib.time.Time;
import tracer.controllers.TankTrajectoryController;
import tracer.following.TankFollower;
import tracer.motion.Position;

public class TankFollowerAction extends FollowerAction {
    private final TankFollower tankFollower;
    private final TankTrajectoryController controller;

    public TankFollowerAction(TankFollower tankFollower, TankTrajectoryController controller) {
        super(tankFollower, controller);
        this.tankFollower = tankFollower;
        this.controller = controller;
    }

    @Override
    protected void setValues(Time relativeTime) {
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