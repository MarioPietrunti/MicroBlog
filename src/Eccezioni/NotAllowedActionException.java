package Eccezioni;

public class NotAllowedActionException extends  Exception{

    public NotAllowedActionException() {
        super();
    }

    public NotAllowedActionException(String msg) {
        super(msg);
    }
}
