import calculus.splines.SplineType;
import calculus.splines.parameters.Waypoint;
import calculus.trajectories.TankTrajectory;
import com.flash3388.flashlib.robot.motion.Direction;
import com.flash3388.flashlib.time.Time;
import tracer.controllers.TankTrajectoryController;
import tracer.controllers.factories.TankTrajectoryControllerFactory;
import tracer.controllers.parameters.MotionControllerParameters;
import tracer.controllers.parameters.PidControllerParameters;
import tracer.motion.MotionState;
import tracer.motion.Position;

public class Main {
    public static void main(String[] args) {
        TankTrajectory trajectory = new TankTrajectory(SplineType.QUINTIC_HERMITE, 0.6, new Waypoint(), new Waypoint(2, 2, Math.toRadians(90)));
        TankTrajectoryControllerFactory factory = new TankTrajectoryControllerFactory(new PidControllerParameters(0, 0, 0), new MotionControllerParameters(0, 0, 0), 0.4);
        TankTrajectoryController controller = factory.create(trajectory, MotionState.frcKitOfPartsParameters(1.5), 12, Direction.FORWARD);
        
        for (int i = 0; i < controller.duration().valueAsMillis(); i++) {
            controller.calcForRight(new Position(Time.milliseconds(i), 0, 0));
        }
    }
}
