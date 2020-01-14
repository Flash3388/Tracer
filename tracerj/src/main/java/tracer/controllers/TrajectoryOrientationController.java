package tracer.controllers;

import com.flash3388.flashlib.math.Mathf;
import com.jmath.ExtendedMath;
import tracer.motion.Position;
import tracer.profiles.Profile;
import calculus.trajectories.Trajectory;

public class TrajectoryOrientationController {
    private final double kP;

    private Trajectory trajectory;
    private Profile trajectoryProfile;

    public TrajectoryOrientationController(double kP) {
        this.kP = kP;
        trajectory = null;
        trajectoryProfile = null;
    }

    public void setTarget(Trajectory trajectory, Profile trajectoryProfile) {
        this.trajectory = trajectory;
        this.trajectoryProfile = trajectoryProfile;
    }

    public double calculate(Position position) {
        checkTarget();

        double passedDistance = ExtendedMath.constrain(trajectoryProfile.distanceAt(position.timestamp()), -trajectory.end(), trajectory.end());
        double expected = -Math.toDegrees(trajectory.angleRadAt(passedDistance));

        return (kP * (Mathf.shortestAngularDistance(position.getAngle(), expected)));
    }

    private void checkTarget() {
        if(trajectory == null || trajectoryProfile == null)
            throw new NoTargetException();
    }
}
