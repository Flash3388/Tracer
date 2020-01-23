package tracer.profiles;

import com.flash3388.flashlib.time.Time;

public class LinkedProfile extends MoreBasicProfile {
    private final ProfileDelegate next;
    private final Profile current;

    public LinkedProfile(Profile prevProfile, Profile profile, Profile next) {
        this(prevProfile.finalState(), profile, next);
    }

    public LinkedProfile(ProfileState initialState, Profile profile, Profile next) {
        super(initialState);
        current = profile;
        this.next = new ProfileDelegate(current.finalState().sub(initialState), next);
    }

    @Override
    public Time duration() {
        return current.deltaState().timestamp().add(next.deltaState().timestamp());
    }

    @Override
    protected ProfileState relativeProfileState(Time relativeTime) {
        if(relativeTime.after(current.deltaState().timestamp()))
            return next.state(relativeTime);
        else
            return current.state(relativeTime).sub(current.initialState());
    }
}
