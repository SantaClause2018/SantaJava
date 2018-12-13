package santa;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;

import java.util.*;

public class PositionNet {

    public static int LONGITUDE_MIN  = -180;
    public static int LONGITUDE_MAX  = 180;
    public static int LONGITUDE_STEP = 5;

    public static int LATITUDE_MIN  = -90;
    public static int LATITUDE_MAX  = 90;
    public static int LATITUDE_STEP = 5;

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

    public RangeMap<Double, RangeMap<Double, PositionCell>> getLongitudeMap() {
        return longitudeMap;
    }

    public void printMap() {
        RangeMap<Double, PositionCell> latitudeMap;
        PositionCell cell;
        List<Gift> gifts;
        int totalSize = 0;
        int sliceSize = 0;
        double weight = 0.0;


        /* Loop over slices => longitude-map using key (longitude range: <lower, upper>) */
        for (Range<Double> longitudeKeys : longitudeMap.asMapOfRanges().keySet()) {
            sliceSize = 0;

            /* Get one slice */
            latitudeMap = longitudeMap.get(longitudeKeys.lowerEndpoint());

            /* Print slice */
            System.out.println(String.format("%6.1f", longitudeKeys.lowerEndpoint()) + " => " + String.format("%6.1f", longitudeKeys.upperEndpoint()));

            /* Loop over cells => latitude-map using key (latitude range: <lower, upper>) */
            for (Range<Double> latitudeKeys : latitudeMap.asMapOfRanges().keySet()) {

                /* Get one cell */
                cell = latitudeMap.get(latitudeKeys.lowerEndpoint());

                gifts = cell.getList();
                sliceSize += gifts.size();
                weight += cell.getTotalWeight();

                /* Print cell */
                System.out.print(String.format("    %6.1f", latitudeKeys.lowerEndpoint()) + " => " + String.format("%6.1f", latitudeKeys.upperEndpoint()));
                System.out.print("   size: " + String.format("%,5d", gifts.size()));
                System.out.print("   weight: " + String.format("%,8.1f", cell.getTotalWeight()));
                if (cell.getTotalWeight() > 0.0) {
                    System.out.print("   ratio:" + String.format("%,6.1f", cell.getTotalWeight() / gifts.size()));
                }
                System.out.println("");

            }
            totalSize += sliceSize;
            //System.out.println("   slice-size: " + sliceSize);
        }
        System.out.println("Total   size: " + String.format("%,8d", totalSize) + "   weight: " + String.format("%,10.1f", weight));
    }

    public void sort() {
        RangeMap<Double, PositionCell> latitudeMap;
        PositionCell cell;
        List<Gift> gifts;

        /* Loop over slices => longitude-map using key (longitude range: <lower, upper>) */
        for (Range<Double> longitudeKeys : longitudeMap.asMapOfRanges().keySet()) {

            /* Get one slice */
            latitudeMap = longitudeMap.get(longitudeKeys.lowerEndpoint());

            for (Range<Double> latitudeKeys : latitudeMap.asMapOfRanges().keySet()) {

                /* Get one cell */
                cell = latitudeMap.get(latitudeKeys.lowerEndpoint());

                /* Get gifts of one cell */
                gifts = cell.getList();

                /* Sort */
                Collections.sort(gifts, (o1, o2) -> {
                    if (o1.getPosition().getLatitude() < o2.getPosition().getLatitude()) {
                        return -1;
                    } else {
                        return 1;
                    }
                });
            }

        }
    }
}
