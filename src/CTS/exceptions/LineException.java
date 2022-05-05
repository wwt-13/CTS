package CTS.exceptions;

import CTS.types.LineIllegalType;

public class LineException extends BaseException {
    public LineException(LineIllegalType t) {
        super(t.toString());
    }
}
