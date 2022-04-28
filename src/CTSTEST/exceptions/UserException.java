package CTSTEST.exceptions;

import CTSTEST.types.UserIllegalType;

public class UserException extends BaseException {
    public UserException(UserIllegalType t) {
        super(t.getValue());
    }
}
