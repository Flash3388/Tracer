package tracer.profiles;

import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionParameters;
import tracer.trajectory.FunctionalTrajectory;

import java.util.ArrayList;
import java.util.List;

public class ProfileFactory {
    public static Profile createTrajectoryProfile(Profile prevProfile, MotionParameters max, FunctionalTrajectory functionalTrajectory) {
        return createTrajectoryProfile(prevProfile.initialDistance(), prevProfile.endParameters().velocity(), max, prevProfile.start(), functionalTrajectory);
    }

    public static Profile createTrajectoryProfile(double initialDistance, double initialVelocity, MotionParameters max, Time startTime, FunctionalTrajectory functionalTrajectory) {
        List<Profile> profiles = new ArrayList<>();

        profiles.add(createStartSCurve(initialDistance, initialVelocity, max, startTime));
        profiles.add(createConstantVelocityProfile(profiles.get(0), functionalTrajectory));
        profiles.add(createEndSCurve(profiles.get(1), max));

        return new ComplexProfile(initialDistance, MotionParameters.stop(), startTime, calcTrajectoryDuration(max, functionalTrajectory.length()), profiles);
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

    private static Time calcTrajectoryDuration(MotionParameters max, double trajectoryLength) {
        Profile sCurve = createSCurve(0, 0, max, Time.seconds(0));

        return sCurve.duration().add(calcConstantVelocityDuration(sCurve, trajectoryLength)).add(sCurve.duration());
    }

    private static Profile createStartSCurve(double initialDistance, double initialVelocity, MotionParameters max, Time startTime) {
        return createSCurve(initialDistance, initialVelocity, max, startTime);
    }

    private static ConstantVelocityProfile createConstantVelocityProfile(Profile prevProfile, FunctionalTrajectory functionalTrajectory) {
        return new ConstantVelocityProfile(prevProfile, calcConstantVelocityDuration(prevProfile, functionalTrajectory.length()));
    }

    private static Time calcConstantVelocityDuration(Profile sCurve, double trajectoryLength) {
        return Time.seconds((trajectoryLength - 2 * sCurve.length()) / sCurve.endParameters().velocity());
    }

    private static Profile createEndSCurve(Profile prevProfile, MotionParameters max) {
        MotionParameters reversedMotionParameters = new MotionParameters(0, -max.acceleration(), -max.jerk());

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
