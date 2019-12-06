package tracer.profiles;

import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionParameters;

import java.util.List;
import java.util.stream.Collectors;

public class ComplexProfile extends Profile {
    private final List<Profile> profiles;

    public ComplexProfile(Profile prevProfile, List<Profile> profiles) {
        this(prevProfile.absoluteLength(), prevProfile.finalParameters(), prevProfile.finalTimestamp(), profiles);
    }

    public ComplexProfile(double initialDistance, MotionParameters initial, Time startTime, List<Profile> profiles) {
        super(initialDistance, initial, startTime, profiles.get(profiles.size()-1).finalTimestamp().sub(profiles.get(0).initialTimestamp()));
        this.profiles = profiles;
    }

    @Override
    protected double relativeVelocityAt(Time relativeTime) {
        Profile correspondingProfile = correspondingProfile(relativeTime);
        return correspondingProfile.relativeVelocityAt(relativeTime.sub(correspondingProfile.initialTimestamp().sub(initialTimestamp()))) + (correspondingProfile.initialParameters().velocity() - initialParameters().velocity());
    }

    @Override
    protected double relativeDistanceAt(Time relativeTime) {
        Profile correspondingProfile = correspondingProfile(relativeTime);
        return correspondingProfile.relativeDistanceAt(relativeTime.sub(correspondingProfile.initialTimestamp().sub(initialTimestamp()))) + (correspondingProfile.initialDistance() - initialDistance());
    }

    @Override
    protected double relativeAccelerationAt(Time relativeTime) {
        Profile correspondingProfile = correspondingProfile(relativeTime);
        return correspondingProfile.relativeAccelerationAt(relativeTime.sub(correspondingProfile.initialTimestamp().sub(initialTimestamp()))) + (correspondingProfile.initialParameters().acceleration() - initialParameters().acceleration());
    }

    @Override
    protected double relativeJerkAt(Time relativeTime) {
        Profile correspondingProfile = correspondingProfile(relativeTime);
        return correspondingProfile.relativeJerkAt(relativeTime.sub(correspondingProfile.initialTimestamp().sub(initialTimestamp()))) + (correspondingProfile.initialParameters().jerk() - initialParameters().jerk());
    }

    private Profile correspondingProfile(Time t) {
        return profiles.stream()
                .filter(profile -> profile.hasValuesOn(t.add(initialTimestamp())))
                .collect(Collectors.toList()).get(0);
    }
}