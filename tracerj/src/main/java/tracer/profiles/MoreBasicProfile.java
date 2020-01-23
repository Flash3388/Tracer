package tracer.profiles;

import com.flash3388.flashlib.time.Time;

public abstract class MoreBasicProfile implements Profile {
    private final ProfileState initialState;

    public MoreBasicProfile() {
        this(new ProfileState());
    }

    public MoreBasicProfile(Profile prevProfile) {
        this(prevProfile.finalState());
    }

    public MoreBasicProfile(ProfileState initialState) {
        this.initialState = initialState;
    }

    @Override
    public ProfileState initialState() {
        return initialState;
    }

    @Override
    public ProfileState finalState() {
        return state(initialState.timestamp().add(duration()));
    }

    @Override
    public ProfileState state(Time currentTime) {
        checkTime(currentTime);
        return initialState.add(relativeProfileState(relativeTime(currentTime)));
    }

    public abstract Time duration();
    protected abstract ProfileState relativeProfileState(Time relativeTime);

    private void checkTime(Time time) {
        if(time.before(initialState.timestamp()) || time.after(finalTimestamp()))
            throw new IllegalArgumentException(String.format("time %s is outside of this profile's time limits", time));
    }

    private Time finalTimestamp() {
        return initialState.timestamp().add(duration());
    }

    private Time relativeTime(Time currentTime) {
        return currentTime.sub(initialState().timestamp());
    }
}
