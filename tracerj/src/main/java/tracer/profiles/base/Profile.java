package tracer.profiles.base;

import com.flash3388.flashlib.time.Time;
import tracer.profiles.ProfileState;

public interface Profile {
    ProfileState initialState();
    ProfileState finalState();
    ProfileState state(Time timestamp);
    Profile repositionProfile(ProfileState newInitialState);

    default ProfileState deltaState() {
        return finalState().sub(initialState());
    }

    default Profile repositionProfile(Profile newPrevProfile) {
        return repositionProfile(newPrevProfile.finalState());
    }

    default Profile then(Profile next) {
        return new LinkedProfile(this, next);
    }
}
