import calculus.splines.SplineType;
import calculus.splines.parameters.Waypoint;
import calculus.trajectories.Trajectory;

public class Main {
    public static void main(String[] args) {
        Trajectory trajectory = new Trajectory(SplineType.CUBIC_HERMITE, new Waypoint(0, 0 ,0), new Waypoint(0, 1, Math.toRadians(180)));

        for (double i = 0; i < trajectory.end(); i+=0.001) {
            System.out.println(String.format("%.4f", Math.toDegrees(trajectory.angleRadAt(i))));
        }
    }
}
