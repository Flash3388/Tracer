package calculus.lengthAITraining;

import calculus.splines.HermiteCubicSpline;
import tracer.motion.Waypoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CubicDataFactory implements DataFactory{
    private final static double ACCURACY = 0.0001;
    private final HermiteCubicSpline spline;

    public CubicDataFactory(double functionRangeStart, double functionRangeEnd) {
        Random rnd = new Random();
        Waypoint start = Waypoint.centimetersRadians(functionRangeStart * rnd.nextDouble(), functionRangeStart * rnd.nextDouble(), 2*Math.PI * rnd.nextDouble());
        Waypoint end = Waypoint.centimetersRadians(functionRangeEnd * rnd.nextDouble(), functionRangeEnd * rnd.nextDouble(), 2*Math.PI * rnd.nextDouble());

        spline = new HermiteCubicSpline(start, end, 0);
    }

    @Override
    public List<TrainingElement> generateDataSet() {
        List<TrainingElement> elements = new ArrayList<>();

        for (double i = 0; i < 1; i+=ACCURACY)
            elements.add(new TrainingElement(spline, i));

        return elements;
    }
}
