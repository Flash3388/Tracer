package simulation.physics.concurrent;

import com.flash3388.flashlib.time.Time;
import com.jmath.vectors.Vector2;
import simulation.physics.compositions.Composition;
import simulation.physics.compositions.EmptyComposition;
import util.TimeConversion;

import java.util.concurrent.TimeUnit;

public class PhysicsTask implements Runnable {
    private final Composition composition;
    private final double physicsUpdatePeriodSeconds;
    private final long physicsUpdatePeriodMillis;

    private Vector2 velocity;
    private Vector2 position;
    private boolean stopped;

    public PhysicsTask(Time physicUpdatePeriod) {
        this(new EmptyComposition(), physicUpdatePeriod);
    }

    public PhysicsTask(Composition composition, Time physicUpdatePeriod) {
        this.composition = composition;
        physicsUpdatePeriodSeconds = TimeConversion.toSeconds(physicUpdatePeriod);
        physicsUpdatePeriodMillis = physicUpdatePeriod.valueAsMillis();

        initialize();
    }

    @Override
    public void run() {
        stopped = false;
        Vector2 acceleration;
        initialize();

        while (!composition.isDone() && !stopped) {
            acceleration = composition.accelerationAt(velocity);
            position = positionAt(acceleration);
            velocity = velocityAt(acceleration);

            delayPhysicsUpdate();
        }
    }

    public void stop() {
        stopped = true;
    }

    public Vector2 velocity() {
        return velocity;
    }

    public Vector2 position() {
        return position;
    }

    private void initialize() {
        velocity = new Vector2();
        position = new Vector2();
    }

    private void delayPhysicsUpdate() {
        try {
            TimeUnit.MILLISECONDS.sleep(physicsUpdatePeriodMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Vector2 positionAt(Vector2 acceleration) {
        return position.add(velocity.multiply(physicsUpdatePeriodSeconds)).add(acceleration.multiply(0.5 * Math.pow(physicsUpdatePeriodSeconds, 2)));
    }

    private Vector2 velocityAt(Vector2 acceleration) {
        return velocity.add(acceleration);
    }
}
