package calculus.segments;

public interface Segment<T> {
    double start();
    double end();
    T get();
}
