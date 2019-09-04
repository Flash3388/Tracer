import calculus.SplineType;
import tracer.MotionParameters;
import tracer.Position;
import tracer.trajectory.MotionController;
import tracer.trajectory.Trajectory;

public class SplineTest {
    public static void main(String[] args) {
        Trajectory trajectory = new Trajectory(SplineType.CUBIC_HERMITE,
                new Position(0,0, Math.PI/2),
                new Position(0, 100, Math.PI/2));
        MotionParameters max = new MotionParameters(10, 5, 2.5);

        MotionController tracer = new MotionController(trajectory, max, 0.1, 0 , 1, 0, 0, 0);
    }
}
