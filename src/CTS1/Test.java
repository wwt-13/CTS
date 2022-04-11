package CTS1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import CTS1.exceptions.*;

public class Test {
    public static void main(String[] args) {
        var scan=new Scanner(System.in);
        String argStr;
        while(true){
            argStr=scan.nextLine();//读取一行
            if(argStr.equals("QUIT")){
                scan.close();//关闭stdin
                System.out.println("----- Good Bye! -----");
                System.exit(0);
            }
            var tmp=argStr.split(" ");
            var arg=new ArrayList<String>();
            Collections.addAll(arg, tmp);
            String option= arg.get(0);//列表访问
            arg.remove(0);
            var user=new User();
            try{
                user.addUser(arg);//检测到异常则直接转入catch
                System.out.println(user);
            }
            catch(ArgumentsIllegalException | NameIllegalException | SexIllegalException | AadhaarIllegalException | AadhaarNumberExistIllegalException e){
                System.out.println(e.getMessage());
            }
        }
    }
}
