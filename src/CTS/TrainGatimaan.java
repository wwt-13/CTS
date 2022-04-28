package CTS;

public class TrainGatimaan extends Train {

    public TrainGatimaan(String[] argArr) {
        trainID = argArr[1];
        lineID = argArr[2];
        name = "Gatimaan";
        seat1 = new Seat("SC", Double.parseDouble(argArr[3]),
                         Integer.parseInt(argArr[4]), Integer.parseInt(argArr[4]));
        seat2 = new Seat("HC", Double.parseDouble(argArr[5]),
                         Integer.parseInt(argArr[6]), Integer.parseInt(argArr[6]));
        seat3 = new Seat("SB", Double.parseDouble(argArr[7]),
                         Integer.parseInt(argArr[8]), Integer.parseInt(argArr[8]));
    }

    public static boolean contains(String type) {
        return type.equals("SC") || type.equals("HC") || type.equals("SB");
    }
}
