package tracer.trajectory;

import com.flash3388.flashlib.time.Time;
import tracer.MotionParameters;
import tracer.motionProfiles.TrajectoryProfile;

public class Tracer {
    private final TrajectoryProfile trajectoryProfile;

    private final double kV;
    private final double kA;

    private final double kP;
    private final double kI;
    private final double kD;

    private final double gP;

    public Tracer(Trajectory trajectory, MotionParameters max, double kV, double kA, double kP, double kI, double kD, double gP) {
        trajectoryProfile = new TrajectoryProfile(0, max, Time.seconds(0), trajectory);

        this.kV = kV;
        this.kA = kA;

        this.kP = kP;
        this.kI = kI;
        this.kD = kD;

        this.gP = gP;
    }

    public double calculate(Time currentTime, double passedDistanceCentimeters) {

    }
}
