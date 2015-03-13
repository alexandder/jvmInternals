package exercisetwo;


public class InvalidJsonFormatException extends Exception {

    public InvalidJsonFormatException(String message) {
        super(message);
    }

    public String getMessage() {
        return super.getMessage();
    }
}

