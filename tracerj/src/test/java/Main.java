import calculus.splines.SplineType;
import calculus.splines.parameters.Waypoint;
import tracer.trajectories.Trajectory;

public class Main {
    public static void main(String[] args) {
        Waypoint start = new Waypoint(0, 0, 0);
        Waypoint end = new Waypoint(1, 1, Math.toRadians(90));
        Trajectory trajectory = new Trajectory(SplineType.CUBIC_HERMITE, start, end);
        for (double i = 0; i < trajectory.end(); i+=0.001) {
            Math.toDegrees(trajectory.angleRadAt(i));
        }
    }
}
