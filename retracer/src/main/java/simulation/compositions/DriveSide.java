package simulation.compositions;

import simulation.motors.Motor;

import java.util.Arrays;
import java.util.List;

public class DriveSide {
    private final List<Motor> motors;

    public DriveSide(Motor... motors) {
        this(Arrays.asList(motors));
    }

    public DriveSide(List<Motor> motors) {
        this.motors = motors;
    }

    public void start() {
        motors.forEach(Motor::start);
    }

    public double sideTorque(double angularVelocity) {
        return motors.stream()
                .mapToDouble(motor -> motor.torqueAt(angularVelocity))
                .sum();
    }
}
