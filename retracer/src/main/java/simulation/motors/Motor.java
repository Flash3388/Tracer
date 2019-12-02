package simulation.motors;

import scheduling.Schedule;
import simulation.motors.parameters.MotorCharacteristics;

public class Motor {
    private final MotorCharacteristics characteristics;
    private final Schedule<Double> schedule;

    public Motor(MotorCharacteristics characteristics, Schedule<Double> schedule) {
        this.characteristics = characteristics;
        this.schedule = schedule;
    }

    public void start() {
        schedule.start();
    }

    public double adjustedValue() {
        return 0;
    }
}
