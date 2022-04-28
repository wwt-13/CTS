package CTS;

import CTS.exceptions.*;
import CTS.types.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

/*
 * 方法运行的基本步骤
 * 1.检测指令是否存在,不存在则抛出CommandNotFound异常
 * 2.检查指令参数个数是否符合,不符合的话直接抛出Argument异常,符合的话调用check()函数继续检测参数格式是否正确(read函数内实现)
 * 3.所有参数格式检测合格,再进行具体的关系+伪数据库检测(具体命令函数实现)
 * 4.无参数异常,正式执行指令(调用具体类函数实现)
 *
 * Command中实现的基本思路
 * 1.read函数获取指令整体参数,并且进行CommandNotFound+Argument异常判断(可能存在特殊情况)
 *  关于CommandNotFound的类型
 *  - 单纯的不存在该指令
 *  - 超管模式和标准模式下的不存在该指令
 * 2.read调用具体函数,再各自的函数内部再对指令参数进行进一步的检查(检查函数全部位于Check类中实现)
 * 3.如果要进行数据存储相关的操作的话,通过调用Date类内的函数实现(并且传入Date内的数据已经确定是完全正确的数据了)
 * */

public class Command {
    private static Command cmd = null;
    private Privilege type;

    /* 获取单例+懒加载 */
    public static Command getInstance() {
        if (cmd == null) {
            cmd = new Command();
            cmd.type = Privilege.NORMAL;
        }
        return cmd;
    }

    /* 读取方法:整个程序的中枢方法,对读取得到的String字符串进行处理并决定调用各种函数 */
    public void read(String[] argArr) throws CommandNotFoundException {
        /* 获取指令类型+指令参数数量检测 */
        CmdType cmdType = CmdType.getValue(argArr[0]);
        if (!Check.checkArgNum(cmdType, argArr.length, type)) {
            throw new ArgumentsIllegalException();
        }
        switch (cmdType) {
            case QUIT -> Quit();
            case ADDUSER -> { /* addUser name sex aadhaar */
                if (!Check.check(CheckType.NAME, argArr[1])) {
                    throw new UserException(UserIllegalType.NAME);
                }
                else if (!Check.check(CheckType.SEX, argArr[2])) {
                    throw new UserException(UserIllegalType.SEX);
                }
                else if (!Check.check(CheckType.AADHAAR, argArr[3])) {
                    throw new UserException(UserIllegalType.AADHAAR_NUMBER_ILLEGAL);
                }
                else {
                    addUser(argArr);
                }
            }
            case UPGRADE -> {
                if (type == Privilege.SUPER) {
                    throw new DuplicateActionConflictException();
                }
                type = Privilege.SUPER;
                System.out.println("DuluDulu");
            }
            case DOWNGRADE -> {
                if (type == Privilege.NORMAL) {
                    throw new DuplicateActionConflictException();
                }
                type = Privilege.NORMAL;
                System.out.println("DaDaDa");
            }
            case ADDLINE -> { /* addLine lineID capacity stationID_i distance_i */
                /* 判断里程数是否合规 */
                for (int i = 4; i < argArr.length; i += 2) {
                    if (!Check.check(CheckType.DISTANCE, argArr[i])) {
                        throw new ArgumentsIllegalException();
                    }
                }
                if (!Check.check(CheckType.LINE_CAPACITY, argArr[2])) {
                    throw new ArgumentsIllegalException();
                }
                else {
                    addLine(argArr);
                }
            }
            case DELLINE -> delLine(argArr[1]);
            case ADDSTATION -> { /* addStation lineID stationID distance */
                if (!Check.check(CheckType.DISTANCE, argArr[3])) {
                    throw new ArgumentsIllegalException();
                }
                else {
                    addStation(argArr);
                }
            }
            case DELSTATION -> delStation(argArr[1], argArr[2]);
            case LINEINFO -> lineInfo(argArr[1]);
            case LISTLINE -> listLine();
            /* 没办法,由于报错顺序的规定问题,这一部分的报错不符合全局规定 */
            case ADDTRAIN -> { /* addTrain trainID lineID seat_i_price seat_i_amount */
                if (((argArr[1].charAt(0) == '0' || argArr[1].charAt(0) == 'G') && argArr.length != 9) || (argArr[1].charAt(0) == 'K' && argArr.length != 7)) {
                    throw new ArgumentsIllegalException();
                }
                else {
                    for (int i = 3; i < argArr.length; i += 2) {
                        if (!Check.check(CheckType.TICKET_PRICE, argArr[i])) {
                            throw new ArgumentsIllegalException();
                        }
                    }
                    for (int i = 4; i < argArr.length; i += 2) {
                        if (!Check.check(CheckType.TICKET_AMOUNT, argArr[i])) {
                            throw new ArgumentsIllegalException();
                        }
                    }
                }
                if (!Check.check(CheckType.TRAIN_ID, argArr[1])) {
                    throw new TrainException(TrainIllegalType.TRAIN_SERIAL_ILLEGAL);
                }
                else {
                    addTrain(argArr);
                }
            }
            case DELTRAIN -> delTrain(argArr[1]);
            case CHECKTICKET -> { /* checkTicket trainID start end seatID */
                if (!Check.check(CheckType.TRAIN_ID, argArr[1])) {
                    throw new TrainException(TrainIllegalType.TRAIN_SERIAL_ILLEGAL);
                }
                else {
                    checkTicket(argArr);
                }
            }
            case LISTTRAIN -> listTrain(argArr);
            case LOGIN -> { /* login aadhaar name */
            }
            case LOGOUT -> {
            }
            case BUYTICKET -> { /* buyTicket trainID start end seatID number */
            }
            case LISTORDER -> {
            }
            case CMDNOTFOUND -> throw new CommandNotFoundException();
        }
    }

