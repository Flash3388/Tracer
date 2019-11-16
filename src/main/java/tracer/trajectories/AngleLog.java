package tracer.trajectories;

import com.flash3388.flashlib.time.Time;
import tracer.profiles.Profile;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class AngleLog {
    private final Map<Time, Double> angleLog;
    private final Thread loggerThread;
    private final long periodMillis;

    public AngleLog(Trajectory trajectory, Profile trajectoryProfile, Time startTime, Time period, int maxSize) {
        angleLog = new LinkedHashMap<>();
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
        stop();

        Time rounded = round(time);
        double result = angleLog.get(rounded);
        clearUntil(rounded);

        return result;
    }

    private Time round(Time time) {
        return Time.milliseconds((int)(time.valueAsMillis()/periodMillis) * periodMillis);
    }

    private void clearUntil(Time time) {
        angleLog.entrySet().removeIf(e -> e.getKey().lessThanOrEquals(time));

        loggerThread.start();
    }
}

class AngleLogger implements Runnable {
    private final Map<Time, Double> angleLog;
    private final Trajectory trajectory;
    private final Profile trajectoryProfile;

    private Time startTime;
    private final Time period;
    private final int maxSize;

    public AngleLogger(Map<Time, Double> angleLog, Trajectory trajectory, Time startTime, Profile trajectoryProfile, int maxSize, Time period) {
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
            angleLog.remove(angleLog.keySet().iterator().next());

        Time current = startTime.add(period);
        startTime = startTime.add(period);
        angleLog.put(current, trajectory.angleAt(trajectoryProfile.distanceAt(current)));
    }
}
