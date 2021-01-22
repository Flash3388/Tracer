package tracer.controllers.parameters;

public class PidControllerParameters {
    private final double kP;
    private final double kI;
    private final double kD;
    
    public PidControllerParameters(double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }

    public double kP() {
        return kP;
    }

    public double kI() {
        return kI;
    }

    public double kD() {
        return kD;
    }
}
