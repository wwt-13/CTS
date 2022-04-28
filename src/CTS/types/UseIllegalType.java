package CTS.types;

public enum UseIllegalType {
    ALREADY_LOGIN("You have logged in"),USER_NOT_EXIST("User does not exist"),
    NAME_NOT_MATCH("Wrong name"),NOT_LOGIN("No user has logged in"),
    NOT_LOGIN2("Please login first"),TICKET_NUM_ILLEGAL("Ticket number illegal"),
    TICKET_NOT_ENOUGH("Ticket does not enough");
    private final String value;
    private UseIllegalType(String value){
        this.value=value;
    }
    public String getValue(){
        return this.value;
    }
}
