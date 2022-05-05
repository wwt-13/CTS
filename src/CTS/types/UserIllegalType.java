package CTS.types;

/*
 * 这里说明的是数据初步检错后的异常类(不包括参数异常、命令不存在异常等)
 * */
public enum UserIllegalType {
    NAME("Name illegal"), SEX("Sex illegal"), AADHAAR_NUMBER_ILLEGAL("Aadhaar number illegal"),
    AADHAAR_NUMBER_EXIST("Aadhaar number exist");
    private final String value;

    private UserIllegalType(String value) {
        this.value = value;
    }

    public String toString() {
        return this.value;
    }
}