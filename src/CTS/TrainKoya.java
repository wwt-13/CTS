package CTS;

public class TrainKoya extends Train {

    public TrainKoya(String[] argArr) {
        trainID = argArr[1];
        lineID = argArr[2];
        name = "Koya";
        seat1 = new Seat("1A", Double.parseDouble(argArr[3]),
                         Integer.parseInt(argArr[4]), Integer.parseInt(argArr[4]));
        seat2 = new Seat("2A", Double.parseDouble(argArr[5]),
                         Integer.parseInt(argArr[6]), Integer.parseInt(argArr[6]));
    }

    public static boolean contains(String type) {
        return type.equals("1A") || type.equals("2A");
    }
}
