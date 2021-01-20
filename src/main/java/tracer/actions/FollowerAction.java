package tracer.actions;

import com.flash3388.flashlib.robot.systems.drive.Drive;
import com.flash3388.flashlib.scheduling.actions.ActionBase;
import com.flash3388.flashlib.scheduling.actions.ActionConfiguration;
import com.flash3388.flashlib.time.Clock;
import com.flash3388.flashlib.time.Time;
import tracer.following.Followable;

abstract class FollowerAction extends ActionBase {
    private final Drive drive;
    private final Followable followable;
    private final Clock clock;

    private Time startTime;

    FollowerAction(Drive drive, Followable followable, Clock clock) {
        this.drive = drive;
        this.followable = followable;
        this.clock = clock;

        setConfiguration(new ActionConfiguration(getConfiguration().getRequirements(), followable.duration(), getConfiguration().getName(), getConfiguration().shouldRunWhenDisabled()));
    }

    @Override
    public void initialize() {
        followable.reset();
        startTime = clock.currentTime();
    }

    @Override
    public void execute() {
        setValues(relativeTime());
    }

    @Override
    public void end(boolean wasInterrupted) {
        drive.stop();
    }

    protected abstract void setValues(Time relativeTime);

    private Time relativeTime() {
        return clock.currentTime().sub(startTime);
    }
}
