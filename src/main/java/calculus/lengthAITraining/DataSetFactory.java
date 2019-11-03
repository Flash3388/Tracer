package calculus.lengthAITraining;

import calculus.splines.SplineType;
import com.sun.tools.javac.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class DataSetFactory {
    private final Map<SplineType, Function<Pair<Double, Double>, DataFactory>> factoryMap;

    public DataSetFactory() {
        factoryMap = new HashMap<>();

        factoryMap.put(SplineType.CUBIC_HERMITE, pair -> createCubicDataFactory(pair.fst,pair.snd));
        factoryMap.put(SplineType.QUINTIC_HERMITE, pair -> createQuinticDataFactory(pair.fst,pair.snd));
    }

    public List<TrainingElement> generateDataSet(SplineType type, double min, double max, int numberOfSplines) {
        List<TrainingElement> result = new ArrayList<>();

        for (int i = 0; i < numberOfSplines; i++) {
            result.addAll(get(type, min, max).generateDataSet());
            System.out.println("created set #"+i);
        }

        return result;
    }

    private CubicDataFactory createCubicDataFactory(double min, double max) {
        return new CubicDataFactory(min, max);
    }

    private QuinticDataFactory createQuinticDataFactory(double min, double max) {
        return new QuinticDataFactory(min, max);
    }

    private DataFactory get(SplineType type, double min, double max) {
        return factoryMap.get(type).apply(new Pair<>(min, max));
    }
}
