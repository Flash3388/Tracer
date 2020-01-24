package tracer.profiles.base;

import com.flash3388.flashlib.time.Time;
import tracer.profiles.LinkedProfile;
import tracer.profiles.ProfileState;

public interface Profile {
    ProfileState initialState();
    ProfileState finalState();
    ProfileState state(Time timestamp);

    default ProfileState deltaState() {
        return finalState().sub(initialState());
    }

    default Profile then(Profile next) {
        return new LinkedProfile(initialState(), this, next);
    }
}
