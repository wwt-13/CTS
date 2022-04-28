package CTS;

public class TrainGatimaan extends Train {

    public TrainGatimaan(String[] argArr) {
        trainID = argArr[1];
        lineID = argArr[2];
        name = "Gatimaan";
        seat1.type = "SC";
        seat1.price = Double.parseDouble(argArr[3]);
        seat1.total = seat1.left = Integer.parseInt(argArr[4]);
        seat2.type = "HC";
        seat2.price = Double.parseDouble(argArr[5]);
        seat2.total = seat2.left = Integer.parseInt(argArr[6]);
        seat3.type = "SB";
        seat3.price = Double.parseDouble(argArr[7]);
        seat3.total = seat3.left = Integer.parseInt(argArr[8]);
    }

    public static boolean contains(String type) {
        return type.equals("SC") || type.equals("HC") || type.equals("SB");
    }
}
