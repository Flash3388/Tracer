import java.util.function.DoubleSupplier;

public abstract class SensorLinkedMotor<T> implements DoubleSupplier, Behavioral<Double, T> {
    private final Sensor<T> sensor;

    public SensorLinkedMotor(Sensor sensor) {
        this.sensor = sensor;
    }

    @Override
    public double getAsDouble() {
        return behavior(sensor.get());
    }
}
