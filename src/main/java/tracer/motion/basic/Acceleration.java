//package tracer.motion.basic;
//
//import java.util.concurrent.TimeUnit;
//
//public class Acceleration {
//    private final double value;
//    private final DimensionUnit dimensionUnit;
//    private final TimeUnit timeUnit;
//
//    public Acceleration(double value, DimensionUnit dimensionUnit, TimeUnit timeUnit) {
//        this.value = value;
//        this.dimensionUnit = dimensionUnit;
//        this.timeUnit = timeUnit;
//    }
//
//    public static Acceleration centimetersPerSecondsPerSecond(double value) {
//        return new Acceleration(value, DimensionUnit.CENTIMETERS, TimeUnit.SECONDS);
//    }
//
//    public static Acceleration metersPerSecondsPerSecond(double value) {
//        return new Acceleration(value, DimensionUnit.METERS, TimeUnit.SECONDS);
//    }
//
//    public Acceleration translateDistanceUnit(DimensionUnit newDimensionUnit) {
//        return new Acceleration(value * dimensionUnit.conversionMultiplier(newDimensionUnit),
//                newDimensionUnit, timeUnit);
//    }
//
//    public Acceleration translateTimeUnit(TimeUnit newTimeUnit) {
//        return new Acceleration(value * 1/Math.pow(timeUnit.convert(1, newTimeUnit), 2), dimensionUnit, newTimeUnit);
//    }
//
//    public Acceleration translateUnits(DimensionUnit newDimensionUnit, TimeUnit newTimeUnit) {
//        return translateDistanceUnit(newDimensionUnit).translateTimeUnit(newTimeUnit);
//    }
//
//    public double value() {
//        return value;
//    }
//
//    public DimensionUnit distanceUnit() {
//        return dimensionUnit;
//    }
//
//    public TimeUnit timeUnit() {
//        return timeUnit;
//    }
//
//    public Acceleration add(Acceleration acceleration) {
//        acceleration = acceleration.translateUnits(dimensionUnit, timeUnit);
//        return new Acceleration(value + acceleration.value(), dimensionUnit, timeUnit);
//    }
//
//    public Acceleration sub(Acceleration acceleration) {
//        Acceleration reversedAcceleration = new Acceleration(-acceleration.value(), acceleration.distanceUnit(), acceleration.timeUnit());
//        return add(reversedAcceleration);
//    }
//}
