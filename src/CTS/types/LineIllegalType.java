package CTS.types;

public enum LineIllegalType {
    STATION_DUPLICATE("Station duplicate"), LINE_EXIST("Line already exists"),
    LINE_NOT_EXIST("Line does not exist"), CAPACITY_ILLEGAL("Capacity illegal"),
    STATION_NOT_EXIST("Station does not exist"), LINE_CAPACITY_ILLEGAL("Line illegal");
    private final String value;

    private LineIllegalType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
