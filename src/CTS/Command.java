package CTS;

import CTS.exceptions.*;
import CTS.types.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;
/*
 * 关于阿里巴巴类内方法书写规范
 * 1.当一个类有多个构造方法，或者多个同名方法，这些方法应该按顺序放置在一起，便于阅读
 * 2.类内方法定义顺序依次是：公有方法或保护方法 > 私有方法 > getter/setter方法
 * */

/*
 * 方法运行的基本步骤
 * 1.检测指令是否存在,不存在则抛出CommandNotFound异常
 * 2.检查指令参数个数是否符合,不符合的话直接抛出Argument异常,符合的话调用check()函数继续检测参数格式是否正确(read函数内实现)
 * 3.所有参数格式检测合格,再进行具体的关系+伪数据库检测(具体命令函数实现)
 * 4.无参数异常,正式执行指令(调用具体类函数实现)
 * */

public class Command {
    private static Command cmd = null;
    private static SuperCommand superCmd = null;
    private String type;

    /*
     * 获取单例方法
     * */
    public static Command getInstance() {
        if (cmd == null) {
            cmd = new Command();
            cmd.type = "Command";
            /*
             * 这句话很关键,必须使得cmd和supercmd连接起来
             * */
            superCmd = cmd.new SuperCommand();
        }
        return cmd;
    }

