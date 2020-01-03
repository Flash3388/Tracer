package tracer.actions;

import com.flash3388.flashlib.robot.scheduling.actions.Action;
import com.flash3388.flashlib.robot.systems.drive.Drive;
import com.flash3388.flashlib.time.Clock;
import com.flash3388.flashlib.time.Time;
import tracer.following.Followable;
import tracer.following.Follower;

abstract class FollowerAction extends Action {
    private final Follower follower;
    private final Followable followable;
    private final Clock clock;

    private Time startTime;

    FollowerAction(Follower follower, Followable followable, Clock clock) {
        this.follower = follower;
        this.followable = followable;
        this.clock = clock;
        setTimeout(followable.duration());
    }

    @Override
    protected void initialize() {
        follower.resetMeasuringDevices();
        followable.reset();
        startTime = clock.currentTime();
    }

    @Override
    protected void execute() {
        setValues(relativeTime());
    }

    @Override
    protected void end() {
        follower.stop();
    }

    abstract void setValues(Time relativeTime);

    private Time relativeTime() {
        return clock.currentTime().sub(startTime);
    }
}
