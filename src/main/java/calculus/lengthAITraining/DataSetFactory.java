package calculus.lengthAITraining;

import calculus.splines.SplineType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;

public class DataSetFactory {
    private final Map<SplineType, Function<Double, DataFactory>> factoryMap;

    public DataSetFactory() {
        factoryMap = new HashMap<>();

        factoryMap.put(SplineType.CUBIC_HERMITE, this::createCubicDataFactory);
        factoryMap.put(SplineType.QUINTIC_HERMITE, this::createQuinticDataFactory);
    }

    public List<TrainingElement> generateDataSet(SplineType type, double range, int numberOfSplines) {
        List<TrainingElement> result = new ArrayList<>();

        IntStream.range(0, numberOfSplines)
                .parallel()
                .forEach(i -> {
                    result.addAll(get(type, range).generateDataSet());
                    System.out.println("created set #"+i);
                });

        return result;
    }

    private CubicDataFactory createCubicDataFactory(double range) {
        return new CubicDataFactory(range);
    }

    private QuinticDataFactory createQuinticDataFactory(double range) {
        return new QuinticDataFactory(range);
    }

    private DataFactory get(SplineType type, double range) {
        return factoryMap.get(type).apply(range);
    }
}
