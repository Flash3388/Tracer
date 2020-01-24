package tracer.units.generic;

import com.flash3388.flashlib.util.CompareResult;
import com.jmath.ExtendedMath;

public interface Unit extends Comparable<Unit> {
    double value();
    Unit add(Unit other);
    Unit sub(Unit other);

    default boolean equals(Unit other) {
        return sub(other).value() == 0;
    }

    @Override
    default int compareTo(Unit other) {
        return (int) ExtendedMath.constrain(sub(other).value(), CompareResult.SMALLER_THAN.value(), CompareResult.GREATER_THAN.value());
    }
}
