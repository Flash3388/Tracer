package calculus.lengthAITraining;

import calculus.splines.SplineType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class DataSetFactory {

    public List<TrainingElement> generateDataSet(SplineType type, double range, int numberOfSplines) {
        List<TrainingElement> result = new ArrayList<>();

        IntStream.range(0, numberOfSplines)
                .parallel()
                .forEach(i -> {
                    result.addAll(new SplineDataFactory(type, range).generateDataSet());
                });

        return result;
    }
}
