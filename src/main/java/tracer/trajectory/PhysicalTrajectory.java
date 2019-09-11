package tracer.trajectory;

import com.flash3388.flashlib.time.Time;
import tracer.motion.MotionParameters;
import tracer.motion.PhysicalPosition;
import tracer.motion.Step;
import tracer.motionProfiles.OutsideOfTimeBoundsException;
import tracer.motionProfiles.Profile;
import tracer.motionProfiles.ProfileFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PhysicalTrajectory {
    private final List<Step> steps;
    private final Time timeStep;

    public PhysicalTrajectory(List<Step> steps, Time timeStep) {
        this.steps = steps;
        this.timeStep = timeStep;
    }

    public static PhysicalTrajectory fromFunctional(FunctionalTrajectory functional, MotionParameters max, Time timeStep) {
        Profile functionalProfile = ProfileFactory.createTrajectoryProfile(0, 0, max, Time.seconds(0), functional);
        return new PhysicalTrajectory(stepsFromFunctional(functionalProfile, functional, timeStep), timeStep);
    }

    public Step lastStep() {
        return steps.get(steps.size()-1);
    }

    public Step closestStep(Time currentTime) {
        try {
            return steps.get(closestIndex(currentTime, timeStep));
        } catch (IndexOutOfBoundsException e) {
            System.out.println("no step at "+currentTime);//fix later
            return Step.start();
        }
    }

    private static List<Step> stepsFromFunctional(Profile functionalProfile, FunctionalTrajectory functional, Time timeStep) {
        return IntStream.range(0, calcNumberOfSteps(functionalProfile, timeStep))
                .mapToObj(index -> correspondingStep(functionalProfile, correspondingTime(index, timeStep), functional))
                .collect(Collectors.toList());
    }

    private static int calcNumberOfSteps(Profile functionalProfile, Time timeStep) {
        return closestIndex(functionalProfile.duration(), timeStep);
    }

    private static Time correspondingTime(int index, Time timeStep) {
        return Time.milliseconds(index * timeStep.valueAsMillis());
    }

    private static Step correspondingStep(Profile functionalProfile, Time correspondingTime, FunctionalTrajectory functional) {
        PhysicalPosition correspondingPosition = correspondingPosition(functionalProfile, correspondingTime, functional);
        MotionParameters correspondingParameters = correspondingParameters(functionalProfile, correspondingTime);
        return new Step(correspondingPosition, correspondingParameters);
    }

    private static PhysicalPosition correspondingPosition(Profile functionalProfile, Time correspondingTime, FunctionalTrajectory functional) {
        double correspondingDistance = correspondingDistance(functionalProfile, correspondingTime);
        double correspondingAngle = correspondingAngle(correspondingDistance, functional);
        return new PhysicalPosition(correspondingTime, correspondingDistance, correspondingAngle);
    }

    private static double correspondingDistance(Profile functionalProfile, Time correspondingTime) {
        try {
            return functionalProfile.distanceAt(correspondingTime);
        } catch (OutsideOfTimeBoundsException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    private static double correspondingAngle(double distance, FunctionalTrajectory trajectory) {
        return trajectory.angleAt(distance);
    }

    private static MotionParameters correspondingParameters(Profile functionalProfile, Time correspondingTime) {
        try {
            return functionalProfile.parametersAt(correspondingTime);
        } catch (OutsideOfTimeBoundsException e) {
            System.out.println(e.getMessage());
            return MotionParameters.stop();
        }
    }

    private static int closestIndex(Time currentTime, Time timeStep) {
        return (int) (currentTime.valueAsMillis()/timeStep.valueAsMillis());
    }
}
