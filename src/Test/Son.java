package Test;

public class Son extends Father {
    public Son() {
        this.test = "1233214";
    }

    public static void main(String[] args) {
        Father f = new Son();
        f.tmp();
        System.out.println(f + "123");
    }
}
