package tracer.profiles.base;

import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionState;
import tracer.profiles.ProfileState;
import tracer.units.distance.Distance;
import tracer.units.morion.Acceleration;
import tracer.units.morion.Jerk;
import tracer.units.morion.Velocity;

public abstract class BasicProfile extends BaseProfile {
    public BasicProfile(Profile prevProfile) {
        this(prevProfile.finalState());
    }

    public BasicProfile(ProfileState initialState) {
        super(initialState);
    }

    @Override
    public String toString() {
        return String.format("from: %s, to: %s",initialState(), finalState());
    }

    @Override
    protected ProfileState relativeProfileState(Time relativeTime) {
        return new ProfileState(relativeDistanceAt(relativeTime), relativeMotionState(relativeTime), relativeTime);
    }

    protected abstract Distance relativeDistanceAt(Time relativeTime);
    protected abstract Velocity relativeVelocityAt(Time relativeTime);
    protected abstract Acceleration relativeAccelerationAt(Time relativeTime);
    protected abstract Jerk relativeJerkAt(Time relativeTime);

    private MotionState relativeMotionState(Time relativeTime) {
        return new MotionState(relativeVelocityAt(relativeTime), relativeAccelerationAt(relativeTime), relativeJerkAt(relativeTime));
    }
}
