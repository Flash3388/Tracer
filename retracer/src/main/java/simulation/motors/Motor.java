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

    public double torqueAt(double wheelAngularVelocity) {
        double rpm = angularVelocityToRpm(wheelAngularVelocity);

        return characteristics.maxTorqueAt(rpm)*characteristics.gearRatio()*schedule.get();
    }

    private double angularVelocityToRpm(double angularVelocity) {
        return angularVelocity/(60*characteristics.gearRatio()*schedule.get())/(2*Math.PI);
    }
}
