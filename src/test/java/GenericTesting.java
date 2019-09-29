import calculus.functions.Cubic;
import calculus.functions.Quartic;

public class GenericTesting {
    public static void main(String[] args) {
//        Trajectory trajectory = new Trajectory(SplineType.CUBIC_HERMITE,
//                Waypoint.centimetersRadians(0,0, Math.PI/2),
//                Waypoint.centimetersRadians(0, 100, Math.PI/2));
//        MotionParameters max = MotionParameters.centimeterUnits(10, 5, 2.5);
//
//        MotionController tracer = MotionController.forTrajectory(trajectory, max, 0.1, 0 , 1, 0, 0, 0);
//
//        System.out.println(tracer.calculate(Position.centimetersDegrees(Time.seconds(0), 0, 90)));
        Quartic q = Quartic.fromConstants(1, 2, 3, 4, 5);
        System.out.println(q.solutions(0));

        Cubic c = Cubic.fromConstants(1, -21.25, -20.25,0);
//        System.out.println(c.solutions(0));
    }
}
