package calculus.lengthAITraining;

import calculus.splines.HermiteQuinticSpline;
import tracer.motion.Waypoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuinticDataFactory implements DataFactory{
    private final static double ACCURACY = 0.0001;
    private final HermiteQuinticSpline spline;

    public QuinticDataFactory(double functionRangeStart, double functionRangeEnd) {
        Random rnd = new Random();
        Waypoint start = Waypoint.centimetersRadians(functionRangeStart * rnd.nextDouble(), functionRangeStart * rnd.nextDouble(), 2*Math.PI * rnd.nextDouble());
        Waypoint end = Waypoint.centimetersRadians(functionRangeEnd * rnd.nextDouble(), functionRangeEnd * rnd.nextDouble(), 2*Math.PI * rnd.nextDouble());

        spline = new HermiteQuinticSpline(start, end, 0);
    }

    @Override
    public List<TrainingElement> generateDataSet() {
        List<TrainingElement> elements = new ArrayList<>();

        for (double i = 0; i < 1; i+=ACCURACY)
            elements.add(new TrainingElement(spline, i));

        return elements;
    }
}
