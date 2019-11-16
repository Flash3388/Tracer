import calculus.splines.SplineType;
import com.flash3388.flashlib.time.Time;
import tracer.controllers.TrajectoryController;
import tracer.motion.MotionParameters;
import tracer.motion.Position;
import tracer.motion.Waypoint;
import tracer.trajectories.Trajectory;

import java.util.concurrent.TimeUnit;

public class GenericTesting {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        Trajectory trajectory = new Trajectory(SplineType.CUBIC_HERMITE,
                Waypoint.centimetersRadians(0,0, 0),
                Waypoint.centimetersRadians(1, 1, 0),
                Waypoint.centimetersRadians(2, -2, -Math.PI/2));

        MotionParameters max = MotionParameters.centimeterUnits(0.1, 0.05, 0.025);
        TrajectoryController tracer = new TrajectoryController(trajectory, max, 1, 0.5 , 1, 0, 0, 0);

        long end = System.currentTimeMillis();
        System.out.println();
        System.out.println("start "+(end-start));

        start = System.currentTimeMillis();

        for (int i = 0; i < 500; i++) {
            tracer.calculate(Position.centimetersDegrees(Time.seconds(i/10.0), 0, 45));
            try {
                TimeUnit.MILLISECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        end = System.currentTimeMillis();
        System.out.println();
        System.out.println((end - start - 10000)/500.0);

    }
}

