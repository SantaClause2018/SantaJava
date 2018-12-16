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

    private double totalDistanceKm = -1;       /* north pole to north pole distance */
    private double sleighWeight = 0;         /* weight of the sleigh at the start of the tour */
    private double weariness = -1;           /* weighted reindeer weariness */
    private int tourId = NOT_ASSIGNED_TO_TOUR; /* number of this tour */
    private double runtimeWearinessCalcMicSec = -1;

    public Tour(List<Gift> giftsOnTour) {
        this.gifts = giftsOnTour;
        if (gifts.size() > 0) {
            this.tourId = gifts.get(0).getTour();
        }
    }

    public Tour(int tourId) {
        this.tourId = tourId;
        this.gifts = new ArrayList<>();
    }

    public void addGift(Gift gift) {
        gifts.add(gift);
    }

    public Gift removeGift(int index) {
        return gifts.remove(index);
    }

    public Gift getGift(int index) { return gifts.get(index); }

    public int size() {
        return gifts.size();
    }


    public double calcWeariness() {


        Stopwatch stopwatch = Stopwatch.createStarted();
        // go backwards through the tour
        Gift firstGift = gifts.get(0);
        Gift lastGift = gifts.get(gifts.size()-1);
        sleighWeight = EMPTY_SLEIGH_WEIGHT;

        double segmentDistance = lastGift.getNorthPoleDistance();
        totalDistanceKm += segmentDistance;
        weariness = sleighWeight * segmentDistance;

        if (gifts.size() > 1) {
            for (int i = gifts.size() - 1; i > 0; i--) {
                sleighWeight += gifts.get(i).getWeight();
                segmentDistance = Haversine.approximateDistance(gifts.get(i).getLatitude(),
                        gifts.get(i).getLongitude(), gifts.get(i - 1).getLatitude(),
                        gifts.get(i - 1).getLongitude());
                totalDistanceKm += segmentDistance;
                weariness += sleighWeight * segmentDistance;
            }
        }
        sleighWeight += firstGift.getWeight();
        segmentDistance = firstGift.getNorthPoleDistance();
        totalDistanceKm += segmentDistance;
        weariness += sleighWeight * segmentDistance;


        stopwatch.stop();

        runtimeWearinessCalcMicSec =  stopwatch.elapsed(TimeUnit.MICROSECONDS);
        // dist(north-pole -> A) * (BASE_WEIGHT + Gift A + Gift B) +
        // dist(A - > B)         * (BASE_WEIGHT + Gift A) +
        // dist(B -> north-pole) * ((BASE_WEIGHT)

        return weariness;
    }

    public double getTotalDistanceKm() {
        return totalDistanceKm;
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

    public double getRuntimeWearinessCalcMicSec() { return runtimeWearinessCalcMicSec; }


    /**
     * Clone the list but not the gifts inside!4
     *
     * @param originalGifts
     * @return
     */
    public static List<Gift> cloneGiftList(List<Gift> originalGifts) {
        ArrayList<Gift> clonedGifts = new ArrayList<>(originalGifts.size());

        for (Gift g : originalGifts) {
            Gift clonedGift = new Gift(g.getId(), g.getLatitude(), g.getLongitude(), g.getWeight(), g.getTour());
            clonedGifts.add(clonedGift);
        }
        return clonedGifts;
    }
}
