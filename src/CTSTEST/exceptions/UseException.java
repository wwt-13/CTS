package CTSTEST.exceptions;

import CTSTEST.types.UseIllegalType;

public class UseException extends BaseException {
    public UseException(UseIllegalType t) {
        super(t.getValue());
    }
}
