package Test;

import java.util.HashMap;

public class Tmp4 {
    public static void main(String[] args) {
        HashMap<String, String> tmp = new HashMap<>();
        tmp.put("123", "123");
        String t = tmp.get("123");
        String t2 = tmp.get("2134");
        System.out.println(t);
        System.out.println(t2);
    }
}
