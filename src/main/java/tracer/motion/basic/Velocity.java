package tracer.motion.basic;

import com.flash3388.flashlib.time.Time;

public class Velocity extends DimensionalVector implements DimensionalVectorRate<DimensionalVector>{
    public Velocity(long value, DimensionUnit dimensionUnit) {
        super(value, dimensionUnit);
    }

    @Override
    public Time timeToReach(DimensionalVector target) {
        return null;
    }
}
