import calculus.splines.SplineType;
import calculus.splines.parameters.Waypoint;
import tracer.trajectories.Trajectory;

public class Main {
    public static void main(String[] args) {
        Waypoint start = new Waypoint(0, 0, 0);
        Waypoint end = new Waypoint(2, 2, Math.toRadians(-90));
        Trajectory trajectory = new Trajectory(SplineType.CUBIC_HERMITE, start, end);

        long startL = System.currentTimeMillis();
        for (double i = 0; i < trajectory.end(); i+=0.001)
            trajectory.angleRadAt(i);
        long endL = System.currentTimeMillis();
        System.out.println((endL - startL)/100);
    }
}
