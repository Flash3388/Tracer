package tracer.profiles;

import calculus.functions.polynomial.Linear;
import calculus.functions.polynomial.PolynomialFunction;
import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionState;
import util.TimeConversion;

public class ConcaveProfile extends BasicProfile {
    private final PolynomialFunction distance;
    private final PolynomialFunction velocity;
    private final PolynomialFunction acceleration;
    private final MotionState target;

    public ConcaveProfile(MotionState target) {
        this(new ProfileState(), target);
    }

    public ConcaveProfile(Profile prevProfile, MotionState target) {
        this(prevProfile.finalState(), target);
    }

    public ConcaveProfile(ProfileState initialState, MotionState target) {
        super(initialState);
        this.target = target;

        acceleration = new Linear(target.jerk(), 0);
        velocity = new PolynomialFunction(target.jerk()/2, 0.0, 0.0);
        distance = new PolynomialFunction(target.jerk()/6, 0.0, initialState.velocity(), 0.0);
    }

    @Override
    protected double relativeDistanceAt(Time relativeTime) {
        double timeInSeconds = TimeConversion.toSeconds(relativeTime);
        return distance.applyAsDouble(timeInSeconds);
    }

    @Override
    protected double relativeVelocityAt(Time relativeTime) {
        double timeInSeconds = TimeConversion.toSeconds(relativeTime);
        return velocity.applyAsDouble(timeInSeconds);
    }

    @Override
    protected double relativeAccelerationAt(Time relativeTime) {
        double timeInSeconds = TimeConversion.toSeconds(relativeTime);
        return acceleration.applyAsDouble(timeInSeconds);
    }

    @Override
    protected double relativeJerkAt(Time relativeTime) {
        return target.jerk();
    }

    @Override
    public Time duration() {
        return Time.seconds(target.acceleration()/target.jerk());
    }
}
