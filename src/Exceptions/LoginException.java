package Exceptions;

/**
 * @Author DavidCohen55
 */
public class LoginException extends Exception {
	private static final long serialVersionUID = 1L;
	public String exception;

	public LoginException(final String str) {
		exception = str;
	}

	@Override
	public String toString() {
		return exception;
	}
}