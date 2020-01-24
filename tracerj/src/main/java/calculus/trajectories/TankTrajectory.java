package calculus.trajectories;

import calculus.splines.SplineType;
import calculus.splines.parameters.Waypoint;
import tracer.units.distance.Distance;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TankTrajectory {
    private final Trajectory left;
    private final Trajectory right;

    public TankTrajectory(SplineType splineType, Distance distanceBetweenWheels, Waypoint... centerPath) {
        this(splineType, distanceBetweenWheels, Arrays.asList(centerPath));
    }

    public TankTrajectory(SplineType splineType, Distance distanceBetweenWheels, List<Waypoint> centerPath) {
        left = new Trajectory(splineType, shiftPath(centerPath, distanceBetweenWheels.valueAsMeters()/2));
        right = new Trajectory(splineType, shiftPath(centerPath, -distanceBetweenWheels.valueAsMeters()/2));
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
        return waypoint.shiftX(Distance.meters(-offset * Math.sin(waypoint.heading().valueAsRadians()))).shiftY(Distance.meters(offset * Math.cos(waypoint.heading().valueAsRadians())));
    }

    @Override
    public String toString() {
        return String.format("{ right: %s , left: %s", right, left);
    }
}
