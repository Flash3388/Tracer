package Tracer;

public abstract class Parameters {
    protected final static double DEF_MARGIN = 0.0001;

    public boolean equals(Parameters parameters) {
        return almostEquals(parameters, 0.0);
    }

    public abstract boolean almostEquals(Parameters parameters, double margin);
}
