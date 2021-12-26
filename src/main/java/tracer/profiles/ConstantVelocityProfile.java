package tracer.profiles;

import calculus.functions.polynomial.Linear;
import calculus.functions.polynomial.PolynomialFunction;
import calculus.trajectories.Trajectory;
import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionState;
import tracer.profiles.base.BaseProfile;
import tracer.profiles.base.BasicProfile;
import tracer.profiles.base.Profile;
import util.TimeConversion;

public class ConstantVelocityProfile extends BasicProfile {
    private final PolynomialFunction distance;
    private final Time duration;

    public ConstantVelocityProfile(Time duration) {
        this(new ProfileState(), duration);
    }

    public ConstantVelocityProfile(Profile prevProfile, Time duration) {
        this(prevProfile.finalState(), duration);
    }

    public ConstantVelocityProfile(ProfileState initialState, Time duration) {
        super(initialState);
        this.duration = duration;

        distance = new Linear(initialState.velocity(), 0);
    }

    public static ConstantVelocityProfile forTrajectory(Trajectory trajectory, MotionState target) {
        double distancePassedIn2SCurves = ProfileFactory.distancePassedInTwoSCurves(target);
        double distanceToBePassed = trajectory.end() - Math.abs(distancePassedIn2SCurves);

        return new ConstantVelocityProfile(Time.seconds(distanceToBePassed/Math.abs(target.velocity())));
    }

    @Override
    public Time duration() {
        return duration;
    }

    @Override
    public BaseProfile repositionProfile(ProfileState newInitialState) {
        return new ConstantVelocityProfile(newInitialState, duration);
    }

    @Override
    protected double relativeDistanceAt(Time relativeTime) {
        double timeInSeconds = TimeConversion.toSeconds(relativeTime);
        return distance.applyAsDouble(timeInSeconds);
    }

    @Override
    protected double relativeVelocityAt(Time relativeTime) {
        return 0;
    }

    @Override
    protected double relativeAccelerationAt(Time relativeTime) {
        return 0;
    }

    @Override
    protected double relativeJerkAt(Time relativeTime) {
        return 0;
    }
}
