package CTSTEST;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/*
 * Line需要记录的信息
 * 1.Line本身的信息(LineID,Capacity)
 * 2.各个站点的主键(StationIDs)
 * 目前不确定的一个问题就是各个站点加入的顺序？
 * (破案了,应该是随缘添加)
 * */
public class Line {
    private String lineID;
    private int capacity;
    private int currentCap;
    private HashMap<String, Station> stations;
    private ArrayList<String> trains;

    public Line(String lineID, int capacity) {
        this.lineID = lineID;
        this.capacity = capacity;
        this.currentCap = 0;
        stations = new HashMap<String, Station>();
        trains = new ArrayList<String>();
    }

    public String getLineID() {
        return lineID;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getCurrentCap() {
        return currentCap;
    }

    public ArrayList<String> getTrains() {
        return trains;
    }

    public HashMap<String, Station> getStations() {
        return stations;
    }

    public boolean isFull() {
        return currentCap == capacity;
    }

    public boolean isEmpty() {
        return currentCap == 0;
    }

    public void addStation(Station station) {
        stations.put(station.getStationID(), station);
    }

    public void delStation(String stationID) {
        stations.remove(stationID);
    }

    public void addTrain(String trainID) {
        trains.add(trainID);
        currentCap++;
    }

    public void delTrain(String trainID) {
        trains.remove(trainID);
        currentCap--;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.lineID).append(" ").append(this.currentCap).append("/").append(this.capacity);
        /*
         * 这里还得看看如何自定义排序方法,之后看完了再来继续写
         * 鉴于这里只是输出数据,所以选择使用ArrayList辅助排序的方法来完成HashMap内元素的排序输出
         * */
        List<Station> list = new ArrayList<Station>(this.stations.values());
        list.sort(new Comparator<Station>() {
            @Override
            public int compare(Station s1, Station s2) {
                return s1.getDistance() - s2.getDistance();
            }
        });
        for (Station station : list) {
            sb.append(" ").append(station.getStationID()).append(":").append(station.getDistance());
        }
        return sb.toString();
    }

    public void outputTrain() {
        HashMap<String, Integer> order = new HashMap<>() {{
            put("Koya", 1);
            put("Gatimaan", 2);
            put("Normal", 3);
        }};
        List<Train> list = new ArrayList<Train>() {{
            for (String trainID : trains) {
                add(Date.getTrainDate().get(trainID));
            }
        }};
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
