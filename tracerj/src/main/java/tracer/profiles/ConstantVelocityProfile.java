package tracer.profiles;

import calculus.functions.polynomial.Linear;
import calculus.functions.polynomial.PolynomialFunction;
import calculus.trajectories.Trajectory;
import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionState;
import util.TimeConversion;

public class ConstantVelocityProfile extends BasicProfile {
    private final PolynomialFunction distance;
    private final Time duration;

    public ConstantVelocityProfile(MotionState state, Time duration) {
        this(new ProfileState(), state, duration);
    }

    public ConstantVelocityProfile(Profile prevProfile, MotionState state, Time duration) {
        this(prevProfile.finalState(), state, duration);
    }

    public ConstantVelocityProfile(ProfileState initialState, MotionState state, Time duration) {
        super(initialState);
        this.duration = duration;

        distance = new Linear(state.velocity(), 0);
    }

    public static ConstantVelocityProfile continuation(Profile prevProfile, Time duration) {
        return continuation(prevProfile.finalState(), duration);
    }

    public static ConstantVelocityProfile continuation(ProfileState initialState, Time duration) {
        return new ConstantVelocityProfile(initialState, initialState.parameters(), duration);
    }

    public static ConstantVelocityProfile forTrajectory(Trajectory trajectory, MotionState target) {
        double distancePassedIn2SCurves = ProfileFactory.distancePassedInTwoSCurves(target);
        double distanceToBePassed = trajectory.end() - distancePassedIn2SCurves;

        return new ConstantVelocityProfile(target, Time.seconds(distanceToBePassed/target.velocity()));
    }

    @Override
    public Time duration() {
        return duration;
    }

    @Override
    protected double relativeDistanceAt(Time relativeTime) {
        double timeInSeconds = TimeConversion.toSeconds(relativeTime);
        return distance.applyAsDouble(timeInSeconds);
    }

    @Override
    protected double relativeVelocityAt(Time relativeTime) {
        return 0;
    }

    @Override
    protected double relativeAccelerationAt(Time relativeTime) {
        return 0;
    }

    @Override
    protected double relativeJerkAt(Time relativeTime) {
        return 0;
    }
}
