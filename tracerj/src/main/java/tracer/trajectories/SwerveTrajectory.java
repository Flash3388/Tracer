package tracer.trajectories;

import calculus.splines.SplineType;
import calculus.splines.parameters.Waypoint;

import java.util.List;
import java.util.stream.Collectors;

public class SwerveTrajectory {
    private final Trajectory rearLeft;
    private final Trajectory rearRight;
    private final Trajectory frontLeft;
    private final Trajectory frontRight;

    public SwerveTrajectory(SplineType splineType, List<Waypoint> centerPath, double widthDistanceMeters, double lengthDistanceMeters) {
        rearLeft = new Trajectory(splineType, shiftPath(centerPath, widthDistanceMeters/2, -lengthDistanceMeters/2));
        rearRight = new Trajectory(splineType, shiftPath(centerPath, -widthDistanceMeters/2, -lengthDistanceMeters/2));;
        frontLeft = new Trajectory(splineType, shiftPath(centerPath, widthDistanceMeters/2, lengthDistanceMeters/2));;
        frontRight = new Trajectory(splineType, shiftPath(centerPath, -widthDistanceMeters/2, lengthDistanceMeters/2));;
    }

    public Trajectory rearLeft() {
        return rearLeft;
    }

    public Trajectory rearRight() {
        return rearRight;
    }

    public Trajectory frontLeft() {
        return frontLeft;
    }

    public Trajectory frontRight() {
        return frontRight;
    }

    private List<Waypoint> shiftPath(List<Waypoint> centerPath, double xOffset, double yOffset) {
        return centerPath.stream()
                .map(waypoint -> shiftForX(shiftForY(waypoint, yOffset), xOffset))
                .collect(Collectors.toList());
    }

    private Waypoint shiftForY(Waypoint waypoint, double offset) {
        return new Waypoint(waypoint.x() - offset * Math.cos(waypoint.heading()), waypoint.y() + offset * Math.sin(waypoint.heading()), waypoint.heading());
    }

    private Waypoint shiftForX(Waypoint waypoint, double offset) {
        return new Waypoint(waypoint.x() + offset * Math.cos(waypoint.heading()), waypoint.y() + offset * Math.sin(waypoint.heading()), waypoint.heading());
    }
}
