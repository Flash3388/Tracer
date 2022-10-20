
package com.flash3388.tracer.following;

import com.flash3388.flashlib.robot.systems.drive.Drive;

public interface Follower extends Drive {
    default void resetMeasuringDevices() {
    }
}