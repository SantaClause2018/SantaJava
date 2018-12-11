package santa;

import java.util.List;

public class Gift {

    public static final int NOT_ASSIGNED_TO_TOUR = -1;


    private int id;
    private GiftPosition position;
    private double weight;
    private int tour;

    public Gift() {
        this(0, 0.0, 0.0, 0.0, 0);
    }

    public Gift(int id, double latitude, double longitude, double weight, int tour) {
        this.id = id;
        this.position = new GiftPosition(latitude, longitude);
        this.weight = weight;
        this.tour = tour;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GiftPosition getPosition() {
        return position;
    }

    public void setPosition(GiftPosition position) {
        this.position = position;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getTour() { return tour; }

    public void setTour(int tour) { this.tour = tour;}

    public void print() {
        System.out.println("id=" + id + " weight=" + weight + " northpoleDist=" + position.getNorthPoleDistance());
        for (GiftNeighbour neighbour : position.getNeighbours()) {
            System.out.println("    id=" + neighbour.getSecond().getId() + " dist=" + neighbour.getDistance());
        }
    }

    /**
     *
     */

    public double getLatitude() {
        return position.getLatitude();
    }

    public void setLatitude(double latitude) {
        position.setLatitude(latitude);
    }

    public double getLongitude() {
        return position.getLongitude();
    }

    public void setLongitude(double longitude) {
        position.setLongitude(longitude);
    }

    public double getNorthPoleDistance() {
        return position.getNorthPoleDistance();
    }

    public List<GiftNeighbour> getNeighbours() {
        return position.getNeighbours();
    }

}
