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

public class ConcaveProfile extends Profile {
    private final Jerk targetJerk;

    private final PolynomialFunction acceleration;
    private final PolynomialFunction velocity;
    private final PolynomialFunction distance;

    public ConcaveProfile(Distance initialDistance, Velocity initialVelocity, MotionParameters target, Time startTime) {
        super(initialDistance, MotionParameters.constantVelocity(initialVelocity), startTime, calcDuration(target));

        targetJerk = target.jerk();
        double j = UnitConversion.toCentimetersPerSecondPerSecondPerSecond(targetJerk);
        double v = UnitConversion.toCentimetersPerSecond(initialVelocity);

        acceleration = new Linear(j, 0);
        velocity = new Quadratic(j/2, 0, 0);
        distance = new Cubic(j/6, 0, v, 0);
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

    private static Time calcDuration(MotionParameters max) {
        double a = UnitConversion.toCentimetersPerSecondPerSecond(max.acceleration());
        double j = UnitConversion.toCentimetersPerSecondPerSecondPerSecond(max.jerk());

        return Time.seconds(a/ j);
    }
}
