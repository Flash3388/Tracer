package tracer.profiles;

import calculus.trajectories.Trajectory;
import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionState;
import tracer.profiles.base.Profile;

public class ProfileFactory {
    public static Profile createTrajectoryProfile(MotionState max, Trajectory trajectory) {
        return createTrajectoryProfile(new ProfileState(), max, trajectory);
    }

    public static Profile createTrajectoryProfile(Profile prevProfile, MotionState max, Trajectory trajectory) {
        return createTrajectoryProfile(prevProfile.finalState(), max, trajectory);
    }

    public static Profile createTrajectoryProfile(ProfileState initialState, MotionState max, Trajectory trajectory) {
        checkVelocity(max, trajectory.end());

        return createSCurve(initialState, max).then(ConstantVelocityProfile.forTrajectory(trajectory, max)).then(createEndSCurve(max));
    }

    public static Profile createSCurve(MotionState max) {
        return createSCurve(new ProfileState(), max);
    }

    public static Profile createSCurve(Profile prevProfile, MotionState max) {
        return createSCurve(prevProfile.finalState(), max);
    }

    public static Profile createSCurve(ProfileState initialState, MotionState max) {
        return new ConcaveProfile(initialState, max).then(LinearVelocityProfile.forSCurve(new ConcaveProfile(initialState, max), max)).then(new ConvexProfile(max));
    }

    public static double distancePassedInTwoSCurves(MotionState target) {
        return createSCurve(target).deltaState().distance() * 2;
    }

    private static void checkVelocity(MotionState max, double targetDistance) {
        if (Math.abs(distancePassedInTwoSCurves(max)) > Math.abs(targetDistance))
            throw new IllegalArgumentException("Too small distance compared to maximum parameters");;
    }

    private static Profile createEndSCurve(MotionState max) {
        MotionState endState = new MotionState(0, -max.acceleration(), -max.jerk());
        return createSCurve(new ProfileState(0, MotionState.constantVelocity(max.velocity()), Time.milliseconds(0)), endState);
    }
}
