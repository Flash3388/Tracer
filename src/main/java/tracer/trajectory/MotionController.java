package tracer.trajectory;

import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionParameters;
import tracer.motion.PhysicalPosition;
import tracer.motionProfiles.OutsideOfTimeBoundsException;
import tracer.motionProfiles.PhysicalTrajectoryProfile;
import tracer.motionProfiles.Profile;
import tracer.motionProfiles.ProfileFactory;

import java.util.function.Function;

public class MotionController {
    private final Profile trajectoryProfile;
    private final Function<Time, Double> angleAt;

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

    public static MotionController forFunctional(FunctionalTrajectory functionalTrajectory, MotionParameters max, double kV, double kA, double kP, double kI, double kD, double gP) {
        Profile functionalProfile = ProfileFactory.createTrajectoryProfile(0, 0, max, Time.milliseconds(0), functionalTrajectory);
        Function<Time, Double> angleAt = time -> {
            try {
                return functionalTrajectory.angleAt(functionalProfile.distanceAt(time));
            } catch (OutsideOfTimeBoundsException e) {
                System.out.println(e.getMessage());
                return 0.0;
            }
        };
        return new MotionController(functionalProfile, angleAt, kV, kA, kP, kI, kD, gP);
    }

    public static MotionController forPhysical(PhysicalTrajectory physicalTrajectory, MotionParameters max, double kV, double kA, double kP, double kI, double kD, double gP) {
        PhysicalTrajectoryProfile physicalProfile = new PhysicalTrajectoryProfile(0, 0, max, Time.milliseconds(0), physicalTrajectory);
        return new MotionController(physicalProfile, physicalProfile::angleAt, kV, kA, kP, kI, kD, gP);
    }

    private MotionController(Profile trajectoryProfile, Function<Time, Double> angleAt, double kV, double kA, double kP, double kI, double kD, double gP) {
        this.trajectoryProfile = trajectoryProfile;
        this.angleAt = angleAt;

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

    public double calculate(PhysicalPosition position) {
        Time timing = position.getTiming();
        double distance = position.getDistance();
        double angle = position.getDistance();

        double error = getError(timing, distance);
        double velocity = getVelocity(timing);
        double acceleration = getAcceleration(timing);
        double angleError = getAngleError(timing, angle);

        if(isFirstRun) {
            lastError = error;
            lastTime = Time.seconds(0);
            totalError = 0;
            isFirstRun = false;
        }

        double pOut = kP * error;
        double iOut = kI * totalError;
//        double dOut = kD * (error - lastError)/(currentTime.sub(lastTime).valueAsMillis()/1000.0) - velocity;

        double vOut = kV * velocity;
        double aOut = kA * acceleration;

        double gOut = gP * angleError;

        totalError += error;
//        lastError = error;
//        lastTime = currentTime;

        return pOut + iOut + /*dOut*/ + vOut + aOut + gOut;
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
            return angleAt.apply(currentTime) - currentAngle;
    }
}
