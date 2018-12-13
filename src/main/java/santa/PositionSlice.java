package santa;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;

import java.util.Collections;
import java.util.List;

public class PositionSlice {

    public static int LONGITUDE_MIN  = -180;
    public static int LONGITUDE_MAX  = 180;
    public static int LONGITUDE_STEP = 5;

    RangeMap<Double, PositionCell> longitudeMap;

    /**
     * Constructor
     */
    public PositionSlice() {
        longitudeMap = TreeRangeMap.create();

        for (int i = LONGITUDE_MIN; i <= (LONGITUDE_MAX - LONGITUDE_STEP); i += LONGITUDE_STEP) {
            longitudeMap.put(Range.closed((double) i,  (double)i + LONGITUDE_STEP), new PositionCell());
        }
    }

    /**
     *
     * @param gift
     */
    public void add(Gift gift) {
        longitudeMap.get(gift.getLongitude()).add(gift);
    }

    public RangeMap<Double, PositionCell> getLongitudeMap() {
        return longitudeMap;
    }

    public void printMap() {
        RangeMap<Double, PositionCell> latitudeMap;
        PositionCell cell;
        List<Gift> gifts;
        int totalSize = 0;
        double weight = 0.0;


        /* Loop over slices => longitude-map using key (longitude range: <lower, upper>) */
        for (Range<Double> longitudeKeys : longitudeMap.asMapOfRanges().keySet()) {

            /* Get one slice */
            cell = longitudeMap.get(longitudeKeys.lowerEndpoint());

            gifts = cell.getList();
            totalSize += gifts.size();
            weight += cell.getTotalWeight();

            /* Print cell */
            System.out.print(String.format("%6.1f", longitudeKeys.lowerEndpoint()) + " => " + String.format("%6.1f", longitudeKeys.upperEndpoint()));
            System.out.print("   size: " + String.format("%,5d", gifts.size()));
            System.out.print("   weight: " + String.format("%,8.1f", cell.getTotalWeight()));
            if (cell.getTotalWeight() > 0.0) {
                System.out.print("   ratio:" + String.format("%,6.1f", cell.getTotalWeight() / gifts.size()));
            }
            System.out.println("");
        }
        System.out.println("Total   size: " + String.format("%,8d", totalSize) + "   weight: " + String.format("%,10.1f", weight));
    }

    public void sort() {
        PositionCell cell;
        List<Gift> gifts;

        /* Loop over slices => longitude-map using key (longitude range: <lower, upper>) */
        for (Range<Double> longitudeKeys : longitudeMap.asMapOfRanges().keySet()) {

            /* Get one slice */
            cell  = longitudeMap.get(longitudeKeys.lowerEndpoint());

            /* Get gifts of one cell */
            gifts = cell.getList();

            /* Sort */
            Collections.sort(gifts, (o1, o2) -> {
                if (o1.getPosition().getLatitude() >= o2.getPosition().getLatitude()) {
                    return -1;
                } else {
                    return 1;
                }
            });

        }
    }
}
