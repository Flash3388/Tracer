package simulation.motors;

import scheduling.Schedule;
import simulation.motors.parameters.MotorCharacteristics;

import java.util.HashMap;
import java.util.Map;

public class MotorFactory {
    private final Map<MotorType, MotorCharacteristics> motorCharacteristics;

    public MotorFactory() {
        motorCharacteristics = new HashMap<>();
    }

    public Motor create(MotorType type, Schedule<Double> schedule) {
        MotorCharacteristics characteristics = motorCharacteristics.get(type);

        return new Motor(characteristics, schedule);
    }
}
