package santa;

import java.util.ArrayList;
import java.util.List;

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


    public int getNumberOfTours() {
        return tours.size();
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


}
