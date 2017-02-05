package Exceptions;

public class NotExists extends Exception {
	private static final long serialVersionUID = 1L;

	public NotExists(String message) {
		super(message);
	}
}
