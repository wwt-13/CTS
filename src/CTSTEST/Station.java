package CTSTEST;

public class Station {
    private String stationID;
    private int distance;

    public Station(String stationID, int distance) {
        this.stationID = stationID;
        this.distance = distance;
    }

    public String getStationID() {
        return stationID;
    }

    public int getDistance() {
        return distance;
    }
}
