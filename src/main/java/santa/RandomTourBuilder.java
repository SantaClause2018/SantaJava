package santa;

import com.google.common.base.Stopwatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static santa.Main.EMPTY_SLEIGH_WEIGHT;
import static santa.Main.MAX_SLEIGH_WEIGHT;

public class RandomTourBuilder {

    /**
     * Create one random solution
     *
     * @param gifts
     * @return
     */
    public static List<Gift>  getTours(List<Gift> gifts) {

        Stopwatch stopwatch = Stopwatch.createStarted();

        /* Clone gift list (but not gifts itself) */
        ArrayList<Gift> curSet = (ArrayList<Gift>) cloneGiftList(gifts);

        /* Shuffle */
        Collections.shuffle(curSet);

        /* Create a list of tour lengths.
         * One tour consists of northpole -> A -> B -> ... -> Z -> northpole */
        List<Double> tourLengths = new ArrayList<Double>();
        tourLengths.add(0.0);
        int currentTour = 0;
        double currentSleighWeight = EMPTY_SLEIGH_WEIGHT;

        for (int i = 0; i < curSet.size(); i++) {
            Gift curGift = curSet.get(i);
            curGift.setTour(currentTour);
            currentSleighWeight += curGift.getWeight();

            /* overweight => create new tour */
            if (currentSleighWeight > MAX_SLEIGH_WEIGHT) {
                /* close tour */
                tourLengths.set(currentTour, tourLengths.get(currentTour) + curGift.getNorthPoleDistance());
                System.out.println("tour " + currentTour + " has lenght " + tourLengths.get(currentTour));

                /* create new tour */
                currentTour++;
                curGift.setTour(currentTour);
                currentSleighWeight = EMPTY_SLEIGH_WEIGHT + curGift.getWeight();
                tourLengths.add(0.0);
            }

            // update tour length
            if (tourLengths.get(currentTour) <=0.0) {
                /* is first gift on tour => add northpole -> first gift */
                tourLengths.set(currentTour, curGift.getNorthPoleDistance());
            } else {
                /* add distance A -> B */
                tourLengths.set(currentTour, tourLengths.get(currentTour) +
                    Haversine.distance(
                        curSet.get(i-1).getLatitude(), curSet.get(i-1).getLongitude(),
                        curGift.getLatitude(), curGift.getLongitude()
                    )
                );
            }
        }

        // close last tour
        tourLengths.set(currentTour, tourLengths.get(currentTour) +
                curSet.get(curSet.size()-1).getNorthPoleDistance()); // close tour
        //curSet.sort((g1, g2) -> Integer.compare(g1.getId(), g2.getId()));

        // calculate total tour length
        double totalLenght = 0;
        for (double tour: tourLengths) {
            totalLenght += tour;
        }

        stopwatch.stop();
        System.out.println("Random solution: number of tours: " + (currentTour+1) + ", total tour length: " + totalLenght + ", time to build solution: " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms" );

        return curSet;
    }

    /**
     * Clone the list but not the gifts inside!4
     *
     * @param originalGifts
     * @return
     */
    public static List<Gift> cloneGiftList(List<Gift> originalGifts) {
        ArrayList<Gift> clonedGifts = new ArrayList<Gift>(originalGifts.size());

        for (Gift g : originalGifts) {
            clonedGifts.add(g);
        }
        return clonedGifts;
    }

}
