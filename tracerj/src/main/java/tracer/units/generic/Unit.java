package tracer.units.generic;

import com.flash3388.flashlib.util.CompareResult;
import com.jmath.ExtendedMath;

public interface Unit extends Comparable<Unit> {
    double DEF_DELTA = 0.001;

    double value();
    Unit add(Unit other);
    Unit sub(Unit other);

    default boolean equals(Unit other) {
        return ExtendedMath.equals(sub(other).value(), 0, DEF_DELTA);
    }

    @Override
    default int compareTo(Unit other) {
        return (int) ExtendedMath.constrain(sub(other).value(), CompareResult.SMALLER_THAN.value(), CompareResult.GREATER_THAN.value());
    }
}
