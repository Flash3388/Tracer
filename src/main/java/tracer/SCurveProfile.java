package tracer;

import com.flash3388.flashlib.time.Time;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SCurveProfile extends Profile {
    private List<Profile> profiles;

    private final double maxVelocity;
    private final double maxAcceleration;
    private final double maxJerk;

    private final double initialDistance;
    private final double initialVelocity;

    public SCurveProfile(double initialDistance, double initialVelocity, MotionParameters max, Time startTime, Time duration) {
        super(initialDistance, initialVelocity, max, startTime, duration);

        maxVelocity = getMaxVelocity();
        maxAcceleration = getMaxAcceleration();
        maxJerk = getMaxJerk();

        this.initialDistance = initialDistance;
        this.initialVelocity = getInitialVelocity();

        profiles = new ArrayList<>();
        profiles.add(createConcaveProfile(max, startTime));
        profiles.add(createLinearProfile(max, profiles.get(0), duration));
        profiles.add(createConvexProfile(max, profiles.get(1)));
    }

    private ConcaveProfile createConcaveProfile(MotionParameters max, Time startTime) {
        return new ConcaveProfile(initialDistance, initialVelocity, max, startTime);
    }

    private LinearVelocityProfile createLinearProfile(MotionParameters max, Profile concave, Time totalDuration) {
        return new LinearVelocityProfile(concave.getLength(), concave.getFinalVelocity(),
                max, concave.getFinalTime(), calcLinearProfileDuration(concave, totalDuration));
    }

    private Time calcLinearProfileDuration(Profile concave, Time duration) {
        return duration.sub(concave.getDuration());
    }

    private ConvexProfile createConvexProfile(MotionParameters max, Profile linear) {
        return new ConvexProfile(linear.getLength(), linear.getFinalVelocity(), max, linear.getFinalTime());
    }

    @Override
    public double relativeVelocityAt(double t) {
        return profiles.stream()
                .filter(profile -> profile.isCorresponding(Time.seconds(t)))
                .collect(Collectors.toList())
                .get(0).relativeVelocityAt(t);
    }

    @Override
    public double relativeDistanceAt(double t) {
        return profiles.stream()
                .filter(profile -> profile.isCorresponding(Time.seconds(t)))
                .collect(Collectors.toList())
                .get(0).relativeDistanceAt(t);
    }
}
