package tracer.trajectories;

import com.flash3388.flashlib.time.Time;
import tracer.profiles.Profile;

import java.util.ArrayList;
import java.util.List;

public class AngleLog {
    private final List<Double> angleLog;
    private final Thread loggerThread;
    private final long periodMillis;

    public AngleLog(Trajectory trajectory, Profile trajectoryProfile, Time startTime, Time period, int maxSize) {
        angleLog = new ArrayList<>();
        loggerThread = new Thread(new AngleLogger(angleLog, trajectory, startTime, trajectoryProfile, maxSize, period));
        periodMillis = period.valueAsMillis();
    }

    public void start() {
        angleLog.clear();
        loggerThread.start();
    }

    public void stop() {
        loggerThread.interrupt();
    }

    public double at(Time time) throws IndexOutOfBoundsException{
        int index = (int) (time.valueAsMillis()/ periodMillis);
        double result = angleLog.get(index);
        angleLog.subList(0, index).clear();

        return result;
    }
}

class AngleLogger implements Runnable {
    private final List<Double> angleLog;
    private final Trajectory trajectory;
    private final Profile trajectoryProfile;

    private Time startTime;
    private final Time period;
    private final int maxSize;

    public AngleLogger(List<Double> angleLog, Trajectory trajectory, Time startTime, Profile trajectoryProfile, int maxSize, Time period) {
        this.angleLog = angleLog;
        this.trajectory = trajectory;
        this.trajectoryProfile = trajectoryProfile;

        this.startTime = startTime;
        this.maxSize = maxSize;
        this.period = period;
    }

    @Override
    public void run() {
        if(angleLog.size() == maxSize)
            angleLog.remove(0);

        Time current = startTime.add(period);
        startTime = startTime.add(period);
        angleLog.add(trajectory.angleAt(trajectoryProfile.distanceAt(current)));

        try {
            Thread.sleep(period.valueAsMillis());
        } catch (InterruptedException ignored) {
        }
    }
}
