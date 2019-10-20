package tracer.profiles;

import calculus.functions.polynomialFunctions.Linear;
import calculus.functions.polynomialFunctions.PolynomialFunction;
import calculus.functions.polynomialFunctions.Quadratic;
import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionParameters;
import tracer.motion.basic.Acceleration;
import tracer.motion.basic.Distance;
import tracer.motion.basic.Jerk;
import tracer.motion.basic.Velocity;
import tracer.motion.basic.units.UnitConversion;

public class LinearVelocityProfile extends Profile {
    private final PolynomialFunction velocity;
    private final PolynomialFunction distance;

    public LinearVelocityProfile(Profile prevProfile, Time duration) {
        this(prevProfile.absoluteLength(), prevProfile.endParameters().velocity(), prevProfile.endParameters().acceleration(), prevProfile.end(), duration);
    }

    public LinearVelocityProfile(Distance initialDistance, Velocity initialVelocity, Acceleration initialAcceleration, Time startTime, Time duration) {
        super(initialDistance, MotionParameters.linearVelocity(initialVelocity, initialAcceleration), startTime, duration);

        velocity = new Linear(UnitConversion.toCentimetersPerSecondPerSecond(initialAcceleration), 0);
        distance = new Quadratic(UnitConversion.toCentimetersPerSecondPerSecond(initialAcceleration)/2,
                UnitConversion.toCentimetersPerSecond(initialVelocity), 0);
    }

    @Override
    protected Velocity relativeVelocityAt(Time t) {
        double timeInSeconds = UnitConversion.toSeconds(t);
        return Velocity.centimetersPerSecond(velocity.apply(timeInSeconds));
    }

    @Override
    protected Distance relativeDistanceAt(Time t) {
        double timeInSeconds = UnitConversion.toSeconds(t);
        return Distance.centimeters(distance.apply(timeInSeconds));
    }

    @Override
    protected Acceleration relativeAccelerationAt(Time t) {
        return Acceleration.centimetersPerSecondPerSecond(0);
    }

    @Override
    protected Jerk relativeJerkAt(Time relativeTime) {
        return Jerk.centimetersPerSecondPerSecondPerSecond(0);
    }
}
