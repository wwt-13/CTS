package CTS;

public class TrainNormal extends Train {

    public TrainNormal(String[] argArr) {
        trainID = argArr[1];
        lineID = argArr[2];
        name = "Normal";
        seat1 = new Seat("CC", Double.parseDouble(argArr[3]),
                         Integer.parseInt(argArr[4]), Integer.parseInt(argArr[4]));
        seat2 = new Seat("SB", Double.parseDouble(argArr[5]),
                         Integer.parseInt(argArr[6]), Integer.parseInt(argArr[6]));
        seat3 = new Seat("GG", Double.parseDouble(argArr[7]),
                         Integer.parseInt(argArr[8]), Integer.parseInt(argArr[8]));
    }

    public static boolean contains(String type) {
        return type.equals("CC") || type.equals("SB") || type.equals("GG");
    }
}
