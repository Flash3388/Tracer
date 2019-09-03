package tracer;

import com.flash3388.flashlib.time.Time;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SCurveProfile extends Profile {
    private List<Profile> profiles;

    private final double initialDistance;
    private final double initialVelocity;

    public SCurveProfile(double initialDistance, double initialVelocity, MotionParameters max, Time startTime, Time duration) {
        super(initialDistance, initialVelocity, max, startTime, duration);
        
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
    public double relativeVelocityAt(double t) throws OutsideOfTimeBoundsException, MoreThanOneCorrespondingProfileException {
        return correspondingProfile(Time.seconds(t)).relativeVelocityAt(t);
    }

    @Override
    public double relativeDistanceAt(double t) throws OutsideOfTimeBoundsException, MoreThanOneCorrespondingProfileException {
        return correspondingProfile(Time.seconds(t)).relativeDistanceAt(t);
    }

    private Profile correspondingProfile(Time t) throws OutsideOfTimeBoundsException, MoreThanOneCorrespondingProfileException {
        List<Profile> correspondingProfiles = profiles.stream()
                .filter(profile -> profile.isCorresponding(t))
                .collect(Collectors.toList());
        checkIfHasCorrespondingProfile(correspondingProfiles, t);

        return correspondingProfiles.get(0);
    }

    private void checkIfHasCorrespondingProfile(List<Profile> correspondingProfiles, Time t) throws OutsideOfTimeBoundsException, MoreThanOneCorrespondingProfileException {
        if(correspondingProfiles.isEmpty())
            throw new OutsideOfTimeBoundsException(t);
        else if(correspondingProfiles.size() > 1)
            throw new MoreThanOneCorrespondingProfileException(t);
    }
}