    /* 退出命令行系统 */
    public void Quit() {
        System.out.println("----- Good Bye! -----");
        System.exit(0);
    }

    /* 添加用户 */
    public void addUser(String[] args) {
        User user = new User(args[1], args[2], args[3]);
        if (Date.getUserDate().containsKey(user.getAadhaar())) {
            throw new UserException(UserIllegalType.AADHAAR_NUMBER_EXIST);
        }
        else {
            Date.addUser(user);
            System.out.println(user);
        }
    }

    /* 添加线路 */
    public void addLine(String[] argArr) {
        /*
         * 1.判断站点是否重复
         * 2.判断线路编号是否重复
         * 3.最后对线路的负载能力进行非0判断
         * */
        HashSet<String> tmp = new HashSet<>() {{
            for (int i = 3; i < argArr.length; i += 2) {
                add(argArr[i]);
            }
        }};
        if (tmp.size() < (argArr.length - 3) / 2) {
            throw new LineException(LineIllegalType.STATION_DUPLICATE);
        }
        else if (Date.getLineDate().containsKey(argArr[1])) {
            throw new LineException(LineIllegalType.LINE_EXIST);
        }
        else if (Integer.parseInt(argArr[2]) <= 0) {
            throw new LineException(LineIllegalType.CAPACITY_ILLEGAL);
        }
        else { /* 此时已经可以确认数据完全正确,直接添加即可 */
            Line line = new Line(argArr[1], Integer.parseInt(argArr[2]));
            for (int i = 3; i < argArr.length; i += 2) {
                line.getStations().put(argArr[i],
                                       new Station(argArr[i], Integer.parseInt(argArr[i + 1])));
            }
            Date.addLine(line);
            System.out.println("Add Line success");
        }
    }

    /* 删除路线 */
    public void delLine(String lineID) {
        if (!Date.getLineDate().containsKey(lineID)) {
            throw new LineException(LineIllegalType.LINE_NOT_EXIST);
        }
        else {
            Date.delLine(lineID);
            System.out.println("Del Line success");
        }
    }

    /* 添加站点 */
    public void addStation(String[] argArr) {
        if (!Date.getLineDate().containsKey(argArr[1])) {
            throw new LineException(LineIllegalType.LINE_NOT_EXIST);
        }
        else if (Date.getLineDate().get(argArr[1]).getStations().containsKey(argArr[2])) {
            throw new LineException(LineIllegalType.STATION_DUPLICATE);
        }
        else {
            Station station = new Station(argArr[2], Integer.parseInt(argArr[3]));
            Date.addStation(argArr[1], station);
            System.out.println("Add Station success");
        }
    }

    /* 删除站点 */
    public void delStation(String lineID, String stationID) {
        if (!Date.getLineDate().containsKey(lineID)) {
            throw new LineException(LineIllegalType.LINE_NOT_EXIST);
        }
        else if (!Date.getLineDate().get(lineID).getStations().containsKey(stationID)) {
            throw new LineException(LineIllegalType.STATION_NOT_EXIST);
        }
        else {
            Date.delStation(lineID, stationID);
            System.out.println("Delete Station success");
        }
    }

    /* 输出某条线路的信息 */
    public void lineInfo(String lineID) {
        if (!Date.getLineDate().containsKey(lineID)) {
            throw new LineException(LineIllegalType.LINE_NOT_EXIST);
        }
        else {
            System.out.println(Date.getLineDate().get(lineID));
        }
    }

