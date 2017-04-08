package util;

import java.util.regex.Pattern;

public class Validation {
	public static boolean isInt(String input) throws Exception {
		try {
			Integer.parseInt(input);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}
	
	public static boolean isFullNamePattern(String input) {
		return Pattern.compile("^[\\p{L} .'-]+$", Pattern.CASE_INSENSITIVE).matcher(input).find();
	}
	
	public static boolean isLicensePlatePattern(String input) {
		return Pattern.compile("^[0-9]{6,7}$", Pattern.CASE_INSENSITIVE).matcher(input).find();
	}
}