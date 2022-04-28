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
        sb.append("[").append(trainID).append(": ").append(start.getStationID()).append("->").append(end.getStationID()).append("]");
        sb.append(" seat:").append(type).append(" remain:").append(seatTmp.left);
        sb.append(" distance:").append(Math.abs(start.getDistance() - end.getDistance()));
        sb.append(" price:").append(df.format(seatTmp.price));
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
