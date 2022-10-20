package com.flash3388.tracer.util;

import com.flash3388.flashlib.math.Mathf;

public class MathUtil {
    public static double distance(double xStart, double yStart, double xEnd, double yEnd) {
        return Math.sqrt(Math.pow(xEnd - xStart, 2) + Math.pow(yEnd - yStart, 2));
    }

    public static double shortestAngularDistance(double from, double to) {
        double result = Mathf.shortestAngularDistance(from, to);
        to = Mathf.translateInRange(to, 180, false);
        from = Mathf.translateInRange(from, 180, false);

        if(to > from)
            return result;
        else
            return -result;
    }
}
