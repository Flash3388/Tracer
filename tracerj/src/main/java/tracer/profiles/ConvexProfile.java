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

public class ConvexProfile extends BasicProfile {
    private final PolynomialFunction distance;
    private final PolynomialFunction velocity;
    private final PolynomialFunction acceleration;
    private final MotionState target;

    public ConvexProfile(MotionState target) {
        this(new ProfileState(), target);
    }

    public ConvexProfile(Profile prevProfile, MotionState target) {
        this(prevProfile.finalState(), target);
    }

    public ConvexProfile(ProfileState initialState, MotionState target) {
        super(initialState);
        this.target = target;
        double jerkMeters = target.jerk().mul(-1).valueAsMetersPerSecondCubed();

        acceleration = new Linear(jerkMeters, 0);
        velocity = new PolynomialFunction(jerkMeters/2, target.acceleration().valueAsMetersPerSecondSquared(), 0.0);
        distance = new PolynomialFunction(jerkMeters/6, target.acceleration().div(2).valueAsMetersPerSecondSquared(), initialState.velocity().valueAsMetersPerSecond(), 0.0);
    }

    @Override
    public Time duration() {
        return target.acceleration().div(target.jerk());
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
        return Jerk.metersPerSecondCubed(0).sub(target.jerk());
    }
}
