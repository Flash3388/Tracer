package tracer.profiles;

import calculus.functions.polynomialFunctions.Cubic;
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

public class ConvexProfile extends Profile {
    private final Jerk targetJerk;

    private final PolynomialFunction acceleration;
    private final PolynomialFunction velocity;
    private final PolynomialFunction distance;

    public ConvexProfile(Profile prevProfile, MotionParameters target) {
        this(prevProfile.absoluteLength(), prevProfile.endParameters().velocity(), prevProfile.endParameters().acceleration(), target, prevProfile.end());
    }

    public ConvexProfile(Distance initialDistance, Velocity initialVelocity, Acceleration initialAcceleration, MotionParameters target, Time startTime) {
        super(initialDistance, MotionParameters.linearVelocity(initialVelocity, initialAcceleration), startTime, calcDuration(target));

        targetJerk = target.jerk().reverse();
        double v = UnitConversion.toCentimetersPerSecond(target.velocity());
        double a = UnitConversion.toCentimetersPerSecondPerSecond(target.acceleration());
        double j = UnitConversion.toCentimetersPerSecondPerSecondPerSecond(target.jerk());

        acceleration = new Linear(j, 0);
        velocity = new Quadratic(j/2, a, 0);
        distance = new Cubic(j/6, a/2, v,0);
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
        double timeInSeconds = UnitConversion.toSeconds(t);
        return Acceleration.centimetersPerSecondPerSecond(acceleration.apply(timeInSeconds));
    }

    @Override
    protected Jerk relativeJerkAt(Time relativeTime) {
        return targetJerk;
    }

    private static Time calcDuration(MotionParameters target) {
        double a = UnitConversion.toCentimetersPerSecondPerSecond(target.acceleration());
        double j = UnitConversion.toCentimetersPerSecondPerSecondPerSecond(target.jerk());

        return Time.seconds(a/ j);
    }
}
