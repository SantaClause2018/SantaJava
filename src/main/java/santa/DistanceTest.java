package santa;

import java.util.List;

public class DistanceTest {
	
	
	public static void runAccuracyTest(List<Gift> gifts, int testCnt) {
			
		for (int i=0; i< testCnt-1; i++) {
			double exactDist = GiftPosition.getHaversineDistance(gifts.get(i).getPosition(), gifts.get(i+1).getPosition());
			double approxDist =  GiftPosition.approximateHaversineDistance(gifts.get(i).getPosition(), gifts.get(i+1).getPosition());
			
			System.out.println(toResultString(exactDist, approxDist, "%6.1f"));
		}
		    
	}
	
	public static void runTimeTest(List<Gift> gifts, int testCnt) {
		
		long exactTime = 0;
		long approxTime = 0;
		
		long exactStartTime = System.currentTimeMillis();
		for (int i=0; i< testCnt-1; i++) {	
			GiftPosition.getHaversineDistance(gifts.get(i).getPosition(), gifts.get(i+1).getPosition());		
		}
		long exactEndTime = System.currentTimeMillis();
		exactTime = exactEndTime - exactStartTime;
		
		long approxStartTime = System.currentTimeMillis();
		for (int i=0; i< testCnt-1; i++) {
			GiftPosition.approximateHaversineDistance(gifts.get(i).getPosition(), gifts.get(i+1).getPosition());
		}
		long approxEndTime = System.currentTimeMillis();
		approxTime = approxEndTime - approxStartTime;
		
		System.out.println("exection time exact Method: " + exactTime + " ms, execution time approximation: " + approxTime +  " ms (" + testCnt + " calculations)");
	}
	
	
	
	
	private static String toResultString(double exact, double approx, String format) {
	 return	"exact calc: " + String.format(format,exact) + ", approx calc: " + String.format(format,approx) + 
		", diff: " + String.format(format,getAbsErr(exact,approx)) + ", rel: "  + String.format(format,(getRelErr(exact, approx)*100)) + "%" ;
	}
	
	private static double getAbsErr(double exact, double approximation) {
		return exact - approximation;
	}
	
	private static double getRelErr(double exact, double approximation) {
		return getAbsErr(exact, approximation)/ exact;
	}

}
