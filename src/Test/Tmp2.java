package Test;

import java.util.*;

public class Tmp2 {
    public static void main(String[] args) {
        HashMap<String, Station> test = new HashMap<>();
        test.put("hello", new Station(12, "hello"));
        test.put("hello1", new Station(1, "hello1"));
        test.put("hello2", new Station(132, "hello2"));
        test.put("hello3", new Station(32, "hello3"));
        List<Station> list = new ArrayList<Station>(test.values());
        Collections.sort(list, new Comparator<Station>() {
            @Override
            public int compare(Station s1, Station s2) {
                return s1.distance - s2.distance;
            }
        });
        for (Station i : list) {
            System.out.println(i.distance);
        }
    }
}
