package simulation.physics.motors;

import scheduling.Schedule;
import simulation.physics.motors.parameters.MotorCharacteristics;

public class Motor extends Drive{
    private final MotorCharacteristics characteristics;

    public Motor(MotorCharacteristics characteristics, Schedule<Double> schedule) {
        super(schedule);
        this.characteristics = characteristics;
    }

    public double torqueAt(double wheelAngularVelocity) {
        double scheduled = scheduled();
        double rpm = characteristics.angularVelocityToRpm(wheelAngularVelocity);

        return characteristics.maxTorqueAt(rpm)*characteristics.gearRatio()*scheduled;
    }
}
