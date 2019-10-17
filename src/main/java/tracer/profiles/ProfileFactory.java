package tracer.profiles;

import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionParameters;
import tracer.trajectories.Trajectory;

import java.util.ArrayList;
import java.util.List;

public class ProfileFactory {
    public static Profile createTrajectoryProfile(Profile prevProfile, MotionParameters max, Trajectory trajectory) {
        return createTrajectoryProfile(prevProfile.initialDistance(), prevProfile.endParameters().velocity(), max, prevProfile.start(), trajectory);
    }

    public static Profile createTrajectoryProfile(double initialDistance, double initialVelocity, MotionParameters max, Time startTime, Trajectory trajectory) {
        List<Profile> profiles = new ArrayList<>();
        max = adjustMaxParameters(max, trajectory.length());

        profiles.add(createStartSCurve(initialDistance, initialVelocity, max, startTime));
        profiles.add(createConstantVelocityProfile(profiles.get(0), trajectory));
        profiles.add(createEndSCurve(profiles.get(1), max));

        return new ComplexProfile(initialDistance, MotionParameters.stop(), startTime, calcTrajectoryDuration(max, initialDistance + trajectory.length()), profiles);
    }

    public static Profile createSCurve(Profile prevProfile, MotionParameters max) {
        return createSCurve(prevProfile.absoluteLength(), prevProfile.endParameters().velocity(), max, prevProfile.end());
    }

    public static Profile createSCurve(double initialDistance, double initialVelocity, MotionParameters max, Time startTime) {
        List<Profile> profiles = new ArrayList<>();
        profiles.add(createConcaveProfile(initialDistance, initialVelocity, max, startTime));
        profiles.add(createLinearProfile(profiles.get(0), max));
        profiles.add(createConvexProfile(max, profiles.get(1)));

        return new ComplexProfile(initialDistance, MotionParameters.constantVelocity(initialVelocity), startTime, calcSCurveDuration(max, initialVelocity), profiles);
    }

    private static MotionParameters adjustMaxParameters(MotionParameters max, double targetDistance) {
        System.out.println(distancePassedInTwoSCurves(max) + " gay " + targetDistance);
        if(distancePassedInTwoSCurves(max) > targetDistance)
            return MotionParameters.centimeterUnits(calcAppropriateVelocity(max, targetDistance), max.acceleration(), max.jerk());
        else
            return max;
    }

    private static double distancePassedInTwoSCurves(MotionParameters max) {
         return createStartSCurve(0.0, 0.0, max, Time.milliseconds(0)).length() * 2;
    }

    private static double calcAppropriateVelocity(MotionParameters max, double targetDistance) {
        double result = targetDistance * max.jerk() / (2*max.acceleration()) - (1 + 2/3.0) * Math.pow(max.acceleration(), 3)/(2*max.acceleration());

        if(result < 0)
            throw new TooSmallDistanceException();
        return result;
    }

    private static Time calcTrajectoryDuration(MotionParameters max, double trajectoryLength) {
        Profile sCurve = createSCurve(0, 0, max, Time.seconds(0));

        return sCurve.duration().add(calcConstantVelocityDuration(sCurve, trajectoryLength)).add(sCurve.duration());
    }

    private static Profile createStartSCurve(double initialDistance, double initialVelocity, MotionParameters max, Time startTime) {
        return createSCurve(initialDistance, initialVelocity, max, startTime);
    }

    private static ConstantVelocityProfile createConstantVelocityProfile(Profile prevProfile, Trajectory trajectory) {
        return new ConstantVelocityProfile(prevProfile, calcConstantVelocityDuration(prevProfile, trajectory.length()));
    }

    private static Time calcConstantVelocityDuration(Profile sCurve, double trajectoryLength) {
        return Time.seconds((trajectoryLength - 2 * sCurve.length()) / sCurve.endParameters().velocity());
    }

    private static Profile createEndSCurve(Profile prevProfile, MotionParameters max) {
        MotionParameters reversedMotionParameters = MotionParameters.centimeterUnits(0, -max.acceleration(), -max.jerk());

        return createSCurve(prevProfile, reversedMotionParameters);
    }

    private static Time calcSCurveDuration(MotionParameters max, double initialVelocity) {
        Profile concave = new ConcaveProfile(0, initialVelocity, max, Time.seconds(0));

        return concave.end().add(calcLinearProfileDuration(concave, max)).add(concave.end());
    }

    private static ConcaveProfile createConcaveProfile(double initialDistance, double initialVelocity, MotionParameters max, Time startTime) {
        return new ConcaveProfile(initialDistance, initialVelocity, max, startTime);
    }

    private static LinearVelocityProfile createLinearProfile(Profile concave, MotionParameters max) {
        return new LinearVelocityProfile(concave, calcLinearProfileDuration(concave, max));
    }

    private static Time calcLinearProfileDuration(Profile concave, MotionParameters max) {
        double linearEndVelocity = max.velocity() - Math.pow(max.acceleration(), 2)/(2 * max.jerk());

        return Time.seconds((linearEndVelocity - concave.endParameters().velocity())/max.acceleration());
    }

    private static ConvexProfile createConvexProfile(MotionParameters max, Profile linear) {
        return new ConvexProfile(linear, max);
    }
}
