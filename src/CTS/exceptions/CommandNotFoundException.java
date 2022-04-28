package CTS.exceptions;

public class CommandNotFoundException extends BaseException{
    public CommandNotFoundException(){
        super("Command does not exist");
    }
}
