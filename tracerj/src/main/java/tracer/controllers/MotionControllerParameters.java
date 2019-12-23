package tracer.controllers;

import tracer.motion.MotionParameters;

public class MotionControllerParameters {
    private final double kV;
    private final double kA;
    private final double kS;

    public static MotionControllerParameters theoreticalParameters(MotionParameters max, double maxVoltage, double kS) {
        double kV = maxVoltage / max.velocity();
        double kA = maxVoltage / max.acceleration();
        return new MotionControllerParameters(kV, kA, kS);
    }

    public MotionControllerParameters(double kV, double kA, double kS) {
        this.kV = kV;
        this.kA = kA;
        this.kS = kS;
    }

    public double kV() {
        return kV;
    }

    public double kA() {
        return kA;
    }

    public double kS() {
        return kS;
    }
}
