package tracer.controllers;

import calculus.trajectories.Trajectory;
import com.flash3388.flashlib.control.Direction;
import com.flash3388.flashlib.time.Time;
import com.jmath.ExtendedMath;
import tracer.motion.Position;
import tracer.profiles.base.Profile;

import static util.MathUtil.shortestAngularDistance;

public class TrajectoryOrientationController {
    private final double kP;
    private final Trajectory trajectory;
    private final Profile trajectoryProfile;
    private final Direction direction;
    private double lastPassedDistance;
    private double lastExpectedAngle;

    public TrajectoryOrientationController(double kP, Trajectory trajectory, Profile trajectoryProfile, Direction direction) {
        this.kP = kP;
        this.trajectory = trajectory;
        this.trajectoryProfile = trajectoryProfile;
        this.direction = direction;

        lastPassedDistance = 0;
        lastExpectedAngle = 0;
    }

    public double calculate(Position position, boolean isRight) {
        double passedDistance = ExtendedMath.constrain(trajectoryProfile.state(position.timestamp()).distance(), -trajectory.end(), trajectory.end());
        double expected = expectedAngleAt(passedDistance);

        return ((isRight ? -kP : kP) * shortestAngularDistance(position.getAngle(), expected));
    }

    public double expectedAngleAt(double passedDistance) {
        double expected;

        if(ExtendedMath.equals(passedDistance, lastPassedDistance, 0.01))
            expected = lastExpectedAngle;
        else {
            expected = Math.toDegrees(trajectory.angleRadAt(passedDistance));
            expected = direction.equals(Direction.FORWARD) ? -expected : 180 - expected;
            lastPassedDistance = passedDistance;
            lastExpectedAngle = expected;
        }

        return expected;
    }

    public Time duration() {
        return trajectoryProfile.deltaState().timestamp();
    }
}
