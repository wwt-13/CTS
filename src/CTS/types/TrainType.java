package CTS.types;

public enum TrainType {
    NORMAL, GATIMAAN, KOYA, TRAINNOTFOUND;

    public static TrainType getInstance(String s) {
        return switch (s.charAt(0)) {
            case '0' -> NORMAL;
            case 'G' -> GATIMAAN;
            case 'K' -> KOYA;
            default -> TRAINNOTFOUND;
        };
    }
}
