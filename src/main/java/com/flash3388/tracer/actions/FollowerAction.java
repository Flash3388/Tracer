package com.flash3388.tracer.actions;

import com.flash3388.flashlib.robot.systems.drive.Drive;
import com.flash3388.flashlib.scheduling.actions.ActionBase;
import com.flash3388.flashlib.time.Time;
import com.flash3388.tracer.following.Followable;

abstract class FollowerAction extends ActionBase {
    private final Drive drive;
    private final Followable followable;

    FollowerAction(Drive drive, Followable followable) {
        this.drive = drive;
        this.followable = followable;

        requires(drive);
        withTimeout(followable.duration());
    }

    @Override
    public void initialize() {
        followable.reset();
    }

    @Override
    public void execute() {
        setValues(getRunTime());
    }

    @Override
    public void end(boolean wasInterrupted) {
        drive.stop();
    }

    protected abstract void setValues(Time relativeTime);
}
