import calculus.splines.SplineType;
import com.flash3388.flashlib.time.Time;
import tracer.controller.MotionController;
import tracer.motion.MotionParameters;
import tracer.motion.Position;
import tracer.motion.Waypoint;
import tracer.trajectory.Trajectory;

public class GenericTesting {
    public static void main(String[] args) {
        Trajectory trajectory = new Trajectory(SplineType.CUBIC_HERMITE,
                Waypoint.centimetersRadians(0,0, Math.PI/2),
                Waypoint.centimetersRadians(20, 100, Math.PI/2));
        MotionParameters max = MotionParameters.centimeterUnits(10, 5, 2.5);

        MotionController tracer = MotionController.forTrajectory(trajectory, max, 0.1, 0 , 1, 0, 0, 0);

        System.out.println(tracer.calculate(Position.centimetersDegrees(Time.seconds(0), 0, 90)));
    }
}
