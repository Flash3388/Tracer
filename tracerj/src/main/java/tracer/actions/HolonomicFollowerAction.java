package tracer.actions;

import com.flash3388.flashlib.time.Clock;
import com.flash3388.flashlib.time.Time;
import com.jmath.vectors.Vector2;
import tracer.controllers.TrajectoryController;
import tracer.following.HolonomicFollower;
import tracer.motion.Position;

public class HolonomicFollowerAction extends FollowerAction{
    private final HolonomicFollower follower;
    private final TrajectoryController controller;

    HolonomicFollowerAction(HolonomicFollower follower, TrajectoryController trajectoryController, Clock clock) {
        super(follower, trajectoryController, clock);
        this.follower = follower;
        this.controller = trajectoryController;
    }

    @Override
    protected void setValues(Time relativeTime) {
        Position position = position(relativeTime)
        double angle = controller.expectedAngleAt(position);
        double value = controller.calculate(position);
        Vector2 valueVector = Vector2.polar(value, angle);

        follower.holonomicDrive(valueVector, 0);
    }

    Position position(Time relativeTime) {
        return new Position(relativeTime, follower.passedDistance(), 0);
    }
}
