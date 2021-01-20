package tracer.controllers.factories;

import calculus.trajectories.Trajectory;
import com.flash3388.flashlib.robot.motion.Direction;
import tracer.controllers.TrajectoryOrientationController;
import tracer.profiles.base.Profile;

public class TrajectoryOrientationControllerFactory {
    private final double gP;

    public TrajectoryOrientationControllerFactory(double gP) {
        this.gP = gP;
    }

    public TrajectoryOrientationController create(Trajectory trajectory, Profile trajectoryProfile, Direction direction) {
        return new TrajectoryOrientationController(gP, trajectory, trajectoryProfile, direction);
    }
}
