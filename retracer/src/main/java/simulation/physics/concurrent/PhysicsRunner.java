package simulation.physics.concurrent;

import com.flash3388.flashlib.time.Time;
import com.jmath.vectors.Vector2;
import simulation.physics.compositions.Composition;

public class PhysicsRunner {
    private final Time physicUpdatePeriod;
    private PhysicsTask current;

    public PhysicsRunner(Time physicUpdatePeriod) {
        this.physicUpdatePeriod = physicUpdatePeriod;
        current = new PhysicsTask(physicUpdatePeriod);
    }

    public void simulate(Composition composition) {
        stop();
        current = new PhysicsTask(composition, physicUpdatePeriod);
        composition.start();

        new Thread(current).start();
    }

    public void stop() {
        current.stop();
    }

    public Vector2 simPosition() {
        return current.position();
    }

    public Vector2 simVelocity() {
        return current.velocity();
    }
}
