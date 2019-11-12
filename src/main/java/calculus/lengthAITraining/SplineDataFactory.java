package calculus.lengthAITraining;

import calculus.functions.MathFunction;
import calculus.splines.Spline;
import calculus.splines.SplineFactory;
import calculus.splines.SplineType;
import tracer.motion.Waypoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SplineDataFactory implements DataFactory{
    private final static double ACCURACY = 0.001;
    private final Spline spline;

    public SplineDataFactory(SplineType type, double range) {
        Random rnd = new Random();
        Waypoint start = Waypoint.centimetersRadians(randomVal(rnd, range), randomVal(rnd, range), 2*Math.PI * rnd.nextDouble());
        Waypoint end = Waypoint.centimetersRadians(randomVal(rnd, range), randomVal(rnd, range), 2*Math.PI * rnd.nextDouble());

        SplineFactory factory = new SplineFactory();
        spline =  factory.get(type, start, end, 0);
    }

    @Override
    public List<TrainingElement> generateDataSet() {
        List<TrainingElement> dataset = new ArrayList<>();
        MathFunction parametric = spline.parametricFunction();
        double sum = 0;
        for(double i = 0; i < 1; i+=ACCURACY) {
            dataset.add(new TrainingElement(spline, i, sum));
            sum += parametric.shortestLength(i, i+ACCURACY);
        }

        return dataset;
    }

    private double randomVal(Random rnd, double range) {
        return rnd.nextDouble() * range - rnd.nextDouble()*range;
    }
}
