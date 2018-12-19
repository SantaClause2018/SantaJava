package santa;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {

    private List<Tour> tours;
    private double totalWeariness = -1;
    private double totalLength = -1;

    public Solution () {
        tours = new ArrayList<>();
    }


    public void addTour(Tour tour) {
        tours.add(tour);
    }

    public Tour removeTour(int index) {
        return tours.remove(index);
    }

    public Tour getTour(int index) {
        return tours.get(index);
    }


    public int tourCnt() {
        return tours.size();
    }

    public boolean containsTour(int index) {
        for (Tour tour : tours) {
            if (tour.getTourId() == index) {
                return true;
            }
        }
        return false;
    }

    public double calculateTotalWeariness() {
        totalWeariness = 0;
        totalLength = 0;

        for (Tour tour: tours) {
            totalWeariness += tour.calcWeariness();
            totalLength += tour.getTotalDistanceKm();
        }
        return totalWeariness;
    }

    public double getTotalWeariness() {
        return totalWeariness;
    }

    public double getTotalLength() {
        return totalLength;
    }

    public List<Double> getSleighWeights() {
        List<Double> weights = new ArrayList<>();
        for (Tour tour: tours) {
            weights.add(tour.getSleighWeight());
        }
        return weights;
    }

    public List<Double> getTourLengthsKm() {
        List<Double> lengths = new ArrayList<>();
        for (Tour tour: tours) {
            lengths.add(tour.getTotalDistanceKm());
        }
        return lengths;
    }

    public List<Double> getWeariness() {
        List<Double> weariness = new ArrayList<>();
        for (Tour tour: tours) {
            weariness.add(tour.getWeariness());
        }
        return weariness;
    }

    public static Solution fromSubmission(List<Pair<Integer,Integer>> submission, List<Gift> gifts) {
        Solution solution = new Solution();
        Map<Integer, Tour> tmpTours = new HashMap<>();
        List<Gift> clonedGifts = Tour.cloneGiftList(gifts);

        for (Pair<Integer,Integer> pair: submission ) {
            int tourId = pair.getValue();
            if ( !tmpTours.containsKey(tourId)) {
                tmpTours.put(tourId, new Tour(tourId));
            }
            clonedGifts.get(pair.getKey()-1).setTour(tourId);
            tmpTours.get(tourId).addGift( clonedGifts.get(pair.getKey()-1));
        }
        for (Map.Entry<Integer,Tour> entry : tmpTours.entrySet()) {
            solution.addTour(entry.getValue());

        }
        return solution;
    }


}
