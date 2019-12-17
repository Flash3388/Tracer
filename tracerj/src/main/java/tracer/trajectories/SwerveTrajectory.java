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
        rearLeft = new Trajectory(splineType, shiftPath(centerPath, -widthDistanceMeters/2, -lengthDistanceMeters/2));
        rearRight = new Trajectory(splineType, shiftPath(centerPath, widthDistanceMeters/2, -lengthDistanceMeters/2));;
        frontLeft = new Trajectory(splineType, shiftPath(centerPath, -widthDistanceMeters/2, lengthDistanceMeters/2));;
        frontRight = new Trajectory(splineType, shiftPath(centerPath, widthDistanceMeters/2, lengthDistanceMeters/2));;
    }

    private List<Waypoint> shiftPath(List<Waypoint> centerPath, double xOffset, double yOffset) {
        return centerPath.stream()
                .map(waypoint -> new Waypoint(waypoint.x()+xOffset, waypoint.y()+yOffset, waypoint.heading()))
                .collect(Collectors.toList());
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
}
