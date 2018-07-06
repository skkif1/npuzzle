package src.input;

public class InputManagerException extends RuntimeException {

	public InputManagerException(String message) {
		super("Bad file : " + message);
	}
}
