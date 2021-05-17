package org.playuniverse.school.numbertheory.math;

public class FastMath {

    private static final double TWO_POWER_52 = 4503599627370496.0;

    private FastMath() {}
    
    /*
     * Fast inverse squareroot
     */
    
    public static long Q_rsqrt(long value) {
        return Q_rsqrt(value, 3);
    }

    public static long Q_rsqrt(long value, int accuracy) {
        return (long) floor(Q_rsqrt((double) value, accuracy));
    }

    public static double Q_rsqrt(double value) {
        return Q_rsqrt(value, 3);
    }

    public static double Q_rsqrt(double value, int accuracy) {
        double x = value;
        double xhalf = 0.5 * x;
        long i = Double.doubleToLongBits(x);
        i = 0x5f3759df - (i >> 1);
        x = Double.longBitsToDouble(i);
        for (int it = 0; it < accuracy; it++) {
            x = x * (1.5f - xhalf * x * x);
        }
        x *= value;
        return x;
    }

    public static double floor(double x) {
        long y;
        if (Double.isNaN(x)) {
            return x;
        }
        if (x >= TWO_POWER_52 || x <= -TWO_POWER_52) {
            return x;
        }
        y = (long) x;
        if (x < 0 && y != x) {
            y--;
        }
        if (y == 0) {
            return x * y;
        }
        return y;
    }

}
