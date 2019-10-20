package tracer.profiles;

import calculus.functions.polynomialFunctions.Linear;
import calculus.functions.polynomialFunctions.PolynomialFunction;
import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionParameters;
import tracer.motion.basic.Acceleration;
import tracer.motion.basic.Distance;
import tracer.motion.basic.Jerk;
import tracer.motion.basic.Velocity;
import tracer.motion.basic.units.UnitConversion;

public class ConstantVelocityProfile extends Profile {
    private final PolynomialFunction distance;

    public ConstantVelocityProfile(Profile prevProfile, Time duration) {
        this(prevProfile.absoluteLength(), prevProfile.endParameters().velocity(), prevProfile.end(), duration);
    }

    public ConstantVelocityProfile(Distance initialDistance, Velocity initialVelocity, Time startTime, Time duration) {
        super(initialDistance, MotionParameters.constantVelocity(initialVelocity), startTime, duration);

        distance = new Linear(UnitConversion.toCentimetersPerSecond(initialVelocity), 0);
    }

    @Override
    protected Velocity relativeVelocityAt(Time t) {
        return Velocity.centimetersPerSecond(0);
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
