package tracer.profiles;

import calculus.functions.Linear;
import calculus.functions.PolynomialFunction;
import calculus.functions.Quadratic;
import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionParameters;
import util.TimeConversion;

public class LinearVelocityProfile extends Profile {
    private final PolynomialFunction velocity;
    private final PolynomialFunction distance;

    public LinearVelocityProfile(Profile prevProfile, Time duration) {
        this(prevProfile.absoluteLength(), prevProfile.endParameters().velocity(), prevProfile.endParameters().acceleration(), prevProfile.end(), duration);
    }

    public LinearVelocityProfile(double initialDistance, double initialVelocity, double initialAcceleration, Time startTime, Time duration) {
        super(initialDistance, MotionParameters.linearVelocity(initialVelocity, initialAcceleration), startTime, duration);

        velocity = Linear.fromConstants(initialAcceleration, 0);
        distance = Quadratic.fromConstants(initialAcceleration/2, initialVelocity, 0);
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
        return 0;
    }

    @Override
    protected double relativeJerkAt(Time relativeTime) {
        return 0;
    }
}
