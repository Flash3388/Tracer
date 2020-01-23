package tracer.profiles;

import com.flash3388.flashlib.time.Time;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import tracer.motion.MotionState;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LinkedProfileTest {
    @ParameterizedTest
    @MethodSource("provideBaseProfilesForLinkedProfileTest")
    public void stateAt_forSecondProfileInLinkedProfile_returnsCorrectState(final Profile baseProfile) {
        final Time linearProfileDuration = Time.seconds(1);
        final Profile linkedProfile = baseProfile.then(new LinearVelocityProfile(baseProfile.finalState().parameters(), linearProfileDuration));
        final Profile regularProfile = LinearVelocityProfile.continuation(baseProfile, Time.seconds(1));

        ProfileState EXPECTED = regularProfile.state(baseProfile.finalState().timestamp().add(linearProfileDuration.sub(Time.seconds(0.5))));
        ProfileState ACTUAL = linkedProfile.state(baseProfile.finalState().timestamp().add(linearProfileDuration.sub(Time.seconds(0.5))));

        assertEquals(EXPECTED, ACTUAL);
    }

    @ParameterizedTest
    @MethodSource("provideBaseProfilesForLinkedProfileTest")
    public void stateAt_forFirstProfileInLinkedProfile_returnsCorrectState(final Profile baseProfile) {
        final Time linearProfileDuration = Time.seconds(1);
        final Profile linkedProfile = baseProfile.then(new LinearVelocityProfile(baseProfile.finalState().parameters(), linearProfileDuration));

        ProfileState EXPECTED = baseProfile.state(linearProfileDuration.sub(Time.seconds(0.5)));
        ProfileState ACTUAL = linkedProfile.state(linearProfileDuration.sub(Time.seconds(0.5)));

        assertEquals(EXPECTED, ACTUAL);
    }

    private static Stream<Arguments> provideBaseProfilesForLinkedProfileTest() {
        MotionState target = new MotionState(1, 2, 3);

        return Stream.of(
                Arguments.of(new ConstantVelocityProfile(MotionState.constantVelocity(2), Time.seconds(2))),
                Arguments.of(ConstantVelocityProfile.continuation(new ProfileState(2, MotionState.constantVelocity(2), Time.milliseconds(0)), Time.seconds(2))),
                Arguments.of(new LinearVelocityProfile(MotionState.linearVelocity(1, 2), Time.seconds(2))),
                Arguments.of(new LinearVelocityProfile(new ProfileState(2, MotionState.stop(), Time.milliseconds(0)), MotionState.linearVelocity(1, 2), Time.seconds(2))),
                Arguments.of(new ConcaveProfile(target)),
                Arguments.of(new ConcaveProfile(new ProfileState(2, MotionState.stop(), Time.milliseconds(0)), target))
        );
    }
}
