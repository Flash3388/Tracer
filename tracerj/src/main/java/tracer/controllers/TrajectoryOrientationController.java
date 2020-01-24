package tracer.controllers;

import com.flash3388.flashlib.math.Mathf;
import com.jmath.ExtendedMath;
import tracer.motion.Position;
import calculus.trajectories.Trajectory;
import tracer.profiles.base.Profile;

import static util.MathUtil.shortestAngularDistance;

public class TrajectoryOrientationController {
    private final double kP;
    private final Trajectory trajectory;
    private final Profile trajectoryProfile;
    private final boolean isForward;

    public TrajectoryOrientationController(double kP, Trajectory trajectory, Profile trajectoryProfile, boolean isForward) {
        this.kP = kP;
        this.trajectory = trajectory;
        this.trajectoryProfile = trajectoryProfile;
        this.isForward = isForward;
    }

    public double calculate(Position position) {
        double passedDistance = ExtendedMath.constrain(trajectoryProfile.state(position.timestamp()).distance().valueAsMeters(), -trajectory.end(), trajectory.end());
        double expected = -Math.toDegrees(trajectory.angleRadAt(passedDistance));
        expected = isForward ? expected : 180 - Mathf.translateInRange(expected, 360, true);

        return (kP * shortestAngularDistance(position.getAngle().valueAsDegrees(), expected));
    }
}
