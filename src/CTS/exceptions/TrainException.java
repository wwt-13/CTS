package CTS.exceptions;

import CTS.types.TrainIllegalType;

public class TrainException extends BaseException {
    public TrainException(TrainIllegalType t) {
        super(t.toString());
    }
}
