package Tracer;

import com.flash3388.flashlib.time.Time;

import java.util.Arrays;
import java.util.List;

public class Trajectory {
    public Trajectory(Time timeStep, MotionParameters maxMotionParameters, List<Position> path) {

    }

    public Trajectory(Time timeStep, MotionParameters maxMotionParameters, Position... path) {
        this(timeStep, maxMotionParameters, Arrays.asList(path));
    }
}
