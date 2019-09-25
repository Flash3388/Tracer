import calculus.splines.SplineType;
import com.flash3388.flashlib.time.Time;
import tracer.controller.MotionController;
import tracer.motion.MotionParameters;
import tracer.motion.PhysicalPosition;
import tracer.motion.Position;
import tracer.trajectory.FunctionalTrajectory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GenericTesting {
    public static void main(String[] args) {
        FunctionalTrajectory functionalTrajectory = new FunctionalTrajectory(SplineType.CUBIC_HERMITE,
                new Position(0,0, Math.PI/2),
                new Position(0, 100, Math.PI/2));
        MotionParameters max = new MotionParameters(10, 5, 2.5);

        MotionController tracer = MotionController.forFunctional(functionalTrajectory, max, 0.1, 0 , 1, 0, 0, 0);

        System.out.println(tracer.calculate(new PhysicalPosition(Time.seconds(0), 0, Math.PI/2)));
    }
}
