package CTS;

import CTS.exceptions.*;

import java.util.Scanner;

/*
 * 所有异常都放置到Test类中进行处理
 * 异常输出的优先级直接根据catch中的异常顺序排列即可
 * */
public class Test {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String argStr;
        String[] argArr = null;
        Command cmd = Command.getInstance();
        while (true) {
            argStr = scan.nextLine();
            argArr = argStr.split(" +");
            try {
                cmd.read(argArr);
            }
            catch (CommandNotFoundException |
                    ArgumentsIllegalException |
                    UserException | LineException | TrainException | UseException |
                    DuplicateActionConflictException |
                    UnknownErrorException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
