package CTS.exceptions;

import CTS.types.UseIllegalType;

public class UseException extends BaseException{
    public UseException(UseIllegalType t){
        super(t.getValue());
    }
}
