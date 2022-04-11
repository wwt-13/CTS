package CTS1;
import CTS1.User;

import java.util.HashMap;
import java.util.Map;

public class UserDate {//存储用户数据，并以Aadhaar卡号作为键值
    private static UserDate obj=new UserDate();
    private static Map<String,User> Date;//单例模式，因为数据库应该只有一份
    //此时已经可以确定数据的正确性
    void add(User user){
        Date.put(user.getAadhaar(),user);
    }
    boolean exist(String aadhaar){
        return Date.containsKey(aadhaar);
    }
    private UserDate(){
        Date=new HashMap<>();//初始化Map
    }
    public static UserDate getInstance(){
        return obj;
    }
}
