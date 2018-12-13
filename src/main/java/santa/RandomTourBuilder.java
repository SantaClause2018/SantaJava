package santa;

import com.google.common.base.Stopwatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static santa.Tour.MAX_SLEIGH_WEIGHT;
import static santa.Tour.cloneGiftList;

public class RandomTourBuilder {

    /**
     * Create one random solution
     *
     * @param gifts
     * @return
     */
    public static Solution  getTours(List<Gift> gifts) {

        Stopwatch stopwatch = Stopwatch.createStarted();

        /* Clone gift list (but not gifts itself) */
        ArrayList<Gift> tmpGiftSet = (ArrayList<Gift>) cloneGiftList(gifts);

        /* Shuffle */
        Collections.shuffle(tmpGiftSet);

        int currentTourId = 0;
        Tour tour = new Tour(currentTourId);
        Solution solution = new Solution();

        double currentSleighWeight = 0;

        for (Gift gift: tmpGiftSet) {
            currentSleighWeight += gift.getWeight();

            /* overweight => create new tour */
            if (currentSleighWeight > (MAX_SLEIGH_WEIGHT)) {

                /* add old */
                tour.calcWeariness();
                solution.addTour(tour);

                /* create new tour */
                currentTourId++;
                tour = new Tour(currentTourId);
                currentSleighWeight = gift.getWeight();
            }
            gift.setTour(currentTourId);
            tour.addGift(gift);
        }
        stopwatch.stop();

        return solution;
    }



}
