package scheduling;

import java.util.function.DoubleSupplier;

public class TankSchedule {
    private final DoubleSupplier rightSupplier;
    private final DoubleSupplier leftSupplier;

    public TankSchedule(DoubleSupplier rightSupplier, DoubleSupplier leftSupplier) {
        this.rightSupplier = rightSupplier;
        this.leftSupplier = leftSupplier;
    }

    public double right() {
        return rightSupplier.getAsDouble();
    }

    public double left() {
        return leftSupplier.getAsDouble();
    }
}
