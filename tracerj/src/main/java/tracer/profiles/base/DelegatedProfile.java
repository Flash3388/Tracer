package tracer.profiles.base;

import com.flash3388.flashlib.time.Time;
import tracer.profiles.ProfileState;

public class DelegatedProfile extends BaseProfile {
    private final Profile inner;

    public DelegatedProfile(Profile prevProfile, Profile inner) {
        this(prevProfile.finalState(), inner);
    }

    public DelegatedProfile(ProfileState initialState, Profile inner) {
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
