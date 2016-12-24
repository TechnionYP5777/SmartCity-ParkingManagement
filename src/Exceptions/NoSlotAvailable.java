package Exceptions;

public class NoSlotAvailable extends Exception {
	private static final long serialVersionUID = 1L;
	public NoSlotAvailable(String message) {
        super(message);
    }
}
