package tracer.controllers.factories;

import calculus.trajectories.Trajectory;
import tracer.controllers.TrajectoryOrientationController;
import tracer.profiles.Profile;

public class TrajectoryOrientationControllerFactory {
    private final double gP;

    public TrajectoryOrientationControllerFactory(double gP) {
        this.gP = gP;
    }

    public TrajectoryOrientationController create(Trajectory trajectory, Profile trajectoryProfile, boolean isForward) {
        return new TrajectoryOrientationController(gP, trajectory, trajectoryProfile, isForward);
    }
}
