package tracer.trajectories;

import calculus.splines.SplineType;
import calculus.splines.parameters.Waypoint;

import java.util.List;
import java.util.stream.Collectors;

public class TankTrajectory {
    private final Trajectory left;
    private final Trajectory right;

    public TankTrajectory(SplineType splineType, List<Waypoint> centerPath, double distanceBetweenWheelsMeters) {
        left = new Trajectory(splineType, shiftPath(centerPath, -distanceBetweenWheelsMeters/2));
        right = new Trajectory(splineType, shiftPath(centerPath, distanceBetweenWheelsMeters/2));
    }

    public Trajectory left() {
        return left;
    }

    public Trajectory right() {
        return right;
    }

    private List<Waypoint> shiftPath(List<Waypoint> centerPath, double offset) {
        return centerPath.stream()
                .map(waypoint -> new Waypoint(waypoint.x()+offset, waypoint.y(), waypoint.heading()))
                .collect(Collectors.toList());
    }
}
