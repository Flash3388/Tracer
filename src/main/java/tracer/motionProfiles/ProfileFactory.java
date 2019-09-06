package tracer.motionProfiles;

import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionParameters;
import tracer.trajectory.Trajectory;

import java.util.ArrayList;
import java.util.List;

public class ProfileFactory {
    public static Profile createTrajectoryProfile(Profile prevProfile, MotionParameters max, Trajectory trajectory) {
        return createTrajectoryProfile(prevProfile.initialDistance(), max, prevProfile.start(), trajectory);
    }

    public static Profile createTrajectoryProfile(double initialDistance, MotionParameters max, Time startTime, Trajectory trajectory) {
        List<Profile> profiles = new ArrayList<>();

        profiles.add(createStartSCurve(initialDistance, max, startTime));
        profiles.add(createConstantVelocityProfile(profiles.get(0), trajectory));
        profiles.add(createEndSCurve(profiles.get(1)));

        return new ComplexProfile(initialDistance, 0, 0, max, startTime, calcTrajectoryDuration(max, trajectory.length()), profiles);
    }

    public static Profile createSCurve(Profile prevProfile, MotionParameters max) {
        return createSCurve(prevProfile.absoluteLength(), prevProfile.finalVelocity(), max, prevProfile.end());
    }

    public static Profile createSCurve(double initialDistance, double initialVelocity, MotionParameters max, Time startTime) {
        List<Profile> profiles = new ArrayList<>();
        profiles.add(createConcaveProfile(initialDistance, initialVelocity, max, startTime));
        profiles.add(createLinearProfile(profiles.get(0)));
        profiles.add(createConvexProfile(max, profiles.get(1)));

        return new ComplexProfile(initialDistance, initialVelocity, 0, max, startTime, calcSCurveDuration(max, initialVelocity), profiles);
    }

    private static Time calcTrajectoryDuration(MotionParameters max, double trajectoryLength) {
        Profile sCurve = createSCurve(0, 0, max, Time.seconds(0));

        return sCurve.duration().add(calcConstantVelocityDuration(sCurve, max, trajectoryLength)).add(sCurve.duration());
    }

    private static Profile createStartSCurve(double initialDistance, MotionParameters max, Time startTime) {
        return createSCurve(initialDistance, 0, max, startTime);
    }

    private static ConstantVelocityProfile createConstantVelocityProfile(Profile prevProfile, Trajectory trajectory) {
        return new ConstantVelocityProfile(prevProfile, calcConstantVelocityDuration(prevProfile, prevProfile.getMax(), trajectory.length()));
    }

    private static Time calcConstantVelocityDuration(Profile sCurve, MotionParameters max, double trajectoryLength) {
        return Time.seconds((trajectoryLength - 2 * sCurve.length()) / max.getVelocity());
    }

    private static Profile createEndSCurve(Profile prevProfile) {
        MotionParameters max = prevProfile.getMax();
        MotionParameters reversedMotionParameters = new MotionParameters(0, -max.getAcceleration(), -max.getJerk());

        return createSCurve(prevProfile, reversedMotionParameters);
    }

    private static Time calcSCurveDuration(MotionParameters max, double initialVelocity) {
        Profile concave = new ConcaveProfile(0, initialVelocity, max, Time.seconds(0));

        return concave.end().add(calcLinearProfileDuration(concave, max)).add(concave.end());
    }

    private static ConcaveProfile createConcaveProfile(double initialDistance, double initialVelocity, MotionParameters max, Time startTime) {
        return new ConcaveProfile(initialDistance, initialVelocity, max, startTime);
    }

    private static LinearVelocityProfile createLinearProfile(Profile concave) {
        return new LinearVelocityProfile(concave, concave.getMax(), calcLinearProfileDuration(concave, concave.getMax()));
    }

    private static Time calcLinearProfileDuration(Profile concave, MotionParameters max) {
        double linearEndVelocity = max.getVelocity() - Math.pow(max.getAcceleration(), 2)/(2 * max.getJerk());

        return Time.seconds((linearEndVelocity - concave.finalVelocity())/max.getAcceleration());
    }

    private static ConvexProfile createConvexProfile(MotionParameters max, Profile linear) {
        return new ConvexProfile(linear, max);
    }
}
