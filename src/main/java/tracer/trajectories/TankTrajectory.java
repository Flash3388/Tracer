package tracer.trajectories;

import calculus.splines.SplineType;
import tracer.motion.Waypoint;
import tracer.motion.basic.Distance;

import java.util.List;
import java.util.stream.Collectors;

public class TankTrajectory {
    private final Trajectory left;
    private final Trajectory right;

    public TankTrajectory(SplineType splineType, List<Waypoint> centerPath, Distance distanceBetweenWheelsCentimeters) {
        left = new Trajectory(splineType, shiftPath(centerPath, distanceBetweenWheelsCentimeters.scaleValue(-0.5)));
        right = new Trajectory(splineType, shiftPath(centerPath, distanceBetweenWheelsCentimeters.scaleValue(0.5)));
    }

    public Trajectory left() {
        return left;
    }

    public Trajectory right() {
        return right;
    }

    private List<Waypoint> shiftPath(List<Waypoint> centerPath, Distance xOffset) {
        return centerPath.stream()
                .map(waypoint -> new Waypoint(waypoint.x().add(xOffset), waypoint.y(), waypoint.heading()))
                .collect(Collectors.toList());
    }
}
