import calculus.splines.SplineType;
import calculus.splines.parameters.Waypoint;
import tracer.trajectories.Trajectory;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        Waypoint start = new Waypoint(0, 0, 0);
        Waypoint end = new Waypoint(1, 1, Math.toRadians(90));
        Trajectory trajectory = new Trajectory(SplineType.CUBIC_HERMITE, start, end);
        long startL = System.currentTimeMillis();
        for (double i = 0; i < trajectory.end(); i+=0.001) {
            trajectory.angleRadAt(i);
            try {
                TimeUnit.MILLISECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long endL = System.currentTimeMillis();
        System.out.println((endL - startL)/1000);
    }
}
