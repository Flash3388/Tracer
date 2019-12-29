package tracer.trajectories;

import calculus.splines.SplineType;
import calculus.splines.parameters.Waypoint;
import com.flash3388.flashlib.time.Time;

import java.util.List;
import java.util.stream.Collectors;

public class TankTrajectory {
    private final Trajectory left;
    private final Trajectory right;

    public TankTrajectory(SplineType splineType, List<Waypoint> centerPath, double distanceBetweenWheelsMeters, double maxVelocityMetersPerSecond, Time maxCycleTime) {
        left = new Trajectory(splineType, maxVelocityMetersPerSecond, maxCycleTime, shiftPath(centerPath, distanceBetweenWheelsMeters/2));
        right = new Trajectory(splineType, maxVelocityMetersPerSecond, maxCycleTime, shiftPath(centerPath, -distanceBetweenWheelsMeters/2));
    }

    public Trajectory left() {
        return left;
    }

    public Trajectory right() {
        return right;
    }

    private List<Waypoint> shiftPath(List<Waypoint> centerPath, double offset) {
        return centerPath.stream()
                .map(waypoint -> shift(waypoint, offset))
                .collect(Collectors.toList());
    }

    private Waypoint shift(Waypoint waypoint, double offset) {
        return new Waypoint(waypoint.x() - offset * Math.sin(waypoint.heading()), waypoint.y() + offset * Math.cos(waypoint.heading()), waypoint.heading());
    }
}
