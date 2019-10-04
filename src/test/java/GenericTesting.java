import calculus.functions.polynomialFunctions.PolynomialFunction;
import calculus.functions.polynomialFunctions.Quadratic;
import calculus.splines.SplineType;
import tracer.motion.Waypoint;
import tracer.trajectory.Trajectory;

public class GenericTesting {
    public static void main(String[] args) {
        Trajectory trajectory = new Trajectory(SplineType.CUBIC_HERMITE,
                Waypoint.centimetersRadians(0,0, 0),
                Waypoint.centimetersRadians(1, 1, Math.PI/2));

        Quadratic q = new Quadratic(1, 1, 1);
        System.out.println(q);
        System.out.println(q.mul(new PolynomialFunction(1.0, 1.0)));
//        MotionParameters max = MotionParameters.centimeterUnits(0.1, 0.05, 0.025);
//
//        MotionController tracer = MotionController.forTrajectory(trajectory, max, 1, 0.5 , 1, 0, 0, 0);
//
//        long start = System.currentTimeMillis();
//        IntStream.range(0, 17)
//                .forEach(i -> tracer.calculate(Position.centimetersDegrees(Time.seconds(i), 0, 45)));
//        long end = System.currentTimeMillis();
//        System.out.println((end-start)/17.0);
    }
}
