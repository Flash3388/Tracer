package simulation.compositions;

public class DriveCompositionCharacteristics {
    private final double aerodynamicDrag;
    private final double rollingResistance;
    private final double slopeAngle;

    public DriveCompositionCharacteristics(double aerodynamicDrag, double rollingResistance, double slopeAngle) {
        this.aerodynamicDrag = aerodynamicDrag;
        this.rollingResistance = rollingResistance;
        this.slopeAngle = slopeAngle;
    }

    public double drag() {
        return aerodynamicDrag;
    }

    public double rr() {
        return rollingResistance;
    }

    public double slopeAngle() {
        return slopeAngle;
    }
}
