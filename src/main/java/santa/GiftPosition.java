package santa;

public class GiftPosition {

    private double latitude;
    private double longitude;

    public GiftPosition() {
        this(0.0, 0.0);
    }

    public GiftPosition(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    
    
    public double getHaversineDistance(GiftPosition giftPos2) {
    	return getHaversineDistance(this, giftPos2);
    }
    
    public static double getHaversineDistance(GiftPosition giftPos1, GiftPosition giftPos2) {
    	double EARTH_RADIUS = 6371.137; 
    	
    	// calculate lat and log in rad
    	double phi1 = Math.toRadians(giftPos1.getLatitude());
    	double phi2 = Math.toRadians(giftPos2.getLatitude());
    	double lambda1 = Math.toRadians(giftPos1.getLongitude());
    	double lambda2 = Math.toRadians(giftPos2.getLongitude());
  
        // apply formula
        double a = Math.pow(Math.sin((phi2-phi1) / 2), 2) + Math.cos(phi1) * Math.cos(phi2) * Math.pow(Math.sin((lambda2-lambda1) / 2), 2); 
        return  2 * EARTH_RADIUS * Math.asin(Math.sqrt(a)); 
    }
    
    public static double approximateHaversineDistance(GiftPosition giftPos1, GiftPosition giftPos2) {
    	double EARTH_RADIUS = 6371.137; 
    	
    	// calculate lat and log in rad
    	double phi1 = Math.toRadians(giftPos1.getLatitude());
    	double phi2 = Math.toRadians(giftPos2.getLatitude());
    	double lambda1 = Math.toRadians(giftPos1.getLongitude());
    	double lambda2 = Math.toRadians(giftPos2.getLongitude());
  
        // apply formula
    	double sin1 = sinApprox((phi2-phi1) / 2, 0.01);
    	double sin2 = sinApprox((lambda2-lambda1) / 2,0.01);   	
        double radicand =  sin1*sin1 + cosApprox(phi1,0.01)*cosApprox(phi2,0.01)*sin2*sin2; 

        return  2 * EARTH_RADIUS * Math.asin(Math.sqrt(radicand)); 
    }
    
    
    // https://martin.ankerl.com/2007/10/04/optimized-pow-approximation-for-java-and-c-c/
    private static double powApprox(final double a, final double b) {
        final long tmp = Double.doubleToLongBits(a);
        final long tmp2 = (long)(b * (tmp - 4606921280493453312L)) + 4606921280493453312L;
        return Double.longBitsToDouble(tmp2);
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
    
    
    private static int factorial(int n) {
    	int result = 1;
    	for (int i = 1; i <= n; i++){
    		result = result * i;
		}
    	return result;
    }
    
    
}
