package com.flash3388.tracer.profiles.base;

import com.flash3388.flashlib.time.Time;
import com.flash3388.tracer.profiles.ProfileState;

public class LinkedProfile implements Profile {
    private final Profile next;
    private final Profile current;

    public LinkedProfile(Profile profile, Profile next) {
        current = profile;
        this.next = next.repositionProfile(profile);
    }

    @Override
    public ProfileState initialState() {
        return current.initialState();
    }

    @Override
    public ProfileState finalState() {
        return next.finalState();
    }

    @Override
    public ProfileState state(Time timestamp) {
        if(timestamp.after(current.finalState().timestamp()))
            return next.state(timestamp);
        else
            return current.state(timestamp);
    }

    @Override
    public Profile repositionProfile(ProfileState newInitialState) {
        return new LinkedProfile(current.repositionProfile(newInitialState), next);
    }
}
