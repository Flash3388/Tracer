package com.flash3388.tracer.profiles;

import com.flash3388.tracer.calculus.functions.polynomial.Linear;
import com.flash3388.tracer.calculus.functions.polynomial.PolynomialFunction;
import com.flash3388.tracer.motion.MotionState;
import com.flash3388.tracer.util.TimeConversion;
import com.flash3388.flashlib.time.Time;
import com.flash3388.tracer.profiles.base.BaseProfile;
import com.flash3388.tracer.profiles.base.BasicProfile;
import com.flash3388.tracer.profiles.base.Profile;

public class LinearVelocityProfile extends BasicProfile {
    private final Time duration;
    private final PolynomialFunction distance;
    private final PolynomialFunction velocity;

    public LinearVelocityProfile(Time duration) {
        this(new ProfileState(), duration);
    }

    public LinearVelocityProfile(Profile prevProfile, Time duration) {
        this(prevProfile.finalState(), duration);
    }

    public LinearVelocityProfile(ProfileState initialState, Time duration) {
        super(initialState);
        this.duration = duration;

        velocity = new Linear(initialState.acceleration(), 0);
        distance = new PolynomialFunction(initialState.acceleration()/2, initialState.velocity(), 0.0);
    }

    public static LinearVelocityProfile forSCurve(ConcaveProfile concave, MotionState target) {
        MotionState finalStateOnConcave = concave.finalState().motionState();
        double velocityDelta = target.sub(finalStateOnConcave).velocity();

        return new LinearVelocityProfile(Time.seconds(velocityDelta/target.acceleration()));
    }

    @Override
    public Time duration() {
        return duration;
    }

    @Override
    public BaseProfile repositionProfile(ProfileState newInitialState) {
        return new LinearVelocityProfile(newInitialState, duration);
    }

    @Override
    protected double relativeDistanceAt(Time t) {
        double timeInSeconds = TimeConversion.toSeconds(t);
        return distance.applyAsDouble(timeInSeconds);
    }

    @Override
    protected double relativeVelocityAt(Time t) {
        double timeInSeconds = TimeConversion.toSeconds(t);
        return velocity.applyAsDouble(timeInSeconds);
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
