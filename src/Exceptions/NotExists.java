package Exceptions;

public class NotExists extends Exception {
	private static final long serialVersionUID = 1L;

	public NotExists(final String message) {
		super(message);
	}
}
