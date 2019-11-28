import java.util.function.DoubleSupplier;

public abstract class SensorLinkedMotorSchedule<T> implements DoubleSupplier, Behavioral<Double, T> {
    private final SensorSchedule<T> sensorSchedule;

    public SensorLinkedMotorSchedule(SensorSchedule sensorSchedule) {
        this.sensorSchedule = sensorSchedule;
    }

    @Override
    public double getAsDouble() {
        return behavior(sensorSchedule.get());
    }
}
