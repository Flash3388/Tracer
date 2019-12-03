package simulation.compositions;

public class PhysicalCharacteristics {
    private final double mass;
    private final double distanceBetweenWheels;
    private final double wheelRadius;

    public PhysicalCharacteristics(double mass, double distanceBetweenWheels, double wheelRadiusMeters) {
        this.mass = mass;
        this.distanceBetweenWheels = distanceBetweenWheels;
        wheelRadius = wheelRadiusMeters;
    }

    public double mass() {
        return mass;
    }

    public double distanceBetweenWheels() {
        return distanceBetweenWheels;
    }

    public double wheelRadius() {
        return wheelRadius;
    }
}
