package Exceptions;

public class AlreadyExists extends Exception {
	private static final long serialVersionUID = 1L;
	public AlreadyExists(String message) {
        super(message);
    }
}

