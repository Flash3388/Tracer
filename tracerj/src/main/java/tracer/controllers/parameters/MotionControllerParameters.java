package tracer.controllers.parameters;

public class MotionControllerParameters {
    private final double kV;
    private final double kA;
    private final double kS;

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