    /* 列出所有线路的信息 */
    public void listLine() {
        if (Date.getLineDate().isEmpty()) {
            System.out.println("No Lines");
        }
        else {
            int i = 1;
            List<Line> list = new ArrayList<>(Date.getLineDate().values());
            list.sort(new Comparator<Line>() {
                @Override
                public int compare(Line l1, Line l2) {
                    return l1.getLineID().compareTo(l2.getLineID());
                }
            });
            for (Line line : list) {
                System.out.println("[" + (i++) + "] " + line);
            }
        }
    }

    /*
     * 添加列车
     * 1.将列车添加到列车数据库中
     * 2.将列车添加到具体的线路中
     * */
    public void addTrain(String[] argArr) {
        if (Date.getTrainDate().containsKey(argArr[1])) {
            throw new TrainException(TrainIllegalType.TRAIN_SERIAL_DUPLICATE);
        }
        else if (!Date.getLineDate().containsKey(argArr[2]) || Date.getLineDate().get(argArr[2]).isFull()) {
            throw new LineException(LineIllegalType.LINE_CAPACITY_ILLEGAL);
        }
        else {
            for (int i = 3; i < argArr.length; i += 2) {
                if (Float.parseFloat(argArr[i]) < 0) {
                    throw new TrainException(TrainIllegalType.PRICE_ILLEGAL);
                }
            }
            for (int i = 4; i < argArr.length; i += 2) {
                if (Integer.parseInt(argArr[i]) < 0) {
                    throw new TrainException(TrainIllegalType.TICKET_NUM_ILLEGAL);
                }
            }
            Train train = switch (argArr[1].charAt(0)) {
                case '0' -> new TrainNormal(argArr);
                case 'G' -> new TrainGatimaan(argArr);
                case 'K' -> new TrainKoya(argArr);
                default -> throw new ArgumentsIllegalException();
            };
            Date.addTrain(argArr[2], train);
            System.out.println("Add Train Success");
        }
    }

    /*
     * 删除列车
     * */
    public void delTrain(String trainID) {
        if (!Date.getTrainDate().containsKey(trainID)) {
            throw new TrainException(TrainIllegalType.TRAIN_NOT_EXIST);
        }
        else {
            Date.delTrain(trainID);
            System.out.println("Del Train Success");
        }
    }

    /*
     * 查询列车余票和票价信息
     * 并且只能在标准模式下访问,但是内部类又可以访问外部类的所有属性和方法
     * 所以必须得增加内部判断条件
     * */
    public void checkTicket(String[] argArr) {
        Train train = Date.getTrainDate().get(argArr[1]);
        Station start = null;
        Station end = null;
        if (train == null) {
            throw new TrainException(TrainIllegalType.TRAIN_SERIAL_NOT_EXIST);
        }
        else {
            start = Date.getLineDate().get(train.lineID).getStations().get(argArr[2]);
            end = Date.getLineDate().get(train.lineID).getStations().get(argArr[3]);
        }
        if (start == null || end == null) {
            throw new LineException(LineIllegalType.STATION_NOT_EXIST);
        }
        else if (
                !switch (argArr[1].charAt(0)) {
                    case '0' -> TrainNormal.contains(argArr[4]);
                    case 'G' -> TrainGatimaan.contains(argArr[4]);
                    case 'K' -> TrainKoya.contains(argArr[4]);
                    default -> false;
                }
        ) {
            throw new TrainException(TrainIllegalType.SEAT_NOT_MATCH);
        }
        else {
            train.output(start, end, argArr[4]);
        }
    }

    /* 列出某条线路上的所有列车信息 */
    public void listTrain(String[] argArr) {
        if (argArr.length == 2) {
            if (!Date.getLineDate().containsKey(argArr[1])) {
                throw new LineException(LineIllegalType.LINE_NOT_EXIST);
            }
            else if (Date.getLineDate().get(argArr[1]).isEmpty()) {
                System.out.println("No Trains");
            }
            else {
                Date.getLineDate().get(argArr[1]).outputTrain();
            }
        }
        else {
            Date.outputTrain();
        }
    }

    /* 用户登录 */
    public void login() {

    }

    /* 用户登出 */
    public void logout() {

    }

    /* 购买车票 */
    public void buyTicket() {

    }

    /* 查询自己已经购买了的车票 */
    public void listOrder() {

    }

    enum Privilege {
        NORMAL, SUPER
    }
}