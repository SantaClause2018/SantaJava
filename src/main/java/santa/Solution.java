package santa;

import java.util.ArrayList;
import java.util.List;

public class Solution {

    private List<Tour> tours;
    private double totalWeariness = 0;
    private double totalLength = 0;

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
            totalLength += tour.getTotalDistance();
        }
        return totalWeariness;
    }

    public double getTotalWeariness() {
        return totalWeariness;
    }

    public double getTotalLength() {
        return totalLength;
    }
}
