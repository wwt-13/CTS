package CTSTEST.exceptions;

import CTSTEST.types.TrainIllegalType;

public class TrainException extends BaseException {
    public TrainException(TrainIllegalType t) {
        super(t.getValue());
    }
}
