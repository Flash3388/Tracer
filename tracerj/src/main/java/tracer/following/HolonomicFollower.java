package tracer.following;

import com.flash3388.flashlib.robot.systems.drive.HolonomicDrive;

public interface HolonomicFollower extends HolonomicDrive, Follower {
    double passedDistance();
}
