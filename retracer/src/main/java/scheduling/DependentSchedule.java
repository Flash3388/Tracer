package scheduling;

public abstract class DependentSchedule<O, I> implements Schedule<O>, Behavioral<O, I> {
    private final TimeSchedule<I> timeSchedule;

    public DependentSchedule(TimeSchedule<I> timeSchedule) {
        this.timeSchedule = timeSchedule;
    }

    @Override
    public void start() {
        timeSchedule.start();
    }

    @Override
    public O get() {
        return behavior(timeSchedule.get());
    }
}
