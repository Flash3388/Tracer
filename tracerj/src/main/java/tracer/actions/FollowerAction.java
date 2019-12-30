package tracer.actions;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.systems.drive.Drive;
import com.flash3388.flashlib.time.Clock;
import com.flash3388.flashlib.time.Time;
import tracer.controllers.Followable;

abstract class FollowerAction extends Action {
    private final Drive drive;
    private final Followable followable;
    private final Clock clock;

    private Time startTime;

    FollowerAction(Drive drive, Followable followable, Clock clock) {
        this.drive = drive;
        this.followable = followable;
        this.clock = clock;
        setTimeout(followable.duration());
    }

    @Override
    protected void initialize() {
        followable.reset();
        startTime = clock.currentTime();
    }

    @Override
    protected void execute() {
        setValues(relativeTime());
    }

    @Override
    protected void end() {
        drive.stop();
    }

    abstract void setValues(Time relativeTime);

    private Time relativeTime() {
        return clock.currentTime().sub(startTime);
    }
}
