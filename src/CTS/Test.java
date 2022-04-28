package CTS;

import CTS.exceptions.*;

import java.util.Arrays;
import java.util.Scanner;

/*
 * 所有异常都放置到Test类中进行处理
 * 异常输出的优先级直接根据catch中的异常顺序排列即可
 * */
public class Test {

    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        String argStr;
        String[] argArr=null;
        Command cmd=Command.getInstance();
        while(true){
            argStr=scan.nextLine();
            argArr=argStr.split(" +");
            try{
                cmd.read(argArr);
                /*
                * 这里暂时确实没办法,必须重复判断一次
                * 但是其余琐碎的判断以及在read函数中实现了
                * */
                if(argArr[0].equals("TunakTunakTun")){
                    cmd=cmd.upgrade();
                }
                else if(argArr[0].equals("NutKanutKanut")){
                    cmd=cmd.downgrade();
                }
            }
            catch(CommandNotFoundException|
                    ArgumentsIllegalException|
                    UserException|LineException|TrainException|UseException|DuplicateActionConflictException|
                    UnknownErrorException e){
                System.out.println(e.getMessage());
                System.out.println(Arrays.toString(e.getStackTrace()));
            }
        }
    }
}
