package tracer.profiles;

import calculus.functions.polynomial.Cubic;
import calculus.functions.polynomial.Linear;
import calculus.functions.polynomial.PolynomialFunction;
import calculus.functions.polynomial.Quadratic;
import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionParameters;
import util.TimeConversion;

public class ConvexProfile extends Profile {
    private final double targetJerk;

    private final PolynomialFunction acceleration;
    private final PolynomialFunction velocity;
    private final PolynomialFunction distance;

    public ConvexProfile(Profile prevProfile, MotionParameters target) {
        this(prevProfile.absoluteLength(), prevProfile.finalParameters().velocity(), prevProfile.finalParameters().acceleration(), target, prevProfile.finalTiming());
    }

    public ConvexProfile(double initialDistance, double initialVelocity, double initialAcceleration, MotionParameters target, Time startTime) {
        super(initialDistance, MotionParameters.linearVelocity(initialVelocity, initialAcceleration), startTime, calcDuration(target));

        double targetAcceleration = target.acceleration();
        targetJerk = -target.jerk();

        acceleration = new Linear(targetJerk, 0);
        velocity = new Quadratic(targetJerk/2, targetAcceleration, 0);
        distance = new Cubic(targetJerk/6, targetAcceleration /2, initialVelocity, 0);
    }

    @Override
    protected double relativeVelocityAt(Time t) {
        double timeInSeconds = TimeConversion.toSeconds(t);
        return velocity.applyAsDouble(timeInSeconds);
    }

    @Override
    protected double relativeDistanceAt(Time t) {
        double timeInSeconds = TimeConversion.toSeconds(t);
        return distance.applyAsDouble(timeInSeconds);
    }

    @Override
    protected double relativeAccelerationAt(Time t) {
        double timeInSeconds = TimeConversion.toSeconds(t);
        return acceleration.applyAsDouble(timeInSeconds);
    }

    @Override
    protected double relativeJerkAt(Time relativeTime) {
        return targetJerk;
    }

    private static Time calcDuration(MotionParameters target) {
        return Time.seconds(target.acceleration()/target.jerk());
    }
}
