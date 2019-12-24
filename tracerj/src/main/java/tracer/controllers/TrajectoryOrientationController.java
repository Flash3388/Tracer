package tracer.controllers;

import com.flash3388.flashlib.time.Time;
import com.jmath.ExtendedMath;
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
        double pOut = kP * getAngleError(position.timestamp(), position.getAngle());
        return ExtendedMath.constrain(pOut, -1, 1);
    }

    private double getAngleError(Time currentTime, double currentAngle) {
        return Math.toDegrees(trajectory.angleRadAt(trajectoryProfile.distanceAt(currentTime))) - currentAngle;
    }
}
