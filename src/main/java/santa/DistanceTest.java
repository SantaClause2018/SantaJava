package santa;

import com.google.common.base.Stopwatch;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class DistanceTest {
	
	
	public static void runAccuracyTest(List<Gift> gifts, int testCnt) {
		Gift gift1;
		Gift gift2;
		Random random = new Random();
		int giftCnt = gifts.size();
		
		for (int i=0; i< testCnt; i++) {
			int giftIds1 = random.nextInt(giftCnt);
			int giftIds2 = random.nextInt(giftCnt);
			gift1 = gifts.get(giftIds1);
			gift2 = gifts.get(giftIds2);
			double exactDist = Haversine.distance(
				gift1.getLatitude(), gift1.getLongitude(),
				gift2.getLatitude(), gift2.getLongitude()
			);
			double approxDist = Haversine.approximateDistance(
				gift1.getLatitude(), gift1.getLongitude(),
				gift2.getLatitude(), gift2.getLongitude()
			);
			
			System.out.println(toAccuracyResultString(exactDist, approxDist, "%6.1f"));
		}
		    
	}
	
	public static void runTimeTest(List<Gift> gifts, int testCnt) {

		GiftPosition giftPos1;
		GiftPosition giftPost2;

		Stopwatch stopwatch = Stopwatch.createStarted();
		HashMap<GiftPosition, GiftPosition>testGifts;
		testGifts = new HashMap<>();
		Random random = new Random();
		int giftCnt = gifts.size();
		for (int i = 0; i< testCnt; i++) {
			int giftIds1 = random.nextInt(giftCnt);
			int giftIds2 = random.nextInt(giftCnt);
			testGifts.put(gifts.get(giftIds1).getPosition(), gifts.get(giftIds2).getPosition());
		}
		
		
		for (Map.Entry<GiftPosition, GiftPosition> entry: testGifts.entrySet()) {
			giftPos1 = entry.getKey();
			giftPost2 = entry.getValue();
			Haversine.distance(
					giftPos1.getLatitude(), giftPos1.getLongitude(),
					giftPost2.getLatitude(), giftPost2.getLongitude()
			);
		}
		System.out.println("Execution time exact method:=" + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms");

		stopwatch.reset();
		stopwatch.start();
		for (Map.Entry<GiftPosition, GiftPosition> entry: testGifts.entrySet()) {
			giftPos1 = entry.getKey();
			giftPost2 = entry.getValue();
			Haversine.approximateDistance(
					giftPos1.getLatitude(), giftPos1.getLongitude(),
					giftPost2.getLatitude(), giftPost2.getLongitude()
			);
		}
		System.out.println("Execution time approximation:=" + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms");
	}

	
	private static String toAccuracyResultString(double exact, double approx, String format) {
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
