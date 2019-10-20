import calculus.splines.SplineType;
import com.flash3388.flashlib.time.Time;
import tracer.controllers.MotionController;
import tracer.motion.MotionParameters;
import tracer.motion.Position;
import tracer.motion.Waypoint;
import tracer.motion.basic.Acceleration;
import tracer.motion.basic.Distance;
import tracer.motion.basic.Jerk;
import tracer.motion.basic.Velocity;
import tracer.trajectories.Trajectory;

public class GenericTesting {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        Trajectory trajectory = new Trajectory(SplineType.CUBIC_HERMITE,
                new Waypoint(Distance.centimeters(0),Distance.centimeters(0), 0),
                new Waypoint(Distance.centimeters(1), Distance.centimeters(1), 0),
                new Waypoint(Distance.centimeters(2), Distance.centimeters(-2), -Math.PI/2));

        MotionParameters max = new MotionParameters(Velocity.centimetersPerSecond(0.1), Acceleration.centimetersPerSecondPerSecond(0.05), Jerk.centimetersPerSecondPerSecondPerSecond(0.025));
        MotionController tracer = MotionController.forTrajectory(trajectory, max, 1, 0.5 , 1, 0, 0, 0);

        long end = System.currentTimeMillis();
        System.out.println();
        System.out.println("start "+(end-start));

        start = System.currentTimeMillis();

        for (int i = 0; i < 500; i++) {
            tracer.calculate(new Position(Time.seconds(i/10.0), Distance.centimeters(0), 45));
        }
        end = System.currentTimeMillis();
        System.out.println();
        System.out.println("per step "+(end-start));
    }
}
