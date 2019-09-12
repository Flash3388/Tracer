import calculus.SplineType;
import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionParameters;
import tracer.motion.Position;
import tracer.trajectory.MotionController;
import tracer.trajectory.FunctionalTrajectory;

import java.util.stream.IntStream;

public class GenericTesting {
    public static void main(String[] args) {
        FunctionalTrajectory functionalTrajectory = new FunctionalTrajectory(SplineType.CUBIC_HERMITE,
                new Position(0,0, Math.PI/2),
                new Position(0, 100, Math.PI/2));
        MotionParameters max = new MotionParameters(10, 5, 2.5);

        MotionController tracer = MotionController.forFunctional(functionalTrajectory, max, 0.1, 0 , 1, 0, 0, 0);

        long startTime = System.currentTimeMillis();

//        IntStream.range(1, 14000)
//                .forEach(index -> tracer.calculate(Time.milliseconds(index), 0, 0));
//        long endTime = System.currentTimeMillis();

//        System.out.println((endTime - startTime)/14000);
    }
}
