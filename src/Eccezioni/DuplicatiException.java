package Eccezioni;

public class DuplicatiException extends Exception{

    public DuplicatiException() {
        super();
    }

    public DuplicatiException(String msg) {
        super(msg);
    }
}
