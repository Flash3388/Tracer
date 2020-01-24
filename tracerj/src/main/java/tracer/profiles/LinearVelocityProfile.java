package tracer.profiles;

import calculus.functions.polynomial.Linear;
import calculus.functions.polynomial.PolynomialFunction;
import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionState;
import tracer.profiles.base.BasicProfile;
import tracer.profiles.base.Profile;
import tracer.units.distance.Distance;
import tracer.units.morion.Acceleration;
import tracer.units.morion.Jerk;
import tracer.units.morion.Velocity;
import util.TimeConversion;

public class LinearVelocityProfile extends BasicProfile {
    private final Time duration;
    private final PolynomialFunction distance;
    private final PolynomialFunction velocity;

    public LinearVelocityProfile(MotionState state, Time duration) {
        this(new ProfileState(), state, duration);
    }

    public LinearVelocityProfile(Profile prevProfile, MotionState state, Time duration) {
        this(prevProfile.finalState(), state, duration);
    }

    public LinearVelocityProfile(ProfileState initialState, MotionState state, Time duration) {
        super(initialState);
        this.duration = duration;

        velocity = new Linear(state.acceleration().valueAsMetersPerSecondSquared(), 0);
        distance = new PolynomialFunction(state.acceleration().div(2).valueAsMetersPerSecondSquared(), state.velocity().valueAsMetersPerSecond(), 0.0);
    }

    public static LinearVelocityProfile continuation(Profile prevProfile, Time duration) {
        return continuation(prevProfile.finalState(), duration);
    }

    public static LinearVelocityProfile continuation(ProfileState initialState, Time duration) {
        return new LinearVelocityProfile(initialState, initialState.parameters(), duration);
    }

    public static LinearVelocityProfile forSCurve(MotionState target) {
        MotionState finalStateOnConcave = new ConcaveProfile(target).finalState().parameters();
        Velocity velocityDelta = target.sub(finalStateOnConcave).velocity();

        return new LinearVelocityProfile(finalStateOnConcave, velocityDelta.div(target.acceleration()));
    }

    @Override
    public Time duration() {
        return duration;
    }

    @Override
    protected Distance relativeDistanceAt(Time t) {
        double timeInSeconds = TimeConversion.toSeconds(t);
        return Distance.meters(distance.applyAsDouble(timeInSeconds));
    }

    @Override
    protected Velocity relativeVelocityAt(Time t) {
        double timeInSeconds = TimeConversion.toSeconds(t);
        return Velocity.metersPerSecond(velocity.applyAsDouble(timeInSeconds));
    }

    @Override
    protected Acceleration relativeAccelerationAt(Time t) {
        return Acceleration.metersPerSecondSquared(0);
    }

    @Override
    protected Jerk relativeJerkAt(Time relativeTime) {
        return Jerk.metersPerSecondCubed(0);
    }
}
