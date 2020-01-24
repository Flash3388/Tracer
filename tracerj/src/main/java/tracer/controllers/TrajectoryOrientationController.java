package tracer.controllers;

import com.flash3388.flashlib.math.Mathf;
import com.flash3388.flashlib.robot.motion.Direction;
import com.jmath.ExtendedMath;
import tracer.motion.Position;
import calculus.trajectories.Trajectory;
import tracer.profiles.base.Profile;

import static util.MathUtil.shortestAngularDistance;

public class TrajectoryOrientationController {
    private final double kP;
    private final Trajectory trajectory;
    private final Profile trajectoryProfile;
    private final Direction direction;

    public TrajectoryOrientationController(double kP, Trajectory trajectory, Profile trajectoryProfile, Direction direction) {
        this.kP = kP;
        this.trajectory = trajectory;
        this.trajectoryProfile = trajectoryProfile;
        this.direction = direction;
    }

    public double calculate(Position position) {
        double passedDistance = ExtendedMath.constrain(trajectoryProfile.state(position.timestamp()).distance(), -trajectory.end(), trajectory.end());
        double expected = -Math.toDegrees(trajectory.angleRadAt(passedDistance));
        expected = direction == Direction.FORWARD ? expected : 180 - Mathf.translateInRange(expected, 360, true);

        return (kP * shortestAngularDistance(position.getAngle(), expected));
    }
}
