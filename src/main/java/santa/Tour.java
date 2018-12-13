package santa;

import com.google.common.base.Stopwatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static santa.Gift.NOT_ASSIGNED_TO_TOUR;



public class Tour {

    public static final double MAX_SLEIGH_WEIGHT = 1000;
    public static final double EMPTY_SLEIGH_WEIGHT = 10;

    private List<Gift> gifts;

    private double totalDistance = -1;       /* north pole to north pole distance */
    private double sleighWeight = 0;         /* weight of the sleigh at the start of the tour */
    private double weariness = -1;           /* weighted reindeer weariness */
    private int tourId = NOT_ASSIGNED_TO_TOUR; /* number of this tour */

    public Tour(List<Gift> giftsOnTour) {
        this.gifts = giftsOnTour;
        if (gifts.size() > 0) {
            this.tourId = gifts.get(0).getTour();
        }
    }

    public Tour() {
        this.gifts = new ArrayList<Gift>();
    }

    public void addGift(Gift gift) {
        gifts.add(gift);
    }

    public Gift removeGift(int index) {
        return gifts.remove(index);
    }


    public double calcWeariness() {


        Stopwatch stopwatch = Stopwatch.createStarted();
        // go backwards through the tour
        Gift firstGift = gifts.get(0);
        Gift lastGift = gifts.get(gifts.size()-1);
        sleighWeight = EMPTY_SLEIGH_WEIGHT;

        double segmentDistance = lastGift.getNorthPoleDistance();
        totalDistance += segmentDistance;
        weariness = sleighWeight * segmentDistance;

        if (gifts.size() > 1) {
            for (int i = gifts.size() - 1; i > 0; i--) {
                sleighWeight += gifts.get(i).getWeight();
                segmentDistance = Haversine.approximateDistance(gifts.get(i).getLatitude(),
                        gifts.get(i).getLongitude(), gifts.get(i - 1).getLatitude(),
                        gifts.get(i - 1).getLongitude());
                totalDistance += segmentDistance;
                weariness += sleighWeight * segmentDistance;
            }
        }
        sleighWeight += firstGift.getWeight();
        segmentDistance = firstGift.getNorthPoleDistance();
        totalDistance += segmentDistance;
        weariness += sleighWeight * segmentDistance;


        System.out.println(" reindeer weariness of tour " + tourId + " : " + weariness);
        System.out.println("runtime to calc. weariness: " + stopwatch.elapsed(TimeUnit.MILLISECONDS));
        // dist(north-pole -> A) * (BASE_WEIGHT + Gift A + Gift B) +
        // dist(A - > B)         * (BASE_WEIGHT + Gift A) +
        // dist(B -> north-pole) * ((BASE_WEIGHT)

        return weariness;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public double getSleighWeight() {
        return sleighWeight;
    }

    public double getWeariness() {
        return weariness;
    }

    public int getTourId() {
        return  tourId;
    }
}
