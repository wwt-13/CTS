package Test;

import java.util.HashMap;

public class Tmp4 {
    public static void main(String[] args) {
        HashMap<String, Father> tmp = new HashMap<>();
        tmp.put("123", new Father());
        Father f = tmp.get("1234");
        if (f == null) {
            System.out.println("123");
        }
    }
}
