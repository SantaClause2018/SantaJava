package santa;

import java.util.ArrayList;
import java.util.List;

public class GiftPosition {

    private double latitude;
    private double longitude;
    private double northPoleDistance;
    private List<GiftNeighbour> neighbours;

    public GiftPosition() {
        this(0.0, 0.0);
    }

    public GiftPosition(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.northPoleDistance = Haversine.distance(90.0, 0, latitude, longitude);
        this.neighbours = new ArrayList<>();
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getNorthPoleDistance() {
        return northPoleDistance;
    }

    public List<GiftNeighbour> getNeighbours() {
        return neighbours;
    }

    public double getHaversineDistance(GiftPosition giftPos2) {
        return Haversine.getHaversineDistance(this, giftPos2);
    }
}
