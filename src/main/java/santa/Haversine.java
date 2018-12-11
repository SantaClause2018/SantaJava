package santa;

public class Haversine {
    public static double APPROX_ACCURACY = 0.01;

    private static final double EARTH_DIAMETER = 12742.274;

    public static double distance(double startLat, double startLong,
                                  double endLat, double endLong) {

        // calculate lat and log in rad
        double phi1 = Math.toRadians(startLat);
        double phi2 = Math.toRadians(endLat);
        double lambda1 = Math.toRadians(startLong);
        double lambda2 = Math.toRadians(endLong);

        // apply formula
        double sin1 = Math.sin((phi2-phi1) / 2);
        double sin2 = Math.sin((lambda2-lambda1) / 2);
        double radicand = sin1*sin1 + Math.cos(phi1) * Math.cos(phi2) * sin2*sin2;
        return EARTH_DIAMETER * Math.asin(Math.sqrt(radicand));
    }

    public static double approximateDistance(double startLat, double startLong,
                                             double endLat, double endLong) {

        // calculate lat and log in rad
        double phi1 = Math.toRadians(startLat);
        double phi2 = Math.toRadians(endLat);
        double lambda1 = Math.toRadians(startLong);
        double lambda2 = Math.toRadians(endLong);

        // apply formula
        double sin1 = sinApprox((phi2-phi1) / 2, APPROX_ACCURACY);
        double sin2 = sinApprox((lambda2-lambda1) / 2,APPROX_ACCURACY);
        double radicand =  sin1*sin1 + cosApprox(phi1,APPROX_ACCURACY)*cosApprox(phi2,APPROX_ACCURACY)*sin2*sin2;

        return  EARTH_DIAMETER * Math.asin(Math.sqrt(radicand));
    }

    private static double cosApprox(double x, double eps){
        // compute the Taylor series approximation
        x = x % (2 * Math.PI);
        double term = 1.0;      // ith term = x^i / i!
        double sum  = 1.0;      // sum of first i terms in taylor series

        for (int i = 1; Math.abs(term) >= eps; i++) {
            term *= (x / i);
            if (i % 4 == 0) sum += term;
            if (i % 4 == 2) sum -= term;
        }
        return sum;
    }

    private static double sinApprox(double x, double eps){
        // compute the Taylor series approximation
        x = x % (2 * Math.PI);
        double term = 1.0;      // ith term = x^i / i!
        double sum  = 0.0;      // sum of first i terms in taylor series

        for (int i = 1; Math.abs(term) >= eps; i++) {
            term *= (x / i);
            if (i % 4 == 1) sum += term;
            if (i % 4 == 3) sum -= term;
        }
        return sum;
    }

}
