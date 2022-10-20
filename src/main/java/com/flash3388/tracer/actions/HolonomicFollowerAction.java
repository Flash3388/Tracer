package com.flash3388.tracer.actions;

import com.flash3388.flashlib.time.Time;
import com.flash3388.tracer.motion.Position;
import com.jmath.vectors.Vector2;
import com.flash3388.tracer.controllers.TrajectoryController;
import com.flash3388.tracer.following.HolonomicFollower;

public class HolonomicFollowerAction extends FollowerAction{
    private final HolonomicFollower follower;
    private final TrajectoryController controller;

    HolonomicFollowerAction(HolonomicFollower follower, TrajectoryController trajectoryController) {
        super(follower, trajectoryController);
        this.follower = follower;
        this.controller = trajectoryController;
    }

    @Override
    protected void setValues(Time relativeTime) {
        Position position = position(relativeTime);
        double angle = controller.expectedAngleAt(position);
        double value = controller.calculate(position);
        Vector2 valueVector = Vector2.polar(value, angle);

        follower.holonomicDrive(valueVector, 0);
    }

    Position position(Time relativeTime) {
        return new Position(relativeTime, follower.passedDistance(), 0);
    }
}
