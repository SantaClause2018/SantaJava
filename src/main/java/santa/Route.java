package santa;

import java.util.List;

public class Route {

    private static final double BASE_WEIGHT = 10.0;

    private List<Gift> gifts;
    private double totalDistance;       /* north pole to north pole distance */
    private double weariness;           /* weighted reindeer weariness */



    public void calcWeariness() {

        // dist(north-pole -> A) * (BASE_WEIGHT + Gift A + Gift B) +
        // dist(A - > B)         * (BASE_WEIGHT + Gift A) +
        // dist(B -> north-pole) * ((BASE_WEIGHT)
    }
}
