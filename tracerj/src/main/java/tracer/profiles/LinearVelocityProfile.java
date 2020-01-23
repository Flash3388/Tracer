package tracer.profiles;

import calculus.functions.polynomial.Linear;
import calculus.functions.polynomial.PolynomialFunction;
import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionState;
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

        velocity = new Linear(state.acceleration(), 0);
        distance = new PolynomialFunction(state.acceleration()/2, state.velocity(), 0.0);
    }

    public static LinearVelocityProfile continuation(Profile prevProfile, Time duration) {
        return continuation(prevProfile.finalState(), duration);
    }

    public static LinearVelocityProfile continuation(ProfileState initialState, Time duration) {
        return new LinearVelocityProfile(initialState, initialState.parameters(), duration);
    }

    public static LinearVelocityProfile forSCurve(MotionState target) {
        MotionState finalStateOnConcave = new ConcaveProfile(target).finalState().parameters();
        double velocityDelta = target.sub(finalStateOnConcave).velocity();
        MotionState initialState = new ConcaveProfile(target).finalState().parameters();

        return new LinearVelocityProfile(initialState, Time.seconds(velocityDelta/target.acceleration()));
    }

    @Override
    public Time duration() {
        return duration;
    }

    @Override
    protected double relativeDistanceAt(Time t) {
        double timeInSeconds = TimeConversion.toSeconds(t);
        return distance.applyAsDouble(timeInSeconds);
    }

    @Override
    protected double relativeVelocityAt(Time t) {
        double timeInSeconds = TimeConversion.toSeconds(t);
        return velocity.applyAsDouble(timeInSeconds);
    }

    @Override
    protected double relativeAccelerationAt(Time t) {
        return 0;
    }

    @Override
    protected double relativeJerkAt(Time relativeTime) {
        return 0;
    }
}
