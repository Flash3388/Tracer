package simulation.physics.motors.parameters;

import calculus.functions.MathFunction;

public class MotorCharacteristics {
    private final MathFunction torqueFunction;
    private final double gearRation;

    public MotorCharacteristics(MathFunction torqueFunction, double gearRation) {
        this.torqueFunction = torqueFunction;
        this.gearRation = gearRation;
    }

    public double maxTorqueAt(double rpm) {
        return torqueFunction.applyAsDouble(rpm) * gearRation;
    }

    public double gearRatio() {
        return gearRation;
    }

    public double angularVelocityToRpm(double angularVelocity) {
        return angularVelocity/(60*gearRation)/(2*Math.PI);
    }
}
