package simulation.compositions;

import com.jmath.vectors.Vector2;

public interface Composition {
    void simulate();
    Vector2 accelerationAt(double velocity);
}
