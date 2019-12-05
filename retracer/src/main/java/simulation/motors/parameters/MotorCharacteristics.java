package simulation.motors.parameters;

import simulation.motors.parameters.segments.LinearSequence;

public class MotorCharacteristics {
    private final LinearSequence torqueGraph;
    private final double gearRation;

    public MotorCharacteristics(LinearSequence torqueGraph, double gearRation) {
        this.torqueGraph = torqueGraph;
        this.gearRation = gearRation;
    }

    public double maxTorqueAt(double rpm) {
        return torqueGraph.at(rpm) * gearRation;
    }

    public double gearRatio() {
        return gearRation;
    }

    public double angularVelocityToRpm(double angularVelocity) {
        return angularVelocity/(60*gearRation)/(2*Math.PI);
    }
}
