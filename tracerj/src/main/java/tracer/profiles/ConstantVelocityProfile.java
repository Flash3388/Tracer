package tracer.profiles;

import calculus.functions.polynomial.Linear;
import calculus.functions.polynomial.PolynomialFunction;
import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionParameters;
import util.TimeConversion;

public class ConstantVelocityProfile extends Profile {
    private final PolynomialFunction distance;

    public ConstantVelocityProfile(Profile prevProfile, Time duration) {
        this(prevProfile.absoluteLength(), prevProfile.finalParameters().velocity(), prevProfile.finalTimestamp(), duration);
    }

    public ConstantVelocityProfile(double initialDistance, double initialVelocity, Time startTime, Time duration) {
        super(initialDistance, MotionParameters.constantVelocity(initialVelocity), startTime, duration);

        distance = new Linear(initialVelocity, 0);
    }

    @Override
    protected double relativeVelocityAt(Time t) {
        return 0;
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
