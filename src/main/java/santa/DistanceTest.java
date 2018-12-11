package santa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DistanceTest {
	
	
	public static void runAccuracyTest(List<Gift> gifts, int testCnt) {
		Random random = new Random();
		int giftCnt = gifts.size();
		
		for (int i=0; i< testCnt-1; i++) {
			int giftIds1 = random.nextInt(giftCnt);
			int giftIds2 = random.nextInt(giftCnt);
			double exactDist = Haversine.getHaversineDistance(gifts.get(giftIds1).getPosition(), gifts.get(giftIds2).getPosition());
			double approxDist =  Haversine.approximateHaversineDistance(gifts.get(giftIds1).getPosition(), gifts.get(giftIds2).getPosition());
			
			System.out.println(toResultString(exactDist, approxDist, "%6.1f"));
		}
		    
	}
	
	public static void runTimeTest(List<Gift> gifts, int testCnt) {
		
		long exactTime = 0;
		long approxTime = 0;
		
		long exactStartTime = System.currentTimeMillis();
		HashMap<GiftPosition, GiftPosition>testGifts = new HashMap<GiftPosition, GiftPosition>();
		Random random = new Random();
		int giftCnt = gifts.size();
		for (int i = 0; i< testCnt-1; i++) {
			int giftIds1 = random.nextInt(giftCnt);
			int giftIds2 = random.nextInt(giftCnt);
			testGifts.put(gifts.get(giftIds1).getPosition(), gifts.get(giftIds2).getPosition());
		}
		
		
		for (Map.Entry<GiftPosition, GiftPosition> entry: testGifts.entrySet()) {
			Haversine.getHaversineDistance(entry.getKey(), entry.getValue());
		}
		long exactEndTime = System.currentTimeMillis();
		exactTime = exactEndTime - exactStartTime;
		
		long approxStartTime = System.currentTimeMillis();
		for (Map.Entry<GiftPosition, GiftPosition> entry: testGifts.entrySet()) {
			Haversine.approximateHaversineDistance(entry.getKey(), entry.getValue());
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
