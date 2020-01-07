package tracer.profiles;

import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionParameters;
import tracer.trajectories.Trajectory;

import java.util.ArrayList;
import java.util.List;

public class ProfileFactory {
    public static Profile createTrajectoryProfile(Profile prevProfile, MotionParameters max, Trajectory trajectory) {
        return createTrajectoryProfile(prevProfile.initialDistance(), prevProfile.finalParameters().velocity(), max, prevProfile.initialTimestamp(), trajectory);
    }

    public static Profile createTrajectoryProfile(double initialDistance, double initialVelocity, MotionParameters max, Time startTime, Trajectory trajectory) {
        List<Profile> profiles = new ArrayList<>();
        max = adjustMaxParameters(max, trajectory.end());

        profiles.add(createStartSCurve(initialDistance, initialVelocity, max, startTime));
        profiles.add(createConstantVelocityProfile(profiles.get(0), trajectory));
        profiles.add(createEndSCurve(profiles.get(1), max));

        return new ComplexProfile(initialDistance, MotionParameters.stop(), startTime, profiles);
    }

    public static Profile createSCurve(Profile prevProfile, MotionParameters max) {
        return createSCurve(prevProfile.absoluteLength(), prevProfile.finalParameters().velocity(), max, prevProfile.finalTimestamp());
    }

    public static Profile createSCurve(double initialDistance, double initialVelocity, MotionParameters max, Time startTime) {
        List<Profile> profiles = new ArrayList<>();
        profiles.add(new ConcaveProfile(initialDistance, initialVelocity, max, startTime));
        profiles.add(createLinearProfile(profiles.get(0), max));
        profiles.add(new ConvexProfile(profiles.get(1), max));

        return new ComplexProfile(initialDistance, MotionParameters.constantVelocity(initialVelocity), startTime, profiles);
    }

    private static MotionParameters adjustMaxParameters(MotionParameters max, double targetDistance) {
        if(isIllegalVelocity(max, targetDistance))
            return new MotionParameters(calcAppropriateVelocity(max, targetDistance), max.acceleration(), max.jerk());
        else
            return max;
    }

    private static boolean isIllegalVelocity(double velocity, MotionParameters max, double targetDistance) {
        return isIllegalVelocity(new MotionParameters(velocity, max.acceleration(), max.jerk()), targetDistance);
    }

    private static boolean isIllegalVelocity(MotionParameters max, double targetDistance) {
        try {
            return Math.abs(distancePassedInTwoSCurves(max)) > Math.abs(targetDistance);
        } catch (IllegalArgumentException ignored) {
            return true;
        }
    }

    private static double distancePassedInTwoSCurves(MotionParameters max) {
         return createStartSCurve(0.0, 0.0, max, Time.milliseconds(0)).length() * 2;
    }

    private static double calcAppropriateVelocity(MotionParameters max, double targetDistance) {
        double result = targetDistance * max.jerk() / (2*max.acceleration()) - (1 + 2/3.0) * Math.pow(max.acceleration(), 3)/(2*max.acceleration());

        if(isIllegalVelocity(result, max, targetDistance))
            throw new IllegalArgumentException("Too small distance compared to maximum jerk and acceleration");
        return result;
    }

    private static Profile createStartSCurve(double initialDistance, double initialVelocity, MotionParameters max, Time startTime) {
        return createSCurve(initialDistance, initialVelocity, max, startTime);
    }

    private static ConstantVelocityProfile createConstantVelocityProfile(Profile prevProfile, Trajectory trajectory) {
        return new ConstantVelocityProfile(prevProfile, calcConstantVelocityDuration(prevProfile, trajectory.end()));
    }

    private static Time calcConstantVelocityDuration(Profile sCurve, double trajectoryLength) {
        return Time.seconds((trajectoryLength - 2 * sCurve.length()) / Math.abs(sCurve.finalParameters().velocity()));
    }

    private static Profile createEndSCurve(Profile prevProfile, MotionParameters max) {
        MotionParameters reversedMotionParameters = new MotionParameters(0, -max.acceleration(), -max.jerk());

        return createSCurve(prevProfile, reversedMotionParameters);
    }

    private static LinearVelocityProfile createLinearProfile(Profile concave, MotionParameters max) {
        return new LinearVelocityProfile(concave, calcLinearProfileDuration(concave, max));
    }

    private static Time calcLinearProfileDuration(Profile concave, MotionParameters max) {
        double linearEndVelocity = max.velocity() - Math.pow(max.acceleration(), 2)/(2 * max.jerk());

        return Time.seconds((linearEndVelocity - concave.finalParameters().velocity())/max.acceleration());
    }
}
