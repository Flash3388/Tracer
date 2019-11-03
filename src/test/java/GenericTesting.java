import calculus.lengthAITraining.DataSetFactory;
import calculus.lengthAITraining.TrainingElement;
import calculus.splines.SplineType;
import com.flash3388.flashlib.time.Time;
import tracer.controllers.MotionController;
import tracer.motion.MotionParameters;
import tracer.motion.Position;
import tracer.motion.Waypoint;
import tracer.trajectories.Trajectory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class GenericTesting {
    public static void main(String[] args) {
//        long start = System.currentTimeMillis();
//
//        Trajectory trajectory = new Trajectory(SplineType.CUBIC_HERMITE,
//                Waypoint.centimetersRadians(0,0, 0),
//                Waypoint.centimetersRadians(1, 1, 0),
//                Waypoint.centimetersRadians(2, -2, -Math.PI/2));
//
//        MotionParameters max = MotionParameters.centimeterUnits(0.1, 0.05, 0.025);
//        MotionController tracer = MotionController.forTrajectory(trajectory, max, 1, 0.5 , 1, 0, 0, 0);
//
//        long end = System.currentTimeMillis();
//        System.out.println();
//        System.out.println("start "+(end-start));
//
//        start = System.currentTimeMillis();
//
//        for (int i = 0; i < 500; i++) {
//            tracer.calculate(Position.centimetersDegrees(Time.seconds(i/10.0), 0, 45));
//        }
//        end = System.currentTimeMillis();
//        System.out.println();
//        System.out.println((e nd-start)/500.0);

        System.out.println("started");
        List<TrainingElement> dataSet = new DataSetFactory().generateDataSet(SplineType.CUBIC_HERMITE, 0, 1000, 1000);
        System.out.println("created all data sets");
        System.out.println(dataSet.size());
        try {
            FileWriter fw = new FileWriter("C:\\Users\\Daniel\\Documents\\plusDataSet.txt", false);
            PrintWriter writer = new PrintWriter(fw);
            for (TrainingElement element : dataSet) {
                writer.println(element.toString());
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("failed at writing data");
        }
        System.out.println("wrote data");
    }
}
