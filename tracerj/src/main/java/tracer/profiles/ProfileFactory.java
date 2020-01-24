package tracer.profiles;

import calculus.trajectories.Trajectory;
import tracer.motion.MotionState;
import tracer.profiles.base.Profile;
import tracer.units.distance.Distance;

public class ProfileFactory {
    public static Profile createTrajectoryProfile(MotionState max, Trajectory trajectory) {
        return createTrajectoryProfile(new ProfileState(), max, trajectory);
    }

    public static Profile createTrajectoryProfile(Profile prevProfile, MotionState max, Trajectory trajectory) {
        return createTrajectoryProfile(prevProfile.finalState(), max, trajectory);
    }

    public static Profile createTrajectoryProfile(ProfileState initialState, MotionState max, Trajectory trajectory) {
        checkVelocity(max, Distance.meters(trajectory.end()));
        return createSCurve(initialState, max).then(ConstantVelocityProfile.forTrajectory(trajectory, max)).then(createSCurve(max.reverse()));
    }

    public static Profile createSCurve(MotionState max) {
        return createSCurve(new ProfileState(), max);
    }

    public static Profile createSCurve(Profile prevProfile, MotionState max) {
        return createSCurve(prevProfile.finalState(), max);
    }

    public static Profile createSCurve(ProfileState initialState, MotionState max) {
        return new ConcaveProfile(initialState, max).then(LinearVelocityProfile.forSCurve(max)).then(new ConvexProfile(max));
    }

    public static Distance distancePassedInTwoSCurves(MotionState target) {
        return createSCurve(target).deltaState().distance();
    }

    private static void checkVelocity(MotionState max, Distance targetDistance) {
        if (Math.abs(distancePassedInTwoSCurves(max).valueAsMeters()) > Math.abs(targetDistance.valueAsMeters()))
            throw new IllegalArgumentException("Too small distance compared to maximum parameters");;
    }
}
