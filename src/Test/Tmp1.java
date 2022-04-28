package Test;

import java.util.Arrays;
import java.util.HashSet;

public class Tmp1 {
    public static void main(String[] args) {
        String[] tmp={"123","23","123","1231324"};
        HashSet<String> tmp2=new HashSet<>(Arrays.asList(tmp));
        for(String i:tmp2){
            System.out.println(i);
        }
        String tmp1="123 123  123  324";
        System.out.println(Arrays.toString(tmp1.split(" +")));
    }
}
