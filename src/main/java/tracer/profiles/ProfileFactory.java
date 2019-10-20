package tracer.profiles;

import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionParameters;
import tracer.motion.basic.Distance;
import tracer.motion.basic.Velocity;
import tracer.motion.basic.units.UnitConversion;
import tracer.trajectories.Trajectory;

import java.util.ArrayList;
import java.util.List;

public class ProfileFactory {
    public static Profile createTrajectoryProfile(Profile prevProfile, MotionParameters max, Trajectory trajectory) {
        return createTrajectoryProfile(prevProfile.initialDistance(), prevProfile.endParameters().velocity(), max, prevProfile.start(), trajectory);
    }

    public static Profile createTrajectoryProfile(Distance initialDistance, Velocity initialVelocity, MotionParameters max, Time startTime, Trajectory trajectory) {
        List<Profile> profiles = new ArrayList<>();
        max = adjustMaxParameters(max, trajectory.length());

        profiles.add(createStartSCurve(initialDistance, initialVelocity, max, startTime));
        profiles.add(createConstantVelocityProfile(profiles.get(0), trajectory));
        profiles.add(createEndSCurve(profiles.get(1), max));

        return new ComplexProfile(initialDistance, MotionParameters.stop(), startTime, profiles);
    }

    public static Profile createSCurve(Profile prevProfile, MotionParameters max) {
        return createSCurve(prevProfile.absoluteLength(), prevProfile.endParameters().velocity(), max, prevProfile.end());
    }

    public static Profile createSCurve(Distance initialDistance, Velocity initialVelocity, MotionParameters max, Time startTime) {
        List<Profile> profiles = new ArrayList<>();
        profiles.add(new ConcaveProfile(initialDistance, initialVelocity, max, startTime));
        profiles.add(createLinearProfile(profiles.get(0), max));
        profiles.add(new ConvexProfile(profiles.get(1), max));

        return new ComplexProfile(initialDistance, MotionParameters.constantVelocity(initialVelocity), startTime, profiles);
    }

    private static MotionParameters adjustMaxParameters(MotionParameters max, Distance targetDistance) {
        if(distancePassedInTwoSCurves(max).largerThen(targetDistance))
            return new MotionParameters(calcAppropriateVelocity(max, targetDistance), max.acceleration(), max.jerk());
        else
            return max;
    }

    private static Distance distancePassedInTwoSCurves(MotionParameters max) {
         return createStartSCurve(Distance.centimeters(0), Velocity.centimetersPerSecond(0), max, Time.milliseconds(0)).length().scaleValue(2);
    }

    private static Velocity calcAppropriateVelocity(MotionParameters max, Distance targetDistance) {
        double a = UnitConversion.toCentimetersPerSecondPerSecond(max.acceleration());
        double j = UnitConversion.toCentimetersPerSecondPerSecondPerSecond(max.jerk());
        double d = UnitConversion.toCentimeters(targetDistance);
        double result = d * j / (2*a) - (1 + 2/3.0) * Math.pow(a, 3)/(2*a);

        if(result < 0)
            throw new TooSmallDistanceException();
        return Velocity.centimetersPerSecond(result);
    }

    private static Profile createStartSCurve(Distance initialDistance, Velocity initialVelocity, MotionParameters max, Time startTime) {
        return createSCurve(initialDistance, initialVelocity, max, startTime);
    }

    private static ConstantVelocityProfile createConstantVelocityProfile(Profile prevProfile, Trajectory trajectory) {
        return new ConstantVelocityProfile(prevProfile, calcConstantVelocityDuration(prevProfile, trajectory.length()));
    }

    private static Time calcConstantVelocityDuration(Profile sCurve, Distance trajectoryLength) {
        return Time.seconds(UnitConversion.toCentimeters(trajectoryLength.sub(sCurve.length().scaleValue(2)))/
                UnitConversion.toCentimetersPerSecond(sCurve.endParameters().velocity()));
    }

    private static Profile createEndSCurve(Profile prevProfile, MotionParameters max) {
        MotionParameters reversedMotionParameters = new MotionParameters(Velocity.centimetersPerSecond(0), max.acceleration().reverse(), max.jerk().reverse());

        return createSCurve(prevProfile, reversedMotionParameters);
    }

    private static LinearVelocityProfile createLinearProfile(Profile concave, MotionParameters max) {
        return new LinearVelocityProfile(concave, calcLinearProfileDuration(concave, max));
    }

    private static Time calcLinearProfileDuration(Profile concave, MotionParameters max) {
        double v = UnitConversion.toCentimetersPerSecond(max.velocity());
        double a = UnitConversion.toCentimetersPerSecondPerSecond(max.acceleration());
        double j = UnitConversion.toCentimetersPerSecondPerSecondPerSecond(max.jerk());
        double linearEndVelocity = v - Math.pow(a, 2)/(2 * j);

        return Time.seconds((linearEndVelocity - UnitConversion.toCentimetersPerSecond(concave.endParameters().velocity()))/a);
    }
}
