package simulation.physics.compositions;

import com.jmath.vectors.Vector2;

public interface Composition {
    Vector2 accelerationAt(Vector2 velocity);
    boolean isDone();

    default void start() {
    }
}
