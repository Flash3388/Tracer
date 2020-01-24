import tracer.units.morion.Velocity;

public class Main {
    public static void main(String[] args) {
        Velocity velocity = Velocity.centimetersPerSecond(3);
        System.out.println(velocity.add(Velocity.inchesPerSecond(1)));
    }
}
