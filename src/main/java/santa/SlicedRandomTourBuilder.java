package santa;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static santa.Tour.EMPTY_SLEIGH_WEIGHT;
import static santa.Tour.MAX_SLEIGH_WEIGHT;

public class SlicedRandomTourBuilder {

    public static Solution getTours(List<Gift> gifts) {

        Stopwatch stopwatch = Stopwatch.createStarted();

        //PositionNet net = new PositionNet();
        PositionSlice slices = new PositionSlice();

        /* distance to all other neighbours */
        if (false) {
            int i;
            int k;

            Gift first;
            Gift second;
            List<GiftNeighbour> neighbours;
            for (i = 0; i < gifts.size(); i++) {
                first = gifts.get(i);
                neighbours = first.getNeighbours();
                slices.add(first);
                for (k = i + 1; k < gifts.size(); k++) {
                    second = gifts.get(k);
                    if (!first.equals(second)) {
                        neighbours.add(new GiftNeighbour(first, second));
                    }
                }

                System.out.println(String.format("%6d", i) + " elapsed=" + stopwatch.elapsed(TimeUnit.SECONDS));
            }
        }

        /* grid */
        for (Gift gift : gifts) {
            slices.add(gift);
        }
        slices.sort();
        //net.printMap();

        RangeMap<Double, PositionCell> latitudeMap;
        PositionCell cell;
        List<Gift> cellGifts;
        int i;

        int currentTour = 0;
        double currentSleighWeight = 0;
        Tour tour = new Tour(currentTour);
        Solution solution = new Solution();

        /* Loop over slices => longitude-map using key (longitude range: <lower, upper>) */
        for (Range<Double> longitudeKeys : slices.getLongitudeMap().asMapOfRanges().keySet()) {

            /* Get one slice */
            cell = slices.getLongitudeMap().get(longitudeKeys.lowerEndpoint());

            for (i = 0; i < cell.getList().size(); i++) {
                Gift gift = cell.getList().get(i);


                currentSleighWeight += gift.getWeight();

                /* overweight => create new tour */
                if (currentSleighWeight > MAX_SLEIGH_WEIGHT) {

                    /* add old */
                    solution.addTour(tour);

                    /* create new tour */
                    currentTour++;
                    tour = new Tour(currentTour);
                    currentSleighWeight = gift.getWeight();
                }

                gift.setTour(currentTour);
                tour.addGift(gift);
            }
        }
        solution.addTour(tour); // last tour

        stopwatch.stop();
        System.out.println("stop=" + stopwatch.elapsed(TimeUnit.MILLISECONDS));

        return solution;
    }
}
