package simulation.motors;

import scheduling.Schedule;
import simulation.Simulatable;
import simulation.motors.parameters.MotorCharacteristics;

public class Motor implements Simulatable {
    private final MotorCharacteristics characteristics;
    private final Schedule<Double> schedule;

    public Motor(MotorCharacteristics characteristics, Schedule<Double> schedule) {
        this.characteristics = characteristics;
        this.schedule = schedule;
    }

    @Override
    public void simulate() {
        schedule.start();
    }
}
