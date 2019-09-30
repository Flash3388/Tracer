package tracer.profiles;

import calculus.functions.polynomialFunctions.Cubic;
import calculus.functions.polynomialFunctions.Linear;
import calculus.functions.PolynomialFunction;
import calculus.functions.polynomialFunctions.Quadratic;
import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionParameters;
import util.TimeConversion;

public class ConcaveProfile extends Profile {
    private final double targetJerk;

    private final PolynomialFunction acceleration;
    private final PolynomialFunction velocity;
    private final PolynomialFunction distance;

    public ConcaveProfile(double initialDistance, double initialVelocity, MotionParameters target, Time startTime) {
        super(initialDistance, MotionParameters.constantVelocity(initialVelocity), startTime, calcDuration(target));

        targetJerk = target.jerk();

        acceleration = Linear.fromConstants(targetJerk, 0);
        velocity = Quadratic.fromConstants(targetJerk/2, 0, 0);
        distance = Cubic.fromConstants(targetJerk/6, 0, initialVelocity, 0);
    }

    private static Time calcDuration(MotionParameters max) {
        return Time.seconds(max.acceleration()/max.jerk());
    }

    @Override
    protected double relativeVelocityAt(Time t) {
        double timeInSeconds = TimeConversion.toSeconds(t);
        return velocity.at(timeInSeconds);
    }

    @Override
    protected double relativeDistanceAt(Time t) {
        double timeInSeconds = TimeConversion.toSeconds(t);
        return distance.at(timeInSeconds);
    }

    @Override
    protected double relativeAccelerationAt(Time t) {
        double timeInSeconds = TimeConversion.toSeconds(t);
        return acceleration.at(timeInSeconds);
    }

    @Override
    protected double relativeJerkAt(Time relativeTime) {
        return targetJerk;
    }
}
