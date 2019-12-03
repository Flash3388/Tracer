package simulation.compositions;

import com.jmath.vectors.Vector2;

public interface Composition {
    void simulate();
    Vector2 force(double velocity);
}
