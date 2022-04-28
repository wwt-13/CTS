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
            //读取一行
            argStr=scan.nextLine();
            if("QUIT".equals(argStr)){
                scan.close();//关闭stdin
                System.out.println("----- Good Bye! -----");
                System.exit(0);
            }
            var tmp=argStr.split(" ");
            var arg=new ArrayList<String>();
            Collections.addAll(arg, tmp);
            //列表访问
            String option= arg.get(0);
            arg.remove(0);
            var user=new User();
            try{
                //检测到异常则直接转入catch
                user.addUser(arg);
                System.out.println(user);
            }
            catch(ArgumentsIllegalException | NameIllegalException | SexIllegalException | AadhaarIllegalException | AadhaarNumberExistIllegalException e){
                System.out.println(e.getMessage());
            }
        }
    }
}
