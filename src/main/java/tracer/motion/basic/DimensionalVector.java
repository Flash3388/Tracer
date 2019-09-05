//package tracer.motion.basic;
//
//public class DimensionalVector implements Comparable<DimensionalVector> {
//    private final long value;
//    private final DimensionUnit dimensionUnit;
//
//    public DimensionalVector(long value, DimensionUnit dimensionUnit) {
//        this.value = value;
//        this.dimensionUnit = dimensionUnit;
//    }
//
//    public DimensionalVector translateUnit(DimensionUnit newUnit) {
//        return new DimensionalVector(value * dimensionUnit.conversionMultiplier(newUnit), newUnit);
//    }
//
//    public static DimensionalVector of(DimensionalVector vector) {
//        return new DimensionalVector(vector.value(), vector.unit());
//    }
//
//    public static DimensionalVector centimeters(long value) {
//        return new DimensionalVector(value, DimensionUnit.CENTIMETERS);
//    }
//
//    public static DimensionalVector millimeters(long value) {
//        return new DimensionalVector(value, DimensionUnit.MILLIMETERS);
//    }
//
//    public static DimensionalVector meters(long value) {
//        return new DimensionalVector(value, DimensionUnit.METERS);
//    }
//
//    public long value() {
//        return value;
//    }
//
//    public long valueMillimeters() {
//        return value * dimensionUnit.conversionMultiplier(DimensionUnit.MILLIMETERS);
//    }
//
//    public DimensionUnit unit() {
//        return dimensionUnit;
//    }
//
//    public DimensionalVector add(DimensionalVector vector) {
//        vector = vector.convertToSameDistanceUnit(this);
//        DimensionalVector tmp = convertToSameDistanceUnit(vector);
//        return new DimensionalVector(tmp.value() + vector.value(), dimensionUnit);
//    }
//
//    public DimensionalVector sub(DimensionalVector distance) {
//        DimensionalVector reversedDistance = new DimensionalVector(-distance.value(), distance.unit());
//        return add(reversedDistance);
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        return DimensionalVector.of((DimensionalVector) obj).value() == value && DimensionalVector.of((DimensionalVector) obj).unit().equals(dimensionUnit);
//    }
//
//    @Override
//    public int compareTo(DimensionalVector vector) {
//        return (int) Math.signum(sub(vector).value());
//    }
//
//    protected DimensionalVector convertToSameDistanceUnit(DimensionalVector vector) {
//        return vector.unit().compareTo(dimensionUnit) > 0 ? vector.translateUnit(dimensionUnit) : translateUnit(vector.unit());
//    }
//}
