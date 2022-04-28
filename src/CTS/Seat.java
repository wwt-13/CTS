package CTS;


import java.text.DecimalFormat;

public class Seat {
    public String type;
    public double price;
    public int total;
    public int left;

    public Seat(String type, double price, int total, int left) {
        this.type = type;
        this.price = price;
        this.total = total;
        this.left = left;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat(".00");
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(type).append("]").append(df.format(price)).append(":").append(left);
        return sb.toString();
    }
}
