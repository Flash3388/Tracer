package tracer.trajectories;

import calculus.splines.SplineType;
import tracer.motion.Waypoint;

import java.util.List;
import java.util.stream.Collectors;

public class TankTrajectory {
    private final Trajectory left;
    private final Trajectory right;

    public TankTrajectory(SplineType splineType, List<Waypoint> centerPath, double distanceBetweenWheelsCentimeters) {
        left = new Trajectory(splineType, adjustPath(centerPath, -distanceBetweenWheelsCentimeters/2));
        right = new Trajectory(splineType, adjustPath(centerPath, distanceBetweenWheelsCentimeters/2));
    }

    public Trajectory left() {
        return left;
    }

    public Trajectory right() {
        return right;
    }

    private List<Waypoint> adjustPath(List<Waypoint> centerPath, double offset) {
        return centerPath.stream()
                .map(waypoint -> Waypoint.centimetersRadians(waypoint.x()+offset, waypoint.y()+offset, waypoint.getHeading()))
                .collect(Collectors.toList());
    }
}
