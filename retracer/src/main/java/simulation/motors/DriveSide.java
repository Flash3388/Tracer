package simulation.motors;

import scheduling.Schedule;
import simulation.motors.parameters.MotorCharacteristics;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DriveSide extends Drive{
    private final List<Motor> motors;

    public DriveSide(Schedule<Double> schedule, MotorCharacteristics... motorCharacteristics) {
        this(schedule, Arrays.asList(motorCharacteristics));
    }

    public DriveSide(Schedule<Double> schedule, List<MotorCharacteristics> motorCharacteristics) {
        super(schedule);
        this.motors = generateMotorsWithSameSchedule(motorCharacteristics, schedule);
    }

    public static DriveSide sameMotors(Schedule<Double> schedule, MotorCharacteristics characteristics, int amount) {
        return new DriveSide(schedule, Collections.nCopies(amount, characteristics));
    }

    @Override
    public double torqueAt(double wheelAngularVelocity) {
        return motors.stream()
                .mapToDouble(motor -> motor.torqueAt(wheelAngularVelocity))
                .sum();
    }

    private List<Motor> generateMotorsWithSameSchedule(List<MotorCharacteristics> characteristics, Schedule<Double> schedule) {
        return characteristics.stream()
                .map(characteristic -> new Motor(characteristic, schedule))
                .collect(Collectors.toList());
    }
}
