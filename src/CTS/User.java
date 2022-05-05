package CTS;

import java.util.ArrayList;

public class User {
    private String name;
    private String sex;
    private String aadhaar;
    private ArrayList<Order> orders;

    public User(String name, String sex, String aadhaar) {
        this.name = name;
        this.sex = sex;
        this.aadhaar = aadhaar;
        this.orders = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAadhaar() {
        return aadhaar;
    }

    public void setAadhaar(String aadhaar) {
        this.aadhaar = aadhaar;
    }

    @Override
    public String toString() {
        return String.format("Name:%s\nSex:%s\nAadhaar:%s", this.name, this.sex, this.aadhaar);
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }
}
