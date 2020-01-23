package tracer.profiles;

import com.flash3388.flashlib.time.Time;

public class ProfileDelegate extends MoreBasicProfile {
    private final Profile inner;

    public ProfileDelegate(Profile prevProfile, Profile inner) {
        this(prevProfile.finalState(), inner);
    }

    public ProfileDelegate(ProfileState initialState, Profile inner) {
        super(initialState);
        this.inner = inner;
    }

    @Override
    public Time duration() {
        return inner.deltaState().timestamp();
    }

    @Override
    protected ProfileState relativeProfileState(Time relativeTime) {
        return inner.state(relativeTime);
    }
}
