package tracer.motion.basic;

import com.flash3388.flashlib.time.Time;

public interface DimensionalVectorRate<T extends  DimensionalVector> {
    Time timeToReach(T target);
}
