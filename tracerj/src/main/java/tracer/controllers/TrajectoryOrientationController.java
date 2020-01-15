package tracer.controllers;

import com.flash3388.flashlib.math.Mathf;
import com.jmath.ExtendedMath;
import tracer.motion.Position;
import tracer.profiles.Profile;
import calculus.trajectories.Trajectory;

public class TrajectoryOrientationController {
    private final Trajectory trajectory;
    private final Profile trajectoryProfile;
    private final double kP;

    public TrajectoryOrientationController(Trajectory trajectory, Profile trajectoryProfile, double kP) {
        this.trajectory = trajectory;
        this.trajectoryProfile = trajectoryProfile;
        this.kP = kP;
    }

    public double calculate(Position position) {
        double passedDistance = ExtendedMath.constrain(trajectoryProfile.distanceAt(position.timestamp()), -trajectory.end(), trajectory.end());
        double expected = -Math.toDegrees(trajectory.angleRadAt(passedDistance));

        return (kP * (Mathf.shortestAngularDistance(position.getAngle(), expected)));
    }
}
