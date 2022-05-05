package CTS.exceptions;

import CTS.types.UserIllegalType;

public class UserException extends BaseException {
    public UserException(UserIllegalType t) {
        super(t.toString());
    }
}
