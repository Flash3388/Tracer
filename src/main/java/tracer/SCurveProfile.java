package tracer;

import com.flash3388.flashlib.time.Time;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SCurveProfile extends Profile {
    private final List<Profile> profiles;

    private final double initialDistance;
    private final double initialVelocity;

    public SCurveProfile(Profile prevProfile, MotionParameters max) {
        this(prevProfile.getLength(), prevProfile.getFinalVelocity(), max, prevProfile.getAbsoluteFinalTime());
    }

    public SCurveProfile(double initialDistance, double initialVelocity, MotionParameters max, Time startTime) {
        super(initialDistance, initialVelocity, max, startTime, calcDuration(max));
        
        this.initialDistance = initialDistance;
        this.initialVelocity = getInitialVelocity();

        profiles = new ArrayList<>();
        profiles.add(createConcaveProfile(startTime));
        profiles.add(createLinearProfile(profiles.get(0)));
        profiles.add(createConvexProfile(max, profiles.get(1)));
    }

    private static Time calcDuration(MotionParameters max) {
        Profile concave = new ConcaveProfile(0, 0, max, Time.seconds(0));

        return concave.getAbsoluteFinalTime().add(calcLinearProfileDuration(concave, max)).add(concave.getAbsoluteFinalTime());
    }

    private ConcaveProfile createConcaveProfile(Time startTime) {
        return new ConcaveProfile(initialDistance, initialVelocity, getMax(), startTime);
    }

    private LinearVelocityProfile createLinearProfile(Profile concave) {
        return new LinearVelocityProfile(concave, concave.getMax(), calcLinearProfileDuration(concave, concave.getMax()));
    }

    private static Time calcLinearProfileDuration(Profile concave, MotionParameters max) {
        double linearEndVelocity = max.getVelocity() - Math.pow(max.getAcceleration(), 2)/(2 * max.getJerk());

        return Time.seconds((linearEndVelocity - concave.getFinalVelocity())/max.getAcceleration());
    }

    private ConvexProfile createConvexProfile(MotionParameters max, Profile linear) {
        return new ConvexProfile(linear, max);
    }

    @Override
    public double relativeVelocityAt(double t) {
        return correspondingProfile(Time.seconds(t)).relativeVelocityAt(t);
    }

    @Override
    public double relativeDistanceAt(double t)  {
        return correspondingProfile(Time.seconds(t)).relativeDistanceAt(t);
    }

    private Profile correspondingProfile(Time t) {
        return profiles.stream()
                .filter(profile -> profile.isCorresponding(t))
                .collect(Collectors.toList()).get(0);
    }
}
