package com.flash3388.tracer.profiles.base;

import com.flash3388.flashlib.time.Time;
import com.flash3388.tracer.profiles.ProfileState;

public abstract class BaseProfile implements Profile {
    private final ProfileState initialState;

    public BaseProfile() {
        this(new ProfileState());
    }

    public BaseProfile(Profile prevProfile) {
        this(prevProfile.finalState());
    }

    public BaseProfile(ProfileState initialState) {
        this.initialState = initialState;
    }

    @Override
    public ProfileState initialState() {
        return initialState;
    }

    @Override
    public ProfileState finalState() {
        return state(finalTimestamp());
    }

    @Override
    public ProfileState state(Time currentTime) {
        checkTime(currentTime);
        return initialState.add(relativeProfileState(relativeTime(currentTime)));
    }

    public abstract Time duration();
    public abstract BaseProfile repositionProfile(ProfileState newInitialState);
    protected abstract ProfileState relativeProfileState(Time relativeTime);

    private void checkTime(Time time) {
        if(time.before(initialState.timestamp()) || time.after(finalTimestamp().add(Time.milliseconds(1))))
            throw new IllegalArgumentException(String.format("time %s is outside of this profile's time limits from %s to %s", time, initialState.timestamp(), finalTimestamp()));
    }

    private Time finalTimestamp() {
        return initialState.timestamp().add(duration());
    }

    private Time relativeTime(Time currentTime) {
        return currentTime.sub(initialState.timestamp());
    }
}
