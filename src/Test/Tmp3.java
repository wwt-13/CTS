package Test;

import java.text.DecimalFormat;

public class Tmp3 {
    public static void main(String[] args) {
        var df = new DecimalFormat(".00");
        System.out.println(df.format(123.1292413));
        StringBuilder sb = new StringBuilder("132");
        System.out.println(sb);
    }
}
