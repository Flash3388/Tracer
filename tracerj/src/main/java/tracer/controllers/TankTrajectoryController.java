package tracer.controllers;

import com.flash3388.flashlib.time.Time;
import tracer.motion.Position;

public class TankTrajectoryController implements Followable {
    private final TrajectoryController left;
    private final TrajectoryController right;

    public TankTrajectoryController(TrajectoryController left, TrajectoryController right) {
        this.left = left;
        this.right = right;
    }

    public double calcForLeft(Position position) {
        return left.calculate(position);
    }

    public double calcForRight(Position position) {
        return right.calculate(position);
    }

    @Override
    public void reset() {
        left.reset();
        right.reset();
    }

    @Override
    public Time duration() {
        return right.duration().after(left.duration()) ? right.duration() : left.duration();
    }
}
