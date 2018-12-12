package santa;

import com.google.common.base.Stopwatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static santa.Main.MAX_SLEIGH_WEIGHT;

public class RandomTourBuilder {



    public static List<Gift>  getTours(List<Gift> gifts) {

        Stopwatch stopwatch = Stopwatch.createStarted();
        ArrayList<Gift> curSet = (ArrayList<Gift>) cloneGiftList(gifts);
        Collections.shuffle(curSet);
        List<Double> tourLengths = new ArrayList<Double>();
        tourLengths.add(0.0);
        int currentTour = 0;
        double currentSleighWeight = 0.0;

        for(int i = 0; i< curSet.size(); i++) {
            Gift curGift = curSet.get(i);
            curGift.setTour(currentTour);
            currentSleighWeight += curGift.getWeight();
            if (currentSleighWeight > MAX_SLEIGH_WEIGHT) {
                tourLengths.set(currentTour, curGift.getNorthPoleDistance()); // close tour
                currentTour++;
                curGift.setTour(currentTour);
                currentSleighWeight = curGift.getWeight();
                tourLengths.add(0.0);
            }

            // update tour length
            if (tourLengths.get(currentTour) <=0.0) {
                // is first gift on tour
                tourLengths.set(currentTour,curGift.getNorthPoleDistance());
            }
            else {
                tourLengths.set(currentTour, Haversine.approximateDistance( curSet.get(i-1).getLatitude(),curSet.get(i-1).getLongitude(),curGift.getLatitude(), curGift.getLongitude()));
            }
        }

        // close last tour
        tourLengths.set(currentTour, curSet.get(curSet.size()-1).getNorthPoleDistance()); // close tour
        //curSet.sort((g1, g2) -> Integer.compare(g1.getId(), g2.getId()));

        // calculate total tour length
        double totalLenght = 0;
        for (double tour: tourLengths) {
            totalLenght += tour;
        }

        System.out.println("Random solution: number of tours: " + (currentTour+1) + ", total tour length: " + totalLenght + ", time to build solution: " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms" );


        return curSet;
    }




    public static List<Gift> cloneGiftList(List<Gift> originalGifts) {
        ArrayList<Gift> clonedGifts = new ArrayList<Gift>(originalGifts.size());

        for (Gift g : originalGifts) {
            clonedGifts.add(g);
        }
        return clonedGifts;
    }

}
