package santa;

public class Gift {
    private int id;
    private GiftPosition position;
    private double weight;

    public Gift() {

    }

    public Gift(int id, double latitude, double longitude, double weight) {
        this.id = id;
        this.position = new GiftPosition(latitude, longitude);
        this.weight = weight;
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

}
