package CTS.types;

public enum CmdType {
    QUIT,
    ADDUSER,
    UPGRADE, DOWNGRADE,
    ADDLINE, DELLINE,
    ADDSTATION, DELSTATION,
    LINEINFO, LISTLINE,
    ADDTRAIN, DELTRAIN,
    CHECKTICKET, LISTTRAIN,
    LOGIN, LOGOUT, BUYTICKET, LISTORDER,
    CMDNOTFOUND;

    public static CmdType getInstance(String s) {
        return switch (s) {
            case "QUIT" -> QUIT;
            case "addUser" -> ADDUSER;
            case "TunakTunakTun" -> UPGRADE;
            case "NutKanutKanut" -> DOWNGRADE;
            case "addLine" -> ADDLINE;
            case "delLine" -> DELLINE;
            case "addStation" -> ADDSTATION;
            case "delStation" -> DELSTATION;
            case "lineInfo" -> LINEINFO;
            case "listLine" -> LISTLINE;
            case "addTrain" -> ADDTRAIN;
            case "delTrain" -> DELTRAIN;
            case "checkTicket" -> CHECKTICKET;
            case "listTrain" -> LISTTRAIN;
            case "login" -> LOGIN;
            case "logout" -> LOGOUT;
            case "buyTicket" -> BUYTICKET;
            case "listOrder" -> LISTORDER;
            default -> CMDNOTFOUND;
        };
    }
}
