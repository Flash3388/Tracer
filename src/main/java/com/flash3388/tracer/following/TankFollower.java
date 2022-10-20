package com.flash3388.tracer.following;

import com.flash3388.flashlib.robot.systems.drive.TankDrive;

public interface TankFollower extends TankDrive, Follower{
    double passedDistanceLeftM();
    double passedDistanceRightM();
    double angle();
}
