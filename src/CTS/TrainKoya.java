package CTS;

public class TrainKoya extends Train {

    public TrainKoya(String[] argArr) {
        trainID = argArr[1];
        lineID = argArr[2];
        name = "Koya";
        seat1.type = "1A";
        seat1.price = Double.parseDouble(argArr[3]);
        seat1.total = seat1.left = Integer.parseInt(argArr[4]);
        seat2.type = "2A";
        seat2.price = Double.parseDouble(argArr[5]);
        seat2.total = seat2.left = Integer.parseInt(argArr[6]);
    }

    public static boolean contains(String type) {
        return type.equals("1A") || type.equals("2A");
    }
}
