package tracer.controllers;

import tracer.motion.Position;
import tracer.profiles.Profile;
import tracer.trajectories.Trajectory;

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
        double passedDistance = trajectoryProfile.distanceAt(position.timestamp());
        double expected;
        try {
             expected = -Math.toDegrees(trajectory.angleRadAt(passedDistance));
        } catch (IllegalArgumentException ignored) {
            expected = -Math.toDegrees(trajectory.angleRadAt(trajectory.end()));
        }

        return (kP * (expected - position.getAngle()));
    }
}
