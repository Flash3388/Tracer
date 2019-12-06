package simulation.physics.motors.parameters;

import calculus.functions.MathFunction;

public class MotorCharacteristics {
    private final MathFunction torqueGraph;
    private final double gearRation;

    public MotorCharacteristics(MathFunction torqueGraph, double gearRation) {
        this.torqueGraph = torqueGraph;
        this.gearRation = gearRation;
    }

    public double maxTorqueAt(double rpm) {
        return torqueGraph.applyAsDouble(rpm) * gearRation;
    }

    public double gearRatio() {
        return gearRation;
    }

    public double angularVelocityToRpm(double angularVelocity) {
        return angularVelocity/(60*gearRation)/(2*Math.PI);
    }
}
