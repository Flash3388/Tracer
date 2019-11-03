package calculus.lengthAITraining;

import calculus.splines.HermiteQuinticSpline;
import tracer.motion.Waypoint;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QuinticDataFactory implements DataFactory{
    private final static double ACCURACY = 0.0001;
    private final HermiteQuinticSpline spline;

    public QuinticDataFactory(double range) {
        Random rnd = new Random();
        Waypoint start = Waypoint.centimetersRadians(randomVal(rnd, range), randomVal(rnd, range), 2*Math.PI * rnd.nextDouble());
        Waypoint end = Waypoint.centimetersRadians(randomVal(rnd, range), randomVal(rnd, range), 2*Math.PI * rnd.nextDouble());

        spline = new HermiteQuinticSpline(start, end, 0);
    }

    @Override
    public List<TrainingElement> generateDataSet() {
        return IntStream.range(0, (int)(1/ACCURACY))
                .parallel()
                .mapToObj(i -> new TrainingElement(spline, i * ACCURACY))
                .collect(Collectors.toList());
    }

    private double randomVal(Random rnd, double range) {
        return rnd.nextDouble() * range - rnd.nextDouble()*range;
    }
}
