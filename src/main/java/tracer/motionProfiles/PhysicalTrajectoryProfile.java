package tracer.motionProfiles;

import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionParameters;
import tracer.trajectory.PhysicalTrajectory;

public class PhysicalTrajectoryProfile extends Profile {
    private final PhysicalTrajectory trajectory;

    public PhysicalTrajectoryProfile(Profile prevProfile, MotionParameters max, PhysicalTrajectory trajectory) {
        this(prevProfile.initialDistance(), prevProfile.initialParameters().velocity(), max, prevProfile.end(), trajectory);
    }

    public PhysicalTrajectoryProfile(double initialDistance, double initialVelocity, MotionParameters max, Time startTime, PhysicalTrajectory trajectory) {
        super(initialDistance, MotionParameters.constantVelocity(initialVelocity), max, startTime, trajectory.lastStep().getTiming());
        this.trajectory = trajectory;
    }

    @Override
    protected double relativeVelocityAt(Time relativeTime) {
        return trajectory.closestStep(relativeTime).velocity();
    }

    @Override
    protected double relativeDistanceAt(Time relativeTime) {
        return trajectory.closestStep(relativeTime).distance();
    }

    @Override
    protected double relativeAccelerationAt(Time relativeTime) {
        return trajectory.closestStep(relativeTime).acceleration();
    }

    @Override
    protected double relativeJerkAt(Time relativeTime) {
        return trajectory.closestStep(relativeTime).jerk();
    }

    public double angleAt(Time relativeTime) {
        return trajectory.closestStep(relativeTime).angle();
    }
}
