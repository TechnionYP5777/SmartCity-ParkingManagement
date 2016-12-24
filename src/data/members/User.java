package data.members;

import java.util.List;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

import Exceptions.LoginException;
import data.management.DBManager;
import logic.DbUser;

/**
 * @Author DavidCohen55
 */

public class User {

	// the name of the user
	private String name;

	// the password of the user
	private String password;

	// the phone number of the user, maybe be use to send notifications to the
	// user
	private String phoneNumber;

	// the serial number of the users car will use to identify the user
	private String carNumber;

	// the email of the users, will use to communicate with the user
	private String email;

	
	// the type of sticker of the user, will determine where can he park
	private StickersColor sticker;

	// saves the parking slot of a user if he parked
	private ParkingSlot currentParking;
	
	private static final String USER_NAME = "username";
	private static final String PASSWORD = "password";
	private static final String PHONE_NUMBER = "phoneNumber";
	private static final String CAR_NUMBER = "carNumber";
	private static final String STICKER = "sticker";
	private static final String EMAIL = "email";
	private static final String PARKING = "currentParking";
	private static final String TABLE_NAME = "PMUser";
	
	private ParseObject user;

	public User(String name, String password, String phoneNumber, String carNumber,
			String email, StickersColor type, ParkingSlot currentLocation) throws ParseException {
		DBManager.initialize();
		this.user = new ParseObject(TABLE_NAME);
		this.setName(name);
		this.setPassword(password);
		this.setPhoneNumber(phoneNumber);
		this.setCarName(carNumber);
		this.setSticker(type);
		this.setEmail(email);
		this.setCurrentParking(currentLocation);
	}
	
	public User(String carNumber) throws LoginException {
		DBManager.initialize();
		this.user = getDbUserObject(carNumber);
		if (this.user == null) throw new LoginException("user doesn't exist");
		this.name = this.user.getString(USER_NAME);
		this.password = this.user.getString(PASSWORD);
		this.phoneNumber = this.user.getString(PHONE_NUMBER);
		this.carNumber = this.user.getString(CAR_NUMBER);
		this.sticker = StickersColor.values()[this.user.getInt(STICKER)];
		this.email = this.user.getString(EMAIL);
		//this.currentParking = useObj.getString(PARKING);
	}

	private static ParseObject getDbUserObject(String carNumber) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery(TABLE_NAME);
		query.whereEqualTo(CAR_NUMBER, carNumber);
		try {
			List<ParseObject> users = query.find();
			return users == null || users.size() != 1 ? null : users.get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	/* Get functions */

	public ParkingSlot getCurrentParking() {
		return currentParking;
	}

	public String getName() {
		return this.name;
	}

	public String getPassword() {
		return password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public StickersColor getSticker() {
		return sticker;
	}

	public String getEmail() {
		return email;
	}
	
	public void DeleteUser() throws ParseException {
		this.user.delete();
	}

	/* Set functions */
	public void setName(String name) throws ParseException {
		this.name = name;
		this.user.put(USER_NAME, name);
		this.user.save();
	}

	public void setCurrentParking(ParkingSlot currentParking) throws ParseException {
		this.currentParking = currentParking;
		//this.user.put(PARKING, currentParking.getName());
		this.user.save();
	}

	public void setPassword(String password) throws ParseException {
		this.password = password;
		this.user.put(PASSWORD, password);
		this.user.save();
	}

	public void setPhoneNumber(String phoneNumber) throws ParseException {
		this.phoneNumber = phoneNumber;
		this.user.put(PHONE_NUMBER, phoneNumber);
		this.user.save();
	}

	public void setSticker(StickersColor type) throws ParseException {
		this.sticker = type;
		this.user.put(STICKER, type.ordinal());
		this.user.save();
	}

	public void setCarName(String carNum) throws ParseException {
		this.carNumber = carNum;
		this.user.put(CAR_NUMBER, carNum);
		this.user.save();
	}
	
	public void setEmail(String email) throws ParseException {
		this.email = email;
		this.user.put(EMAIL, email);
		this.user.save();
	}

	/**
	 * will be use to update the properties of a user won't change stickerType
	 * because you will need a manager approve won't change carNumber because
	 * this will be identification of a user
	 * 
	 * @param name
	 * @param phoneNumber
	 * @throws ParseException
	 */
	public void updateUser(String name, String phoneNumber) throws ParseException {
		this.setName(name);
		this.setPhoneNumber(phoneNumber);
		this.user.save();
	}

	public void updatePassword(String newPassword, String passwordVerify) throws ParseException {
		if (!newPassword.equals(passwordVerify))
			return;
		this.password = newPassword;
		this.user.put(PASSWORD, password);
		this.user.save();
	}

	public String getTableID() {
		return this.user.getObjectId();
	}

}
