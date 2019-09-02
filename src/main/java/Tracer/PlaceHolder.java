package Tracer;

import com.flash3388.flashlib.time.Time;

public class PlaceHolder {
    private final Trajectory trajectory;

    public PlaceHolder(Trajectory trajectory) {
        this.trajectory = trajectory;
    }

    public double VelocityAt(Time time) {
        return 0.0;
    }

    public double distanceAt(Time time) {
        return 0.0;
    }

    public double angleAt(Time time) {
        return 0.0;
    }
}
