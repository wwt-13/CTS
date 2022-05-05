package CTS;

import CTS.exceptions.ArgumentsIllegalException;
import CTS.exceptions.CommandNotFoundException;
import CTS.types.CheckType;
import CTS.types.CmdType;
import CTS.types.TrainType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * 参数格式校验类
 * 1.校验参数类型
 * 2.校验具体参数要求(但是不涉及需要查询Date的校验)
 * 3.对于车票类型的校验需要函数自己处理
 * */
public class Check {

    private static final String regInteger = "^-?([1-9][0-9]*|0)$";
    private static final String regPositiveInteger = "^[1-9][0-9]*$";
    private static final String regNumber = "^-?([1-9]\\d*\\.?\\d*)|(0\\.\\d*[1-9])$";

    private static final String regName = "^[a-zA-Z_]+$";
    private static final String regSex = "[MFO]";
    private static final String regAadhaar = "(0(?!0{3})[0-9]{3}|1[0-1][0-9]{2}|12[0-2][0-9]|123" +
            "[0" +
            "-7])(00[2-9][0-9]|0[1-3][0-9]{2}|04[0-5][0-9]|0460)((0[0-9]{2}|100)[0-2])";

    private static final String regLineCapacity = regInteger;
    private static final String regDistance = regPositiveInteger;
    private static final String regTrainID = "^[GK0][0-9]{4}$";
    private static final String regTicketPrice = regNumber;
    private static final String regTicketAmount = regInteger;

    public static boolean check(CheckType t, String arg) {
        Pattern pattern = switch (t) {
            case NAME -> Pattern.compile(regName);
            case SEX -> Pattern.compile(regSex);
            case AADHAAR -> Pattern.compile(regAadhaar);
            case LINE_CAPACITY -> Pattern.compile(regLineCapacity);
            case DISTANCE -> Pattern.compile(regDistance);
            case TRAIN_ID -> Pattern.compile(regTrainID);
            case TICKET_PRICE -> Pattern.compile(regTicketPrice);
            case TICKET_AMOUNT -> Pattern.compile(regTicketAmount);
        };
        Matcher matcher = pattern.matcher(arg);
        return matcher.find();
    }

    /* 参数个数检查+权限检查 */
    public static boolean checkArgNumAndPri(CmdType t, String[] argArr, Command.Privilege type) {
        int length = argArr.length;
        return switch (t) {
            case QUIT, LISTLINE, LOGOUT, LISTORDER, UPGRADE, DOWNGRADE -> length == 1;
            case LISTTRAIN -> length == 1 || length == 2;
            case LINEINFO -> length == 2;
            case LOGIN -> length == 3;
            case ADDUSER -> length == 4;
            case BUYTICKET -> length == 6;
            case ADDLINE -> {
                if (type == Command.Privilege.NORMAL) {
                    throw new CommandNotFoundException();
                }
                yield length >= 5 && (length - 3) % 2 == 0;
            }
            case DELLINE, DELTRAIN -> {
                if (type == Command.Privilege.NORMAL) {
                    throw new CommandNotFoundException();
                }
                yield length == 2;
            }
            case ADDSTATION -> {
                if (type == Command.Privilege.NORMAL) {
                    throw new CommandNotFoundException();
                }
                yield length == 4;
            }
            case DELSTATION -> {
                if (type == Command.Privilege.NORMAL) {
                    throw new CommandNotFoundException();
                }
                yield length == 3;
            }
            case ADDTRAIN -> {
                if (type == Command.Privilege.NORMAL) {
                    throw new CommandNotFoundException();
                }
                if (length < 2) {
                    throw new ArgumentsIllegalException();
                }
                TrainType trainType = TrainType.getInstance(argArr[1]);
                if (trainType == TrainType.KOYA) {
                    yield length == 7;
                }
                else if (trainType == TrainType.NORMAL || trainType == TrainType.GATIMAAN) {
                    yield length == 9;
                }
                else {
                    yield true;
                }
            }
            case CHECKTICKET -> {
                if (type == Command.Privilege.SUPER) {
                    throw new CommandNotFoundException();
                }
                yield length == 5;
            }
            case CMDNOTFOUND -> throw new CommandNotFoundException();
        };
    }
}
