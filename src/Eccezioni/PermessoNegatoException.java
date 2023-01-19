package Eccezioni;

public class PermessoNegatoException extends Exception {

    public PermessoNegatoException() {
        super();
    }

    public PermessoNegatoException(String msg) {
        super(msg);
    }
}