    /*
     * 读取方法
     * 对读取得到的String字符串进行处理并决定调用各种函数
     * 可以说是整个程序的中枢方法
     * */
    public void read(String[] argArr) {
        /*
         * 首先对字符串进行处理
         * 1.字符串分割
         * 2.根据得到的首位字符串获取准确的命令类型
         * */
        CmdType cmdType = CmdType.getValue(argArr[0]);
        switch (cmdType) {
            case QUIT -> {
                if (argArr.length == 1) {
                    Quit();
                }
                else {
                    throw new ArgumentsIllegalException();
                }
            }
            case ADDUSER -> {
                if (argArr.length != 4) {
                    throw new ArgumentsIllegalException();
                }
                else if (!Check.check(CheckType.NAME, argArr[1])) {
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
                if (argArr.length != 1) {
                    throw new ArgumentsIllegalException();
                }
                else if (cmd.type.equals("SuperCommand")) {
                    throw new DuplicateActionConflictException();
                }
            }
            case DOWNGRADE -> {
                if (argArr.length != 1) {
                    throw new ArgumentsIllegalException();
                }
                else if (cmd.type.equals("Command")) {
                    throw new DuplicateActionConflictException();
                }
            }
            case ADDLINE -> {
                if (cmd.type.equals("Command")) {
                    throw new CommandNotFoundException();
                }
                else if ((argArr.length < 5) || (argArr.length - 3) % 2 != 0) {
                    throw new ArgumentsIllegalException();
                }
                /*
                 * 判断里程数是否合规
                 * */
                for (int i = 4; i < argArr.length; i += 2) {
                    if (!Check.check(CheckType.DISTANCE, argArr[i])) {
                        throw new ArgumentsIllegalException();
                    }
                }
                if (!Check.check(CheckType.LINE_CAPACITY, argArr[2])) {
                    throw new ArgumentsIllegalException();
                }
                else {
                    superCmd.addLine(argArr);
                }
            }
            case DELLINE -> {
                if (cmd.type.equals("Command")) {
                    throw new CommandNotFoundException();
                }
                else if (argArr.length != 2) {
                    throw new ArgumentsIllegalException();
                }
                else {
                    superCmd.delLine(argArr[1]);
                }
            }
            case ADDSTATION -> {
                if (cmd.type.equals("Command")) {
                    throw new CommandNotFoundException();
                }
                else if (argArr.length != 4) {
                    throw new ArgumentsIllegalException();
                }
                else if (!Check.check(CheckType.DISTANCE, argArr[3])) {
                    throw new ArgumentsIllegalException();
                }
                else {
                    superCmd.addStation(argArr);
                }
            }
            case DELSTATION -> {
                if (cmd.type.equals("Command")) {
                    throw new CommandNotFoundException();
                }
                else if (argArr.length != 3) {
                    throw new ArgumentsIllegalException();
                }
                else {
                    superCmd.delStation(argArr);
                }
            }
            case LINEINFO -> {
                if (argArr.length != 2) {
                    throw new ArgumentsIllegalException();
                }
                else {
                    lineInfo(argArr[1]);
                }
            }
            case LISTIINE -> {
                if (argArr.length != 1) {
                    throw new ArgumentsIllegalException();
                }
                else {
                    listLine();
                }
            }
            /*
             * 没办法,由于报错顺序的规定问题,这一部分的报错不符合全局规定
             * */
            case ADDTRAIN -> {
                if (cmd.type.equals("Command")) {
                    throw new CommandNotFoundException();
                }
                else if ((argArr.length > 9) || ((argArr[1].charAt(0) == '0' || argArr[1].charAt(0) == 'G') && argArr.length != 8) || (argArr[1].charAt(0) == 'K' && argArr.length != 6)) {
                    throw new ArgumentsIllegalException();
                }
                else if (!Check.check(CheckType.TRAIN_ID, argArr[2])) {
                    throw new TrainException(TrainIllegalType.TRAIN_SERIAL_ILLEGAL);
                }
                else {
                    superCmd.addTrain(argArr);
                }
            }
            case DELTRAIN -> {
                if (cmd.type.equals("Command")) {
                    throw new CommandNotFoundException();
                }
                else if (argArr.length != 2) {
                    throw new ArgumentsIllegalException();
                }
                else {
                    superCmd.delTrain(argArr);
                }
            }
            case CHECKTICKET -> {
                if (cmd.type.equals("SuperCommand")) {
                    throw new CommandNotFoundException();
                }
                else if (argArr.length != 5) {
                    throw new ArgumentsIllegalException();
                }
                else if (!Check.check(CheckType.TRAIN_ID, argArr[1])) {
                    throw new TrainException(TrainIllegalType.TRAIN_SERIAL_ILLEGAL);
                }
                else {
                    checkTicket(argArr);
                }
            }
            case LISTTRAIN -> {
                if (argArr.length != 2) {
                    throw new ArgumentsIllegalException();
                }
                else {
                    listTrain(argArr[1]);
                }
            }
            case LOGIN -> {
            }
            case LOGOUT -> {
            }
            case BUYTICKET -> {
            }
            case LISTORDER -> {
            }
            case CMDNOTFOUND -> {
                throw new CommandNotFoundException();
            }
        }
    }

    /*
     * 提升权限操作
     * 如果未初始化超级管理员，初始化超级管理员
     * */
    public SuperCommand upgrade() {
        cmd.type = "SuperCommand";
        System.out.println("DuluDulu");
        return superCmd;
    }

    /*
     * 降低权限操作
     * */
    public Command downgrade() {
        cmd.type = "Command";
        System.out.println("DaDaDa");
        return cmd;
    }

    /*
     * 退出命令行系统
     * */
    public void Quit() {
        System.out.println("----- Good Bye! -----");
        System.exit(0);
    }

    /*
     * 添加用户
     * */
    public void addUser(String[] args) {
        User user = new User(args[1], args[2], args[3]);
        HashMap<String, User> UserDate = Date.getUserDate();
        if (UserDate.containsKey(user.getAadhaar())) {
            throw new UserException(UserIllegalType.AADHAAR_NUMBER_EXIST);
        }
        else {
            Date.addUser(user);
            System.out.println(user);
        }
    }

    /*
     * 输出某条线路的信息
     * */
    public void lineInfo(String lineID) {
        if (!Date.getLineDate().containsKey(lineID)) {
            throw new LineException(LineIllegalType.LINE_NOT_EXIST);
        }
        else {
            System.out.println(Date.getLineDate().get(lineID));
        }
    }

    /*
     * 列出所有线路的信息
     * */
    public void listLine() {
        if (Date.getLineDate().isEmpty()) {
            System.out.println("No Lines");
        }
        else {
            /*
             * 这里封装的还不是很好,之后有时间可以再来修改下
             * 不考虑重写HashMap的toString方法,那样的话需要修改Date类的架构
             * 此处使用排序的方法实现,因为是按键排序,所以直接利用TreeSet即可完成
             * 之后还得好好看看迭代器的使用方法
             * */
            TreeMap<String, Line> treeMap = new TreeMap<>(Date.getLineDate());
            for (Line line : treeMap.values()) {
                System.out.println(line);
            }
        }
    }

    /*
     * 查询列车余票和票价信息
     * 并且只能在标准模式下访问,但是内部类又可以访问外部类的所有属性和方法
     * 所以必须得增加内部判断条件
     * */
    public void checkTicket(String[] argArr) {
        Train train = Date.getTrainDate().get(argArr[1]);
        Station start = Date.getLineDate().get(train.lineID).getStations().get(argArr[2]);
        Station end = Date.getLineDate().get(train.lineID).getStations().get(argArr[3]);
        if (start == null || end == null) {
            throw new LineException(LineIllegalType.STATION_NOT_EXIST);
        }
        else if (
                !switch (argArr[1].charAt(0)) {
                    case '0' -> TrainNormal.contains(argArr[4]);
                    case 'G' -> TrainGatimaan.contains(argArr[4]);
                    case 'K' -> TrainKoya.contains(argArr[4]);
                    default -> throw new ArgumentsIllegalException();
                }
        ) {
            throw new TrainException(TrainIllegalType.SEAT_NOT_MATCH);
        }
        else {
            train.output(start, end, argArr[4]);
        }
    }

    /*
     * 列出某条线路上的所有列车信息
     * */
    public void listTrain(String lineID) {
        if (!Date.getLineDate().containsKey(lineID)) {
            throw new LineException(LineIllegalType.LINE_NOT_EXIST);
        }
        else {
            Date.getLineDate().get(lineID).outputTrain();
        }
    }

    /*
     * 用户登录
     * */
    public void login() {

    }

    /*
     * 用户登出
     * */
    public void logout() {

    }

    /*
     * 购买车票
     * */
    public void buyTicket() {

    }

    /*
     * 查询自己已经购买了的车票
     * */
    public void listOrder() {

    }

    /*
     * 超级管理员内部类(实现权限管理)
     * */
    private class SuperCommand extends Command {
        /*
         * 添加线路
         * */
        public void addLine(String[] argArr) {
            /*
             * 1.判断站点是否重复
             * 2.判断线路编号是否重复
             * 3.最后对线路的负载能力进行非0判断
             * */
            HashSet<String> tmp = new HashSet<>();
            for (int i = 3; i < argArr.length; i += 2) {
                tmp.add(argArr[i]);
            }
            if (tmp.size() < (argArr.length - 3) / 2) {
                throw new LineException(LineIllegalType.STATION_DUPLICATE);
            }
            else if (Date.getLineDate().containsKey(argArr[1])) {
                throw new LineException(LineIllegalType.LINE_EXIST);
            }
            else if (Integer.parseInt(argArr[2]) <= 0) {
                throw new LineException(LineIllegalType.LINE_CAPACITY_ILLEGAL);
            }
            else {
                /*
                 * 此时已经可以确认数据完全正确,直接添加即可
                 * 1.创建线路
                 * 2.添加各个站点
                 * */
                Line line = new Line(argArr[1], Integer.parseInt(argArr[2]));
                for (int i = 3; i < argArr.length; i += 2) {
                    line.getStations().put(argArr[i], new Station(argArr[i],
                            Integer.parseInt(argArr[i + 1])));
                }
                Date.addLine(line);
                System.out.println("Add Line success");
            }
        }

        /*
         * 删除路线
         * */
        public void delLine(String lineID) {
            if (!Date.getLineDate().containsKey(lineID)) {
                throw new LineException(LineIllegalType.LINE_NOT_EXIST);
            }
            else {
                Date.delLine(lineID);
                System.out.println("Del Line success");
            }
        }

        /*
         * 添加站点
         * */
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

        /*
         * 删除站点
         * */
        public void delStation(String[] argArr) {
            if (!Date.getLineDate().containsKey(argArr[1])) {
                throw new LineException(LineIllegalType.LINE_NOT_EXIST);
            }
            else if (!Date.getLineDate().get(argArr[1]).getStations().containsKey(argArr[2])) {
                throw new LineException(LineIllegalType.STATION_NOT_EXIST);
            }
            else {
                Date.delStation(argArr[1], argArr[2]);
                System.out.println("Delete Station success");
            }
        }

        /*
         * 添加列车
         * 1.将列车添加到列车数据库中
         * 2.将列车添加到具体的线路中
         * */
        public void addTrain(String[] argArr) {
            if (!Date.getTrainDate().containsKey(argArr[1])) {
                throw new TrainException(TrainIllegalType.TRAIN_SERIAL_ILLEGAL);
            }
            else if (!Date.getLineDate().containsKey(argArr[2]) && !Date.getLineDate().get(argArr[1]).isFull()) {
                throw new LineException(LineIllegalType.LINE_CAPACITY_ILLEGAL);
            }
            else {
                for (int i = 3; i < argArr.length; i += 2) {
                    if (!(Check.check(CheckType.TICKET_PRICE, argArr[i]) && Float.parseFloat(argArr[i]) > 0)) {
                        throw new TrainException(TrainIllegalType.PRICE_ILLEGAL);
                    }
                }
                for (int i = 4; i < argArr.length; i += 2) {
                    if (!(Check.check(CheckType.TICKET_AMOUNT, argArr[i]) && Integer.parseInt(argArr[i]) > 0)) {
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
            }
        }

        /*
         * 删除列车
         * */
        public void delTrain(String[] argArr) {
            if (!Date.getTrainDate().containsKey(argArr[1])) {
                throw new TrainException(TrainIllegalType.TRAIN_NOT_EXIST);
            }
            else {
                Date.delTrain(argArr[1]);
            }
        }
    }
}

