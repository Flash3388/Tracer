package tracer.profiles;

import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionParameters;
import tracer.motion.basic.Acceleration;
import tracer.motion.basic.Distance;
import tracer.motion.basic.Jerk;
import tracer.motion.basic.Velocity;

import java.util.List;
import java.util.stream.Collectors;

public class ComplexProfile extends Profile {
    private final List<Profile> profiles;

    public ComplexProfile(Profile prevProfile, List<Profile> profiles) {
        this(prevProfile.absoluteLength(), prevProfile.endParameters(), prevProfile.end(), profiles);
    }

    public ComplexProfile(Distance initialDistance, MotionParameters initial, Time startTime, List<Profile> profiles) {
        super(initialDistance, initial, startTime, profiles.get(profiles.size()-1).end().sub(profiles.get(0).start()));
        this.profiles = profiles;
    }

    @Override
    protected Velocity relativeVelocityAt(Time relativeTime) {
        Profile correspondingProfile = correspondingProfile(relativeTime);
        return correspondingProfile.relativeVelocityAt(relativeTime.sub(correspondingProfile.start().sub(start())))
                .add(correspondingProfile.initialParameters().velocity()
                        .sub(initialParameters().velocity()));
    }

    @Override
    protected Distance relativeDistanceAt(Time relativeTime) {
        Profile correspondingProfile = correspondingProfile(relativeTime);
        return correspondingProfile.relativeDistanceAt(relativeTime.sub(correspondingProfile.start().sub(start())))
                .add(correspondingProfile.initialDistance()
                        .sub(initialDistance()));
    }

    @Override
    protected Acceleration relativeAccelerationAt(Time relativeTime) {
        Profile correspondingProfile = correspondingProfile(relativeTime);
        return correspondingProfile.relativeAccelerationAt(relativeTime.sub(correspondingProfile.start().sub(start())))
                .add(correspondingProfile.initialParameters().acceleration()
                        .sub(initialParameters().acceleration()));
    }

    @Override
    protected Jerk relativeJerkAt(Time relativeTime) {
        Profile correspondingProfile = correspondingProfile(relativeTime);
        return correspondingProfile.relativeJerkAt(relativeTime.sub(correspondingProfile.start().sub(start())))
                .add(correspondingProfile.initialParameters().jerk()
                        .sub(initialParameters().jerk()));
    }

    private Profile correspondingProfile(Time t) {
        return profiles.stream()
                .filter(profile -> profile.isCorresponding(t.add(start())))
                .collect(Collectors.toList()).get(0);
    }
}
