package CTS;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/*
 * 数据类,用于存放CTS中所涉及的所有数据
 * 问题1:Station的存储貌似没什么意义,所以选择直接将Station的具体信息存储在Line里
 * 列车因为本身的不可重复性也需要存储在Date中
 * */
public class Date {
    private static HashMap<String, User> UserDate = null;
    private static HashMap<String, Line> LineDate = null;
    private static HashMap<String, Train> TrainDate = null;

    public static HashMap<String, User> getUserDate() {
        if (UserDate == null) {
            UserDate = new HashMap<String, User>();
        }
        return UserDate;
    }

    public static HashMap<String, Line> getLineDate() {
        if (LineDate == null) {
            LineDate = new HashMap<String, Line>();
        }
        return LineDate;
    }

    public static HashMap<String, Train> getTrainDate() {
        if (TrainDate == null) {
            TrainDate = new HashMap<String, Train>();
        }
        return TrainDate;
    }

    public static void addUser(User user) {
        UserDate.put(user.getAadhaar(), user);
    }

    public static void addLine(Line line) {
        LineDate.put(line.getLineID(), line);
    }

    public static void delLine(String lineID) {
        LineDate.remove(lineID);
    }

    public static void addStation(String lineID, Station station) {
        LineDate.get(lineID).addStation(station);
    }

    public static void delStation(String lineID, String stationID) {
        LineDate.get(lineID).delStation(stationID);
    }

    public static void addTrain(String lineID, Train train) {
        LineDate.get(lineID).addTrain(train.trainID);
        TrainDate.put(train.trainID, train);
    }

    public static void delTrain(String trainID) {
        String lineID = TrainDate.get(trainID).lineID;
        LineDate.get(lineID).delTrain(trainID);
        TrainDate.remove(trainID);
    }

    public static void outputTrain() {
        HashMap<String, Integer> order = new HashMap<>() {{
            put("Koya", 1);
            put("Gatimaan", 2);
            put("Normal", 3);
        }};
        List<Train> list = new ArrayList<Train>(getTrainDate().values());
        list.sort(new Comparator<Train>() {
            @Override
            public int compare(Train s1, Train s2) {
                if (s1.name.equals(s2.name)) {
                    return s1.trainID.compareTo(s2.trainID);
                }
                else {
                    return order.get(s1.name) - order.get(s2.name);
                }
            }
        });
        int i = 1;
        for (Train train : list) {
            System.out.println("[" + (i++) + "] " + train);
        }
    }
}
