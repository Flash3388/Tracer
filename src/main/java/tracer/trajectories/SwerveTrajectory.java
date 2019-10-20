package tracer.trajectories;

import calculus.splines.SplineType;
import tracer.motion.Waypoint;
import tracer.motion.basic.Distance;

import java.util.List;
import java.util.stream.Collectors;

public class SwerveTrajectory {
    private final Trajectory rearLeft;
    private final Trajectory rearRight;
    private final Trajectory frontLeft;
    private final Trajectory frontRight;

    public SwerveTrajectory(SplineType splineType, List<Waypoint> centerPath, Distance widthDistance, Distance lengthDistance) {
        rearLeft = new Trajectory(splineType, shiftPath(centerPath, widthDistance.scaleValue(-0.5), lengthDistance.scaleValue(-0.5)));
        rearRight = new Trajectory(splineType, shiftPath(centerPath, widthDistance.scaleValue(0.5), lengthDistance.scaleValue(-0.5)));;
        frontLeft = new Trajectory(splineType, shiftPath(centerPath, widthDistance.scaleValue(-0.5), lengthDistance.scaleValue(0.5)));;
        frontRight = new Trajectory(splineType, shiftPath(centerPath, widthDistance.scaleValue(0.5), lengthDistance.scaleValue(0.5)));;
    }

    private List<Waypoint> shiftPath(List<Waypoint> centerPath, Distance xOffset, Distance yOffset) {
        return centerPath.stream()
                .map(waypoint -> new Waypoint(waypoint.x().add(xOffset), waypoint.y().add(yOffset), waypoint.heading()))
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
