import calculus.splines.SplineType;
import com.flash3388.flashlib.time.Time;
import tracer.controller.MotionController;
import tracer.motion.MotionParameters;
import tracer.motion.Position;
import tracer.motion.Waypoint;
import tracer.trajectory.Trajectory;

import java.util.stream.IntStream;

public class GenericTesting {
    public static void main(String[] args) {
        Trajectory trajectory = new Trajectory(SplineType.CUBIC_HERMITE,
                Waypoint.centimetersRadians(0,0, 0),
                Waypoint.centimetersRadians(2, 2, Math.PI/2));

        MotionParameters max = MotionParameters.centimeterUnits(0.1, 0.05, 0.025);

        MotionController tracer = MotionController.forTrajectory(trajectory, max, 1, 0.5 , 1, 0, 0, 0);

        long start = System.currentTimeMillis();
        IntStream.range(0, 13)
                .forEach(i -> tracer.calculate(Position.centimetersDegrees(Time.seconds(i), 0, 45)));
        long end = System.currentTimeMillis();
        System.out.println((end-start)/17.0);
    }
}
