package calculus.splines;

import calculus.functions.polynomialFunctions.Cubic;
import tracer.motion.Waypoint;

public class HermiteCubicSpline extends Spline {
    public HermiteCubicSpline(Waypoint startWaypoint, Waypoint endWaypoint) {
        super(calcYFunction(startWaypoint, endWaypoint), calcXFunction(startWaypoint, endWaypoint));
    }

    private static Cubic calcYFunction(Waypoint start, Waypoint end) {
        double m0y = Math.sin(start.getHeading());
        double m1y = Math.sin(end.getHeading());

        System.out.println("ay= "+start.y()+" by= "+m0y+" cy= "+m1y+" dy= "+end.y());

        return calcFunction(start.y(), m0y, m1y, end.y());
    }

    private static Cubic calcXFunction(Waypoint start, Waypoint end) {
        double m0x = Math.cos(start.getHeading());
        double m1x = Math.cos(end.getHeading());

        System.out.println("ax= "+start.x()+" bx= "+m0x+" cx= "+m1x+" dx= "+end.x());

        return calcFunction(start.x(), m0x, m1x, end.x());
    }

    private static Cubic calcFunction(double p0, double m0, double m1, double p1) {
        return new Cubic(
                calcA(p0, m0, m1, p1),
                calcB(p0, m0, m1, p1),
                calcC(m0),
                calcD(p0));
    }

    private static double calcA(double p0, double m0, double m1, double p1) {
        return 2*p0 + m0 + m1 - 2*p1;
    }

    private static double calcB(double p0, double m0, double m1, double p1) {
        return -3*p0 - m1 -2*m0 + 3*p1;
    }

    private static double calcC(double m0) {
        return m0;
    }

    private static double calcD(double p0) {
        return p0;
    }
}
