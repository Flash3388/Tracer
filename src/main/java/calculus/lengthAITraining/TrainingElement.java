package calculus.lengthAITraining;

import calculus.splines.Spline;

import java.util.ArrayList;
import java.util.List;

public class TrainingElement {
    private final List<Double> input;
    private final double result;

    public TrainingElement(Spline spline, double percentage) {
        input = new ArrayList<>();
        input.addAll(spline.yFunction().modifiers());
        input.addAll(spline.xFunction().modifiers());
        input.add(spline.parametricFunction().lengthBetween(0, percentage, 0.0001));
        result = percentage;
    }

    @Override
    public String toString() {
        return input.toString().replace('[', ' ').replace(']', ' ') + ":: " + result;
    }
}
