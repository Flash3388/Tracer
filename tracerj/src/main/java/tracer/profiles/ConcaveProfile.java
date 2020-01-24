package tracer.profiles;

import calculus.functions.polynomial.Linear;
import calculus.functions.polynomial.PolynomialFunction;
import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionState;
import tracer.units.distance.Distance;
import tracer.units.morion.Acceleration;
import tracer.units.morion.Jerk;
import tracer.units.morion.Velocity;
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
        double jerkMeters = target.jerk().valueAsMetersPerSecondCubed();

        acceleration = new Linear(jerkMeters, 0);
        velocity = new PolynomialFunction(jerkMeters/2, 0.0, 0.0);
        distance = new PolynomialFunction(jerkMeters/6, 0.0, initialState.velocity().valueAsMetersPerSecond(), 0.0);
    }

    @Override
    protected Distance relativeDistanceAt(Time relativeTime) {
        double timeInSeconds = TimeConversion.toSeconds(relativeTime);
        return Distance.meters(distance.applyAsDouble(timeInSeconds));
    }

    @Override
    protected Velocity relativeVelocityAt(Time relativeTime) {
        double timeInSeconds = TimeConversion.toSeconds(relativeTime);
        return Velocity.metersPerSecond(velocity.applyAsDouble(timeInSeconds));
    }

    @Override
    protected Acceleration relativeAccelerationAt(Time relativeTime) {
        double timeInSeconds = TimeConversion.toSeconds(relativeTime);
        return Acceleration.metersPerSecondSquared(acceleration.applyAsDouble(timeInSeconds));
    }

    @Override
    protected Jerk relativeJerkAt(Time relativeTime) {
        return target.jerk();
    }

    @Override
    public Time duration() {
        return Time.seconds(target.acceleration().valueAsMetersPerSecondSquared()/target.jerk().valueAsMetersPerSecondCubed());
    }
}
