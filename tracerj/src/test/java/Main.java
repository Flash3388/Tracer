import calculus.splines.SplineType;
import calculus.splines.parameters.Waypoint;
import calculus.trajectories.Trajectory;
import com.flash3388.flashlib.time.Time;
import tracer.controllers.TrajectoryController;
import tracer.controllers.factories.TrajectoryControllerFactory;
import tracer.controllers.parameters.MotionControllerParameters;
import tracer.controllers.parameters.PidControllerParameters;
import tracer.motion.MotionState;
import tracer.units.angle.Angle;
import tracer.units.distance.Distance;

public class Main {
    public static void main(String[] args) {
        MotionState max = MotionState.meterUnits(1, 2, 4);
        Trajectory trajectory = new Trajectory(SplineType.QUINTIC_HERMITE, new Waypoint(), new Waypoint(Distance.meters(3), Distance.meters(2), Angle.degrees(90)));
        MotionControllerParameters motionParameters = new MotionControllerParameters(4, 1.05, 0);
        PidControllerParameters pidParameters = new PidControllerParameters(1, 0, 0);

        TrajectoryControllerFactory factory = TrajectoryControllerFactory.single(pidParameters, motionParameters);
        TrajectoryController controller = factory.create(trajectory, max, 12, Time.milliseconds(0), true);
        System.out.println(controller.duration());
    }
}
