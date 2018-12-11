package santa;

public class GiftNeighbour {
    private double distance;
    private Gift first;
    private Gift second;

    public GiftNeighbour(Gift first, Gift second) {
        this.first = first;
        this.second = second;
        this.distance = Haversine.distance(
            first.getLatitude(), first.getLongitude(),
            second.getLatitude(), second.getLongitude()
        );
    }

    public double getDistance() {
        return distance;
    }

    public Gift getFirst() {
        return first;
    }

    public Gift getSecond() {
        return second;
    }
}
