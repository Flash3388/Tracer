package tracer.motionProfiles;

import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionParameters;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SCurveProfile extends Profile {
    private final List<Profile> profiles;

    private final double initialDistance;
    private final double initialVelocity;

    public SCurveProfile(Profile prevProfile, MotionParameters max) {
        this(prevProfile.absoluteLength(), prevProfile.finalVelocity(), max, prevProfile.end());
    }

    public SCurveProfile(double initialDistance, double initialVelocity, MotionParameters max, Time startTime) {
        super(initialDistance, initialVelocity, 0, max, startTime, calcDuration(max, initialVelocity));

        this.initialDistance = initialDistance;
        this.initialVelocity = initialVelocity();

        profiles = new ArrayList<>();
        profiles.add(createConcaveProfile(startTime));
        profiles.add(createLinearProfile(profiles.get(0)));
        profiles.add(createConvexProfile(max, profiles.get(1)));

        profiles.forEach(profile -> System.out.println(profile.length()));
    }

    private static Time calcDuration(MotionParameters max, double initialVelocity) {
        Profile concave = new ConcaveProfile(0, initialVelocity, max, Time.seconds(0));

        return concave.end().add(calcLinearProfileDuration(concave, max)).add(concave.end());
    }

    private ConcaveProfile createConcaveProfile(Time startTime) {
        return new ConcaveProfile(initialDistance, initialVelocity, getMax(), startTime);
    }

    private LinearVelocityProfile createLinearProfile(Profile concave) {
        return new LinearVelocityProfile(concave, concave.getMax(), calcLinearProfileDuration(concave, concave.getMax()));
    }

    private static Time calcLinearProfileDuration(Profile concave, MotionParameters max) {
        double linearEndVelocity = max.getVelocity() - Math.pow(max.getAcceleration(), 2)/(2 * max.getJerk());

        return Time.seconds((linearEndVelocity - concave.finalVelocity())/max.getAcceleration());
    }

    private ConvexProfile createConvexProfile(MotionParameters max, Profile linear) {
        return new ConvexProfile(linear, max);
    }

    @Override
    protected double relativeVelocityAt(double t) {
        Profile correspondingProfile = correspondingProfile(Time.seconds(t));
        return correspondingProfile.relativeVelocityAt(t) + (correspondingProfile.initialVelocity() - initialVelocity);
    }

    @Override
    protected double relativeDistanceAt(double t)  {
        Profile correspondingProfile = correspondingProfile(Time.seconds(t));
        return correspondingProfile.relativeDistanceAt(t) + (correspondingProfile.initialDistance() - initialDistance);
    }

    @Override
    protected double relativeAccelerationAt(double t) {
        Profile correspondingProfile = correspondingProfile(Time.seconds(t));
        return correspondingProfile.relativeAccelerationAt(t) + correspondingProfile.initialAcceleration();
    }

    private Profile correspondingProfile(Time t) {
        return profiles.stream()
                .filter(profile -> profile.isCorresponding(t.add(start())))
                .collect(Collectors.toList()).get(0);
    }
}
