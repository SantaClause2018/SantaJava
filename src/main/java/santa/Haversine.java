package santa;

public class Haversine {
    public static double APPROX_ACCURACY = 0.01;

    private static final double earthDiameter = 12742.274;
    private static final int EARTH_RADIUS = 6371; // Approx Earth radius in KM

    public static double distance(double startLat, double startLong,
                                  double endLat, double endLong) {

        double dLat  = Math.toRadians((endLat - startLat));
        double dLong = Math.toRadians((endLong - startLong));

        startLat = Math.toRadians(startLat);
        endLat   = Math.toRadians(endLat);

        double a = haversin(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversin(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c; // <-- d
    }

    public static double haversin(double val) {
        return Math.pow(Math.sin(val / 2), 2);
    }

    public static double getHaversineDistance(GiftPosition giftPos1, GiftPosition giftPos2) {

        // calculate lat and log in rad
        double phi1 = Math.toRadians(giftPos1.getLatitude());
        double phi2 = Math.toRadians(giftPos2.getLatitude());
        double lambda1 = Math.toRadians(giftPos1.getLongitude());
        double lambda2 = Math.toRadians(giftPos2.getLongitude());

        // apply formula
        double sin1 = Math.sin((phi2-phi1) / 2);
        double sin2 = Math.sin((lambda2-lambda1) / 2);
        double a = sin1*sin1 + Math.cos(phi1) * Math.cos(phi2) * sin2*sin2;
        return earthDiameter * Math.asin(Math.sqrt(a));
    }

    public static double approximateHaversineDistance(GiftPosition giftPos1, GiftPosition giftPos2) {
        double earthDiameter = 12742.274;

        // calculate lat and log in rad
        double phi1 = Math.toRadians(giftPos1.getLatitude());
        double phi2 = Math.toRadians(giftPos2.getLatitude());
        double lambda1 = Math.toRadians(giftPos1.getLongitude());
        double lambda2 = Math.toRadians(giftPos2.getLongitude());

        // apply formula
        double sin1 = sinApprox((phi2-phi1) / 2, APPROX_ACCURACY);
        double sin2 = sinApprox((lambda2-lambda1) / 2,APPROX_ACCURACY);
        double radicand =  sin1*sin1 + cosApprox(phi1,APPROX_ACCURACY)*cosApprox(phi2,APPROX_ACCURACY)*sin2*sin2;

        return  earthDiameter * Math.asin(Math.sqrt(radicand));
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

    private static double asinApprox(double x, double eps ) {
        // compute the Taylor series approximation
        x = x % (2 * Math.PI);
        double term = 1.0;      // ith term = x^i / i!
        double sum  = 0.0;      // sum of first i terms in taylor series

        for (int i = 1; Math.abs(term) >= eps; i++) {
            term *= (x / i);
            if (i % 4 == 1 || i % 4 == 3) sum += term;
        }
        return sum;
    }


    private static int factorial(int n) {
        int result = 1;
        for (int i = 1; i <= n; i++){
            result = result * i;
        }
        return result;
    }

}
