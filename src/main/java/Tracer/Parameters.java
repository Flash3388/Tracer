package Tracer;

public abstract class Parameters {
    protected final static double DEF_MARGIN = 0.0001;

    protected boolean almostEquals(double val, double val1, double margin) {
        return Math.abs(val-val1) <= margin;
    }

    public boolean equals(Parameters parameters) {
        return almostEquals(parameters, 0.0);
    }

    public abstract boolean almostEquals(Parameters parameters, double margin);
}
