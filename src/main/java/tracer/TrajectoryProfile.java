package tracer;

import com.flash3388.flashlib.time.Time;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TrajectoryProfile extends Profile {
    private final List<Profile> profiles;
    private final Trajectory trajectory;

    private final double initialDistance;
    private final double initialVelocity;

    public TrajectoryProfile(double initialDistance, double initialVelocity, MotionParameters max, Time startTime, Trajectory trajectory) {
        super(initialDistance, initialVelocity, max, startTime, calcDuration(max, trajectory.getLength()));

        this.initialDistance = initialDistance;
        this.initialVelocity = getInitialVelocity();
        this.trajectory = trajectory;

        profiles = new ArrayList<>();
        profiles.add(createStartSCurve(startTime));
        profiles.add(createConstantVelocityProfile(profiles.get(0), trajectory));
        profiles.add(createEndSCurve(profiles.get(1)));
    }

    private static Time calcDuration(MotionParameters max, double trajectoryLength) {
        SCurveProfile sCurve = new SCurveProfile(0, 0, max, Time.seconds(0));

        return sCurve.getDuration().add(calcConstantVelocityDuration(sCurve, max, trajectoryLength)).add(sCurve.getDuration());
    }

    private SCurveProfile createStartSCurve(Time startTime) {
        return new SCurveProfile(initialDistance, initialVelocity, getMax(), startTime);
    }

    private ConstantVelocityProfile createConstantVelocityProfile(Profile prevProfile, Trajectory trajectory) {
        return new ConstantVelocityProfile(prevProfile.getLength(), getMax(),
                prevProfile.getAbsoluteFinalTime(), calcConstantVelocityDuration(prevProfile, getMax(), trajectory.getLength()));
    }

    private static Time calcConstantVelocityDuration(Profile sCurve, MotionParameters max, double trajectoryLength) {
        return Time.seconds((trajectoryLength - 2 * sCurve.getLength()) / max.getVelocity());
    }

    private SCurveProfile createEndSCurve(Profile prevProfile) {
        MotionParameters max = getMax();
        MotionParameters reversedMotionParameters = new MotionParameters(-max.getVelocity(), -max.getAcceleration(), max.getJerk());

        return new SCurveProfile(prevProfile.getLength(), prevProfile.getFinalVelocity(), reversedMotionParameters, prevProfile.getAbsoluteFinalTime());
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
