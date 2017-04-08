package util;

public class Validation {
	public static boolean isInt(String input) throws Exception {
		try {
			Integer.parseInt(input);
			return true;
		} catch(NumberFormatException e) {
			throw new Exception("Error: " + input +"is not an Integer");
		}
	}
}