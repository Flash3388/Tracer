package tracer.profiles;

import calculus.functions.polynomial.Linear;
import calculus.functions.polynomial.PolynomialFunction;
import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionState;
import tracer.profiles.base.BaseProfile;
import tracer.profiles.base.BasicProfile;
import tracer.profiles.base.Profile;
import util.TimeConversion;

public class LinearVelocityProfile extends BasicProfile {
    private final Time duration;
    private final PolynomialFunction distance;
    private final PolynomialFunction velocity;

    public LinearVelocityProfile(Time duration) {
        this(new ProfileState(), duration);
    }

    public LinearVelocityProfile(Profile prevProfile, Time duration) {
        this(prevProfile.finalState(), duration);
    }

    public LinearVelocityProfile(ProfileState initialState, Time duration) {
        super(initialState);
        this.duration = duration;

        velocity = new Linear(initialState.acceleration(), 0);
        distance = new PolynomialFunction(initialState.acceleration()/2, initialState.velocity(), 0.0);
    }

    public static LinearVelocityProfile forSCurve(ConcaveProfile concave, MotionState target) {
        MotionState finalStateOnConcave = concave.finalState().motionState();
        double velocityDelta = target.sub(finalStateOnConcave).velocity();

        return new LinearVelocityProfile(Time.seconds(velocityDelta/target.acceleration()));
    }

    @Override
    public Time duration() {
        return duration;
    }

    @Override
    public BaseProfile repositionProfile(ProfileState newInitialState) {
        return new LinearVelocityProfile(newInitialState, duration);
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
