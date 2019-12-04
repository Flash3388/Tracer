package simulation.compositions;

public class PhysicalCharacteristics {
    private final double mass;
    private final double wheelRadius;

    public PhysicalCharacteristics(double mass, double wheelRadiusMeters) {
        this.mass = mass;
        wheelRadius = wheelRadiusMeters;
    }

    public double mass() {
        return mass;
    }

    public double wheelRadius() {
        return wheelRadius;
    }
}
