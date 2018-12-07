package santa;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;

import java.util.*;

public class PositionNet {

    public static int LONGITUDE_MIN  = -180;
    public static int LONGITUDE_MAX  = 180;
    public static int LONGITUDE_STEP = 15;

    public static int LATITUDE_MIN  = -90;
    public static int LATITUDE_MAX  = 90;
    public static int LATITUDE_STEP = 15;

    RangeMap<Double, RangeMap<Double, List<Gift>>> longitudeMap;

    /**
     * Helper function to construct PositionNet
     * @return RangeMap of Latitude per Longitude
     */
    private static RangeMap<Double, List<Gift>> create() {
        RangeMap<Double, List<Gift>> latitudeMap = TreeRangeMap.create();

        for (int i = LATITUDE_MIN; i <= (LATITUDE_MAX - LATITUDE_STEP); i += LATITUDE_STEP) {
            latitudeMap.put(Range.closed((double) i,  (double)i + LATITUDE_STEP), new ArrayList<Gift>());
        }
        return latitudeMap;
    }

    /**
     * Constructor
     */
    public PositionNet() {
        longitudeMap = TreeRangeMap.create();

        for (int i = LONGITUDE_MIN; i <= (LONGITUDE_MAX - LONGITUDE_STEP); i += LONGITUDE_STEP) {
            longitudeMap.put(Range.closed((double) i,  (double)i + LONGITUDE_STEP), create());
        }
    }

    /**
     *
     * @param gift
     */
    public void add(Gift gift) {
        longitudeMap.get(gift.getLongitude()).get(gift.getLatitude()).add(gift);
    }

    public void printMap() {
        RangeMap<Double, List<Gift>> latitudeMap;
        List<Gift> gifts;
        int size = 0;
        for (Range<Double> longitudeKeys : longitudeMap.asMapOfRanges().keySet()) {
            System.out.println(String.format("%6.1f", longitudeKeys.lowerEndpoint()) + " => " + String.format("%6.1f", longitudeKeys.upperEndpoint()));
            latitudeMap = longitudeMap.get(longitudeKeys.lowerEndpoint());
            for (Range<Double> latitudeKeys : latitudeMap.asMapOfRanges().keySet()) {
                gifts = latitudeMap.get(latitudeKeys.lowerEndpoint());
                size += gifts.size();
                System.out.println(String.format("    %6.1f", latitudeKeys.lowerEndpoint()) + " => " + String.format("%6.1f", latitudeKeys.upperEndpoint()) + ": " + String.format("%,5d", gifts.size()));

            }
        }
        System.out.println("Total: " + String.format("%,d", size));
    }
}
