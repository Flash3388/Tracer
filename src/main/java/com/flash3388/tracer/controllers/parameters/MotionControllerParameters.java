package com.flash3388.tracer.controllers.parameters;

public class MotionControllerParameters {
    private final double kV;
    private final double kA;
    private final double kS;

    /**
     *
     * @param kV a constant that determines the affect of expected speed on the applied voltage
     * @param kA a constant that determines the affect of expected acceleration on the applied voltage
     * @param kS a constant that determines the addition to the applied voltage
     */
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
