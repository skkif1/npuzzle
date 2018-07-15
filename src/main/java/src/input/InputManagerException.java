package src.input;

public class InputManagerException extends RuntimeException {

	public InputManagerException(String message) {
		super("Bad file : " + message); // TODO: 7/15/18 Write input info and exit
	}
}
