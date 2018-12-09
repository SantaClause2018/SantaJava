package santa;

import java.util.ArrayList;
import java.util.List;

public class PositionCell {
    private ArrayList<Gift> gifts;
    private double totalWeight;

    public PositionCell() {
        gifts = new ArrayList<Gift>();
        totalWeight = 0.0;
    }

    public void add(Gift gift) {
        gifts.add(gift);
        totalWeight += gift.getWeight();
    }

    public List<Gift> getList() {
        return gifts;
    }

    public double getTotalWeight() {
        return  totalWeight;
    }
}
