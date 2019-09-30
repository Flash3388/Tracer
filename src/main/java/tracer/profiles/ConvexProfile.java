package tracer.profiles;

import calculus.functions.polynomialFunctions.Cubic;
import calculus.functions.polynomialFunctions.Linear;
import calculus.functions.PolynomialFunction;
import calculus.functions.polynomialFunctions.Quadratic;
import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionParameters;
import util.TimeConversion;

public class ConvexProfile extends Profile {
    private final double targetJerk;

    private final PolynomialFunction acceleration;
    private final PolynomialFunction velocity;
    private final PolynomialFunction distance;

    public ConvexProfile(Profile prevProfile, MotionParameters target) {
        this(prevProfile.absoluteLength(), prevProfile.endParameters().velocity(), prevProfile.endParameters().acceleration(), target, prevProfile.end());
    }

    public ConvexProfile(double initialDistance, double initialVelocity, double initialAcceleration, MotionParameters target, Time startTime) {
        super(initialDistance, MotionParameters.linearVelocity(initialVelocity, initialAcceleration), startTime, calcDuration(target));

        double targetAcceleration = target.acceleration();
        targetJerk = -target.jerk();

        acceleration = Linear.fromConstants(targetJerk, 0);
        velocity = Quadratic.fromConstants(targetJerk/2, targetAcceleration, 0);
        distance = Cubic.fromConstants(targetJerk/6, targetAcceleration /2, initialVelocity, 0);
    }

    private static Time calcDuration(MotionParameters target) {
        return Time.seconds(target.acceleration()/target.jerk());
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
