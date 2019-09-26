package tracer.controller;

import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionParameters;
import tracer.motion.Position;
import tracer.profiles.Profile;
import tracer.profiles.ProfileFactory;
import tracer.trajectory.Trajectory;
import util.TimeConversion;

import java.util.function.Function;

public class MotionController extends Controller{
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

    public static MotionController forTrajectory(Trajectory trajectory, MotionParameters max, double kV, double kA, double kP, double kI, double kD, double gP) {
        Profile functionalProfile = ProfileFactory.createTrajectoryProfile(0, 0, max, Time.milliseconds(0), trajectory);
        Function<Time, Double> angleAt = time -> trajectory.angleAt(functionalProfile.distanceAt(time));;
        return new MotionController(functionalProfile, angleAt, kV, kA, kP, kI, kD, gP);
    }

    private MotionController(Profile trajectoryProfile, Function<Time, Double> angleAt, double kV, double kA, double kP, double kI, double kD, double gP) {
        super(trajectoryProfile);
        this.angleAt = angleAt;

        isFirstRun = true;

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

    @Override
    public double calculate(Position position) {
        Time timing = position.getTiming();
        double distance = position.getDistance();
        double angle = position.getAngle();

        double error = getProfile().distanceAt(timing) - distance;
        double velocity = getProfile().velocityAt(timing);
        double acceleration = getProfile().accelerationAt(timing);
        double angleError = getAngleError(timing, angle);

        if(isFirstRun) {
            lastError = error;
            lastTime = Time.seconds(0);
            totalError = 0;
            isFirstRun = false;
        }

        double pOut = kP * error;
        double iOut = kI * totalError;
        double dOut = kD * (error - lastError)/(TimeConversion.toSeconds(timing.sub(lastTime).add(Time.milliseconds(1))));//so there won't be a division by zero

        double vOut = kV * velocity;
        double aOut = kA * acceleration;

        double gOut = gP * angleError;

        System.out.println(kP + " " + error + " " + pOut);
        System.out.println(kD + " " + (error - lastError) + " " + dOut);
        System.out.println(kI + " " + totalError + " " + iOut);
        System.out.println(kV + " " + velocity + " " + vOut);
        System.out.println(kA + " " + acceleration + " " + aOut);
        System.out.println(gP + " " + angleError + " " + gOut);

        totalError += error;
        lastError = error;
        lastTime = timing;

        return pOut + iOut + dOut + vOut + aOut + gOut;
    }

    private double getAngleError(Time currentTime, double currentAngle) {
        double expected = angleAt.apply(currentTime);
        return expected == 3388 ? 0.0 : expected - currentAngle; //an issue might come up which the user is not responsible for, also values above 360 are pretty much not possible in this context
    }
}