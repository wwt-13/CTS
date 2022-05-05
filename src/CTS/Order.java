package CTS;

import java.text.DecimalFormat;

public class Order {
    public String trainID;
    public String start;
    public String end;
    public String seatType;
    public int num;

    public Order(String trainID, String start, String end, String seatType, int num) {
        this.trainID = trainID;
        this.start = start;
        this.end = end;
        this.seatType = seatType;
        this.num = num;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        DecimalFormat df = new DecimalFormat(".00");
        Train train = Date.getTrainDate().get(trainID);
        Line line = Date.getLineDate().get(train.lineID);
        int distance = Math.abs(line.getStations().get(start).getDistance() - line.getStations().get(end).getDistance());
        sb.append("[").append(trainID).append(": ").append(start).append("->").append(end);
        sb.append(" seat:").append(seatType);
        sb.append(" num:").append(num);
        sb.append(" price:").append(df.format(distance * train.seat(seatType).price));
        return sb.toString();
    }
}
