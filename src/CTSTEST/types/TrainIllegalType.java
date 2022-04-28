package CTSTEST.types;

public enum TrainIllegalType {
    TRAIN_SERIAL_ILLEGAL("Train serial illegal"), TRAIN_SERIAL_DUPLICATE("Train serial duplicate"),
    TRAIN_NOT_EXIST("Train does not exist"), PRICE_ILLEGAL("Price illegal"),
    TICKET_NUM_ILLEGAL("Ticket num illegal"), TRAIN_SERIAL_NOT_EXIST("Train serial does not exist"),
    SEAT_NOT_MATCH("Seat does not match");
    private final String value;

    private TrainIllegalType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
