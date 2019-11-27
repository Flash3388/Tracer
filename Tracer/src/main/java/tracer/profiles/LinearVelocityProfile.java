package tracer.profiles;

import calculus.functions.polynomial.Linear;
import calculus.functions.polynomial.PolynomialFunction;
import calculus.functions.polynomial.Quadratic;
import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionParameters;
import util.TimeConversion;

public class LinearVelocityProfile extends Profile {
    private final PolynomialFunction velocity;
    private final PolynomialFunction distance;

    public LinearVelocityProfile(Profile prevProfile, Time duration) {
        this(prevProfile.absoluteLength(), prevProfile.finalParameters().velocity(), prevProfile.finalParameters().acceleration(), prevProfile.finalTimestamp(), duration);
    }

    public LinearVelocityProfile(double initialDistance, double initialVelocity, double initialAcceleration, Time startTime, Time duration) {
        super(initialDistance, MotionParameters.linearVelocity(initialVelocity, initialAcceleration), startTime, duration);

        velocity = new Linear(initialAcceleration, 0);
        distance = new Quadratic(initialAcceleration/2, initialVelocity, 0);
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
        return 0;
    }

    @Override
    protected double relativeJerkAt(Time relativeTime) {
        return 0;
    }
}
