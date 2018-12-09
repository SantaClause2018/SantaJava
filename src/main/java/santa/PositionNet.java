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

    RangeMap<Double, RangeMap<Double, PositionCell>> longitudeMap;

    /**
     * Helper function to construct PositionNet
     * @return RangeMap of Latitude per Longitude
     */
    private static RangeMap<Double, PositionCell> create() {
        RangeMap<Double, PositionCell> latitudeMap = TreeRangeMap.create();

        for (int i = LATITUDE_MIN; i <= (LATITUDE_MAX - LATITUDE_STEP); i += LATITUDE_STEP) {
            latitudeMap.put(Range.closed((double) i,  (double)i + LATITUDE_STEP), new PositionCell());
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
        RangeMap<Double, PositionCell> latitudeMap;
        PositionCell cell;
        List<Gift> gifts;
        int size = 0;
        double weight = 0.0;
        for (Range<Double> longitudeKeys : longitudeMap.asMapOfRanges().keySet()) {
            System.out.println(String.format("%6.1f", longitudeKeys.lowerEndpoint()) + " => " + String.format("%6.1f", longitudeKeys.upperEndpoint()));
            latitudeMap = longitudeMap.get(longitudeKeys.lowerEndpoint());
            for (Range<Double> latitudeKeys : latitudeMap.asMapOfRanges().keySet()) {
                cell = latitudeMap.get(latitudeKeys.lowerEndpoint());
                gifts = cell.getList();
                size += gifts.size();
                weight += cell.getTotalWeight();
                System.out.print(String.format("    %6.1f", latitudeKeys.lowerEndpoint()) + " => " + String.format("%6.1f", latitudeKeys.upperEndpoint()));
                System.out.print("   size: " + String.format("%,5d", gifts.size()));
                System.out.print("   weight: " + String.format("%,8.1f", cell.getTotalWeight()));
                if (cell.getTotalWeight() > 0.0) {
                    System.out.print("   ratio:" + String.format("%,6.1f", cell.getTotalWeight() / gifts.size()));
                }
                System.out.println("");

            }
        }
        System.out.println("Total   size: " + String.format("%,8d", size) + "   weight: " + String.format("%,10.1f", weight));
    }
}
