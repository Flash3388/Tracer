package simulation.physics.concurrent;

import com.flash3388.flashlib.time.Time;
import simulation.physics.compositions.Composition;

public class PhysicsRunner {
    private final Time physicUpdatePeriod;

    public PhysicsRunner(Time physicUpdatePeriod) {
        this.physicUpdatePeriod = physicUpdatePeriod;
    }

    public void simulate(Composition composition) {
        composition.start();
        new Thread(new PhysicsTask(composition, physicUpdatePeriod)).start();
    }
}
