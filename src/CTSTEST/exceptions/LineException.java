package CTSTEST.exceptions;

import CTSTEST.types.LineIllegalType;

public class LineException extends BaseException {
    public LineException(LineIllegalType t) {
        super(t.getValue());
    }
}
