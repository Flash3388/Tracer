package tracer.profiles;

import calculus.functions.polynomial.Linear;
import calculus.functions.polynomial.PolynomialFunction;
import calculus.trajectories.Trajectory;
import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionState;
import tracer.profiles.base.BasicProfile;
import tracer.profiles.base.Profile;
import tracer.units.distance.Distance;
import tracer.units.morion.Acceleration;
import tracer.units.morion.Jerk;
import tracer.units.morion.Velocity;
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

        distance = new Linear(state.velocity().valueAsMetersPerSecond(), 0);
    }

    public static ConstantVelocityProfile continuation(Profile prevProfile, Time duration) {
        return continuation(prevProfile.finalState(), duration);
    }

    public static ConstantVelocityProfile continuation(ProfileState initialState, Time duration) {
        return new ConstantVelocityProfile(initialState, initialState.parameters(), duration);
    }

    public static ConstantVelocityProfile forTrajectory(Trajectory trajectory, MotionState target) {
        Distance distancePassedIn2SCurves = ProfileFactory.distancePassedInTwoSCurves(target);
        double distanceToBePassed = trajectory.end() - distancePassedIn2SCurves.valueAsMeters();

        return new ConstantVelocityProfile(target, Time.seconds(distanceToBePassed/target.velocity().valueAsMetersPerSecond()));
    }

    @Override
    public Time duration() {
        return duration;
    }

    @Override
    protected Distance relativeDistanceAt(Time relativeTime) {
        double timeInSeconds = TimeConversion.toSeconds(relativeTime);
        return Distance.meters(distance.applyAsDouble(timeInSeconds));
    }

    @Override
    protected Velocity relativeVelocityAt(Time relativeTime) {
        return Velocity.metersPerSecond(0);
    }

    @Override
    protected Acceleration relativeAccelerationAt(Time relativeTime) {
        return Acceleration.metersPerSecondSquared(0);
    }

    @Override
    protected Jerk relativeJerkAt(Time relativeTime) {
        return Jerk.metersPerSecondCubed(0);
    }
}
