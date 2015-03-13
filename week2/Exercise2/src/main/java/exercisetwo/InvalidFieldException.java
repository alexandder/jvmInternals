package exercisetwo;


public class InvalidFieldException extends Exception {

    public InvalidFieldException(String message) {
        super(message);
    }

    public String getMessage() {
        return super.getMessage();
    }
}
