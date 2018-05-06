package input;

public class InputMangerException extends RuntimeException
{

    public InputMangerException(String message) {
        super("Bad file : " + message);
    }
}
