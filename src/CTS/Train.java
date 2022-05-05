package CTS;

import java.text.DecimalFormat;
import java.util.Arrays;

public class Train {
    protected String trainID;
    protected String lineID;
    protected String name;
    protected Seat seat1;
    protected Seat seat2;
    protected Seat seat3;

    public Seat seat(String type) {
        /*
         * 利用Arrays创建的临时集合最适合与这种遍历判断的情况(集合不可变并且开销小)
         * */
        for (Seat seat : Arrays.asList(seat1, seat2, seat3)) {
            if (seat.type.equals(type)) {
                return seat;
            }
        }
        return null;
    }

    public void output(Station start, Station end, String type) {
        DecimalFormat df = new DecimalFormat(".00");
        StringBuilder sb = new StringBuilder();
        Seat seatTmp = seat(type);
        int dis = Math.abs(start.getDistance() - end.getDistance());
        sb.append("[").append(trainID).append(": ").append(start.getStationID()).append("->").append(end.getStationID()).append("]");
        sb.append(" seat:").append(type).append(" remain:").append(seatTmp.left);
        sb.append(" distance:").append(dis);
        sb.append(" price:").append(df.format(dis * seatTmp.price));
        System.out.println(sb);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(trainID).append(": ").append(lineID);
        for (Seat seat : Arrays.asList(seat1, seat2, seat3)) {
            if (seat != null) {
                sb.append(" ").append(seat);
            }
        }
        return sb.toString();
    }
}

class TrainNormal extends Train {

    public TrainNormal(String[] argArr) {
        trainID = argArr[1];
        lineID = argArr[2];
        name = "Normal";
        seat1 = new Seat("CC", Double.parseDouble(argArr[3]), Integer.parseInt(argArr[4]), Integer.parseInt(argArr[4]));
        seat2 = new Seat("SB", Double.parseDouble(argArr[5]), Integer.parseInt(argArr[6]), Integer.parseInt(argArr[6]));
        seat3 = new Seat("GG", Double.parseDouble(argArr[7]), Integer.parseInt(argArr[8]), Integer.parseInt(argArr[8]));
    }

    public static boolean contains(String type) {
        return type.equals("CC") || type.equals("SB") || type.equals("GG");
    }
}

class TrainGatimaan extends Train {

    public TrainGatimaan(String[] argArr) {
        trainID = argArr[1];
        lineID = argArr[2];
        name = "Gatimaan";
        seat1 = new Seat("SC", Double.parseDouble(argArr[3]), Integer.parseInt(argArr[4]), Integer.parseInt(argArr[4]));
        seat2 = new Seat("HC", Double.parseDouble(argArr[5]), Integer.parseInt(argArr[6]), Integer.parseInt(argArr[6]));
        seat3 = new Seat("SB", Double.parseDouble(argArr[7]), Integer.parseInt(argArr[8]), Integer.parseInt(argArr[8]));
    }

    public static boolean contains(String type) {
        return type.equals("SC") || type.equals("HC") || type.equals("SB");
    }
}

class TrainKoya extends Train {

    public TrainKoya(String[] argArr) {
        trainID = argArr[1];
        lineID = argArr[2];
        name = "Koya";
        seat1 = new Seat("1A", Double.parseDouble(argArr[3]), Integer.parseInt(argArr[4]), Integer.parseInt(argArr[4]));
        seat2 = new Seat("2A", Double.parseDouble(argArr[5]), Integer.parseInt(argArr[6]), Integer.parseInt(argArr[6]));
    }

    public static boolean contains(String type) {
        return type.equals("1A") || type.equals("2A");
    }
}