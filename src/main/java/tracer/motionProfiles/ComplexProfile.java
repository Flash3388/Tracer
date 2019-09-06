package tracer.motionProfiles;

import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionParameters;

import java.util.List;
import java.util.stream.Collectors;

public class ComplexProfile extends Profile {
    private final List<Profile> profiles;

    public ComplexProfile(Profile prevProfile, MotionParameters max, Time duration, List<Profile> profiles) {
        super(prevProfile, max, duration);
        this.profiles = profiles;
    }

    public ComplexProfile(double initialDistance, double initialVelocity, double initAcceleration, MotionParameters max, Time startTime, Time duration, List<Profile> profiles) {
        super(initialDistance, initialVelocity, initAcceleration, max, startTime, duration);
        this.profiles = profiles;
    }

    @Override
    protected double relativeVelocityAt(Time relativeTime) {
        Profile correspondingProfile = correspondingProfile(relativeTime);
        return correspondingProfile.relativeVelocityAt(relativeTime.sub(correspondingProfile.start())) + (correspondingProfile.initialVelocity() - initialVelocity());
    }

    @Override
    protected double relativeDistanceAt(Time relativeTime) {
        Profile correspondingProfile = correspondingProfile(relativeTime);
        return correspondingProfile.relativeDistanceAt(relativeTime.sub(correspondingProfile.start())) + (correspondingProfile.initialDistance() - initialDistance());
    }

    @Override
    protected double relativeAccelerationAt(Time relativeTime) {
        Profile correspondingProfile = correspondingProfile(relativeTime);
        return correspondingProfile.relativeAccelerationAt(relativeTime.sub(correspondingProfile.start())) + correspondingProfile.initialAcceleration();
    }

    private Profile correspondingProfile(Time t) {
        return profiles.stream()
                .filter(profile -> profile.isCorresponding(t.add(start())))
                .collect(Collectors.toList()).get(0);
    }
}
