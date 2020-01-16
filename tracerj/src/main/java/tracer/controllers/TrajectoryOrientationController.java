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

    public TrajectoryOrientationController(double kP, Trajectory trajectory, Profile trajectoryProfile) {
        this.kP = kP;
        this.trajectory = trajectory;
        this.trajectoryProfile = trajectoryProfile;
    }

    public double calculate(Position position) {
        double passedDistance = ExtendedMath.constrain(trajectoryProfile.distanceAt(position.timestamp()), -trajectory.end(), trajectory.end());
        double expected = -Math.toDegrees(trajectory.angleRadAt(passedDistance));

        return (kP * shortestAngularDistance(position.getAngle(), expected));
    }

    private double shortestAngularDistance(double from, double to) {
        double result = Mathf.shortestAngularDistance(from, to);
        to = Mathf.translateInRange(to, 180, true);
        from = Mathf.translateInRange(from, 180, true);

        if(to > from)
            return result;
        else
            return -result;
    }
}
