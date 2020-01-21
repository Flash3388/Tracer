package tracer.controllers;

import com.flash3388.flashlib.math.Mathf;
import com.jmath.ExtendedMath;
import tracer.motion.Position;
import tracer.profiles.Profile;
import calculus.trajectories.Trajectory;

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
        double passedDistance = ExtendedMath.constrain(trajectoryProfile.distanceAt(position.timestamp()), -trajectory.end(), trajectory.end());
        double expected = -Math.toDegrees(trajectory.angleRadAt(passedDistance));
        expected = isForward ? expected : 180 - Mathf.translateInRange(expected, 360, true);

        return (kP * shortestAngularDistance(position.getAngle(), expected));
    }

    private double shortestAngularDistance(double from, double to) {
        double result = Mathf.shortestAngularDistance(from, to);
        to = Mathf.translateInRange(to, 360, true);
        from = Mathf.translateInRange(from, 360, true);

        if(to > from)
            return result;
        else
            return -result;
    }
}
