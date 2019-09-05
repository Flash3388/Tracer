package tracer.trajectory;

import com.flash3388.flashlib.math.Mathf;
import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionParameters;
import tracer.motionProfiles.OutsideOfTimeBoundsException;
import tracer.motionProfiles.TrajectoryProfile;

public class MotionController {
    private final TrajectoryProfile trajectoryProfile;

    private boolean isFirstRun;
    private double totalError;
    private double lastError;
    private Time lastTime;

    private final double kV;
    private final double kA;

    private final double kP;
    private final double kI;
    private final double kD;

    private final double gP;

    public MotionController(Trajectory trajectory, MotionParameters max, double kV, double kA, double kP, double kI, double kD, double gP) {
        trajectoryProfile = new TrajectoryProfile(0, max, Time.seconds(0), trajectory);

        isFirstRun = false;

        this.kV = kV;
        this.kA = kA;

        this.kP = kP;
        this.kI = kI;
        this.kD = kD;

        this.gP = gP;
    }

    public void reset() {
        isFirstRun = false;
    }

    public double calculate(Time currentTime, double passedDistanceCentimeters, double angleRadians) {
        double error = getError(currentTime, passedDistanceCentimeters);
        double velocity = getVelocity(currentTime);
        double acceleration = getAcceleration(currentTime);
        double angleError = getAngleError(currentTime, angleRadians);

        if(isFirstRun) {
            lastError = error;
            lastTime = Time.seconds(0);
            totalError = 0;
        }

        double pOut = kP * error;
        double iOut = kI * totalError;
        double dOut = kD * (error - lastError)/(currentTime.sub(lastTime).valueAsMillis()/1000.0) - velocity;

        double vOut = kV * velocity;
        double aOut = kA * acceleration;

        double gOut = gP * angleError;

        totalError += error;
        lastError = error;
        lastTime = currentTime;

        return pOut + iOut + dOut + vOut + aOut + gOut;
    }

    private double getError(Time currentTime, double passedDistance) {
        try {
            return trajectoryProfile.distanceAt(currentTime) - passedDistance;
        } catch (OutsideOfTimeBoundsException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    private double getVelocity(Time currentTime) {
        try {
            return trajectoryProfile.velocityAt(currentTime);
        } catch (OutsideOfTimeBoundsException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    private double getAcceleration(Time currentTime) {
        try {
            return trajectoryProfile.accelerationAt(currentTime);
        } catch (OutsideOfTimeBoundsException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    private double getAngleError(Time currentTime, double currentAngle) {
        try {
            return Mathf.translateInRange(trajectoryProfile.angleAt(currentTime), 2 * Math.PI, false) - currentAngle;
        } catch (OutsideOfTimeBoundsException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }
}
