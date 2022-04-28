package CTS.types;

public enum CmdType {
    QUIT,
    ADDUSER,
    UPGRADE,DOWNGRADE,
    ADDLINE,DELLINE,
    ADDSTATION,DELSTATION,
    LINEINFO,LISTIINE,
    ADDTRAIN,DELTRAIN,
    CHECKTICKET,LISTTRAIN,
    LOGIN,LOGOUT,BUYTICKET,LISTORDER,
    CMDNOTFOUND;

    /*
    * 不能理解为什么不能重载valueOf方法
    * */
    public static CmdType getValue(String s){
        return switch(s){
            case "QUIT"-> QUIT;
            case "addUser"->ADDUSER;
            case "TunakTunakTun"->UPGRADE;
            case "NutKanutKanut"->DOWNGRADE;
            case "addLine"->ADDLINE;
            case "delLine"->DELLINE;
            case "addStation"->ADDSTATION;
            case "delStation"->DELSTATION;
            case "lineInfo"->LINEINFO;
            case "listLine"->LISTIINE;
            case "addTrain"->ADDTRAIN;
            case "delTrain"->DELTRAIN;
            case "checkTicket"->CHECKTICKET;
            case "listTrain"->LISTTRAIN;
            case "login"->LOGIN;
            case "logout"->LOGOUT;
            case "buyTicket"->BUYTICKET;
            case "listOrder"->LISTORDER;
            default -> CMDNOTFOUND;
        };
    }
}
