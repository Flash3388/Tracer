package com.flash3388.tracer.controllers.factories;

import com.flash3388.tracer.calculus.trajectories.Trajectory;
import com.flash3388.tracer.controllers.TrajectoryOrientationController;
import com.flash3388.tracer.profiles.base.Profile;
import com.flash3388.flashlib.control.Direction;

public class TrajectoryOrientationControllerFactory {
    private final double gP;

    public TrajectoryOrientationControllerFactory(double gP) {
        this.gP = gP;
    }

    public TrajectoryOrientationController create(Trajectory trajectory, Profile trajectoryProfile, Direction direction) {
        return new TrajectoryOrientationController(gP, trajectory, trajectoryProfile, direction);
    }
}
