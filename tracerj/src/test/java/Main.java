import calculus.splines.SplineType;
import calculus.splines.parameters.Waypoint;
import com.flash3388.flashlib.time.Time;
import tracer.controllers.TrajectoryController;
import tracer.motion.MotionParameters;
import tracer.motion.Position;
import tracer.trajectories.Trajectory;

public class Main {
    public static void main(String[] args) {
        MotionParameters max = new MotionParameters(0, 0.4, 0.2);
        Trajectory trajectory = new Trajectory(SplineType.CUBIC_HERMITE,
                new Waypoint(0, 0, Math.toRadians(0)),
                new Waypoint(1, 1, Math.toRadians(90)));
        TrajectoryController controller = new TrajectoryController(trajectory, max, 0.1, 0, 1, 0, 0.1, 0.1);

        long start = System.currentTimeMillis();
        for (long i = 0; i < 1000; i++)
            controller.calculate(new Position(Time.milliseconds(i), 0, 0));
        System.out.println((System.currentTimeMillis() - start)/1000.0);
    }
}
