package util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.parse4j.ParseException;

import data.management.DBManager;
import data.members.MapLocation;
import data.members.ParkingSlotStatus;
import data.members.StickersColor;

public class Validation {
	
	/**
	 * @author Inbal Matityahu
	 * @since 6/5/17 This class validates arguments of all the system class
	 */
	
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
	
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	public static boolean validateMail(String emailStr) {
		        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
		        return matcher.find();
	}
	
	public static boolean validatePassword(String password, int n, int m) {
		  if (password == null || password.length() < n || password.length() > m) {
		    return false;
		  }
		  return true;
	}
	
	public static boolean validateCarId(String carId) {
		if(carId==null || carId.length()!=7)
			return false;
		return true;
	}
	
	public static boolean validateNewDriver(final String id, final String email, final String carId, final String password) throws ParseException
	{	
		if (id == null || email == null || carId == null || password == null)
			return false;
		else
			return true;
	}
	
	public static boolean isIdExist(final String newId) throws ParseException{
		DBManager.initialize();
		final String objectClass = "Driver";
		System.out.println("i was here 2");
		Map<String, Object> key = new HashMap<String, Object>();
		key.put("id", newId);
		System.out.println("i was here 2.5");
		return (DBManager.getObjectFieldsByKey(objectClass, key).isEmpty()==false);
	}
	
	public static void validateNewParkingSlot(final ParkingSlotStatus s, final StickersColor c, final StickersColor defaultColor, final MapLocation l)
			throws IllegalArgumentException {
		if (s == null)
			throw new IllegalArgumentException("status can not be empty!");
		if (c == null)
			throw new IllegalArgumentException("color can not be empty!");
		if (defaultColor == null)
			throw new IllegalArgumentException("defaultColor can not be empty!");
		if (l == null)
			throw new IllegalArgumentException("location can not be empty!");
	}
}