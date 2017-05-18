package util;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.parse4j.ParseException;

import data.management.DBManager;
import data.management.DatabaseManager;
import data.members.Area;
import data.members.MapLocation;
import data.members.ParkingSlotStatus;
import data.members.StickersColor;

public class Validation {
	
	/**
	 * @author Inbal Matityahu
	 * @since 6/5/17 
	 * This class validates arguments of all the system class
	 */
	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	
	public static boolean isInt(String input) throws Exception {
		try {
			Integer.parseInt(input);
			return true;
		} catch(NumberFormatException e) {
			LOGGER.warning("the given input" + input + "is not a number");
			return false;
		}
	}
	
	public static boolean isFullNamePattern(String input) {
		return Pattern.compile("^[\\p{L} .'-]+$", Pattern.CASE_INSENSITIVE).matcher(input).find();
	}
	
	public static boolean isLicensePlatePattern(String input) {
		boolean b = Pattern.compile("^[0-9]{6,7}$", Pattern.CASE_INSENSITIVE).matcher(input).find();
		if (b) return b;
		LOGGER.warning("Licens plate is not in the right format: " + input);
		return b;
	}

	public static boolean validateMail(String emailStr) {
        boolean b = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr).find();
		if (b) return b;
		LOGGER.warning("Email is not in the right format: "+emailStr);
		return b;
	}
	
	public static boolean isValidDriverId(String driverId) {
		boolean b = Pattern.compile("^[0-9]{9}$", Pattern.CASE_INSENSITIVE).matcher(driverId).find();
		if (b) return b;
		LOGGER.warning("Driver id is not in the right format"+driverId);
		return b;
}
	
	public static boolean validatePassword(String password, int i, int m) {
		boolean b = password != null && password.length() >= i && password.length() <= m;
		if (b) return b;
		LOGGER.warning("Password is not in the right format"+password);
		return b;
	}
	
	public static boolean validateCarId(String carId) {
		boolean b = carId != null && carId.length() == 7;
		if (b) return b;
		LOGGER.warning("Car Id is not in the right format"+carId);
		return b;
	}
	
	public static boolean validateNewDriver(final String id, final String email, final String carId, final String password) throws ParseException
	{	
		boolean b = id != null && email != null && carId != null && password != null;
		if (b) return b;
		LOGGER.warning("Driver is not in the right format");
		return b;
	}
	
	public static boolean isIdExist(final String newId, DatabaseManager dbm){
		dbm.initialize();
		final String objectClass = "Driver";
		Map<String, Object> key = new HashMap<String, Object>();
		key.put("id", newId);
		return (!dbm.getObjectFieldsByKey(objectClass, key).isEmpty());
	}
	
	public static void validateNewParkingSlot(final ParkingSlotStatus s, final StickersColor c, final StickersColor defaultColor, final MapLocation l, final Area a)
			throws IllegalArgumentException {
		if (s == null){
			LOGGER.severe("Driver is not in the right format");
			throw new IllegalArgumentException("status can not be empty!");
		}
		if (c == null){
			LOGGER.severe("color can not be empty!");
			throw new IllegalArgumentException("color can not be empty!");
		}
		if (defaultColor == null){
			LOGGER.severe("defaultColor can not be empty!");
			throw new IllegalArgumentException("defaultColor can not be empty!");
		}
		if (a == null){
			LOGGER.severe("area can not be empty!");
			throw new IllegalArgumentException("area can not be empty!");
		}
		if (l != null)
			return;
		LOGGER.severe("location can not be empty!");
		throw new IllegalArgumentException("location can not be empty!");
	}
}