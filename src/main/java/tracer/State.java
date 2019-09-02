package tracer;

public class State {
    private final MotionParameters motionParameters;
    private final double passedDistance;

    public State(MotionParameters motionParameters, double passedDistance) {
        this.motionParameters = motionParameters;
        this.passedDistance = passedDistance;
    }

    public MotionParameters getMotionParameters() {
        return motionParameters;
    }

    public double getPassedDistance() {
        return passedDistance;
    }
}
