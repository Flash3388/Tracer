package simulation.motors;

import scheduling.Schedule;

public abstract class Drive {
    private final Schedule<Double> schedule;

    protected Drive(Schedule<Double> schedule) {
        this.schedule = schedule;
    }

    public void start() {
        schedule.start();
    }

    public abstract double torqueAt(double wheelAngularVelocity);

    protected double scheduled() {
        return schedule.get();
    }
}
