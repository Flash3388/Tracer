package simulation.physics.compositions;

import com.jmath.vectors.Vector2;

public class EmptyComposition implements Composition {
    public EmptyComposition() {}

    @Override
    public Vector2 accelerationAt(Vector2 velocity) {
        return new Vector2();
    }

    @Override
    public boolean isDone() {
        return true;
    }
}
