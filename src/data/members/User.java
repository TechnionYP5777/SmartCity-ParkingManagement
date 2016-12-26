package data.members;

import java.util.List;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

import Exceptions.LoginException;
import data.management.DBManager;

/**
 * @Author DavidCohen55
 */

public class User extends dbMember {

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

	public User(String name, String password, String phoneNumber, String carNumber, String email, StickersColor type,
			ParkingSlot currentLocation) throws ParseException {
		DBManager.initialize();
		this.setParseObject(TABLE_NAME);
		this.setName(name);
		this.setPassword(password);
		this.setPhoneNumber(phoneNumber);
		this.setCarName(carNumber);
		this.setSticker(type);
		this.setEmail(email);
		this.setCurrentParking(currentLocation);
		this.setObjectId();
	}

	public User(String carNumber) throws LoginException {
		DBManager.initialize();
		this.parseObject = getDbUserObject(carNumber);
		if (this.parseObject == null)
			throw new LoginException("user doesn't exist");
		this.name = this.parseObject.getString(USER_NAME);
		this.password = this.parseObject.getString(PASSWORD);
		this.phoneNumber = this.parseObject.getString(PHONE_NUMBER);
		this.carNumber = this.parseObject.getString(CAR_NUMBER);
		this.sticker = StickersColor.values()[this.parseObject.getInt(STICKER)];
		this.email = this.parseObject.getString(EMAIL);
		this.objectId = this.parseObject.getObjectId();
		if (this.parseObject.getParseObject(PARKING) == null)
			return;
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingSlot");
		try {
			this.currentParking = new ParkingSlot(query.get(this.parseObject.getParseObject(PARKING).getObjectId()));
		} catch (ParseException e) {
		}
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
		ParseObject o = null;
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("ParkingSlot");
		if (currentParking != null)
			try {
				o = query.get(currentParking.getobjectId());
			} catch (ParseException e) {
				System.out.println("could'nt find");
				return null;
			}
		try {
			this.setParseCurrentParking(o);
		} catch (ParseException e) {
			System.out.println("error");
			return null;
		}
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

	/* Set functions */
	public void setName(String name) throws ParseException {
		this.name = name;
		this.parseObject.put(USER_NAME, name);
		this.parseObject.save();
	}

	/***
	 * 
	 * @param currentParking,
	 *            object from ParkingSlot class
	 * @throws ParseException
	 */
	public void setCurrentParking(ParkingSlot currentParking) throws ParseException {
		this.currentParking = currentParking;
		if (currentParking == null)
			this.parseObject.remove(PARKING);
		else
			this.parseObject.put(PARKING, currentParking.getParseObject());
		this.parseObject.save();
	}

	/***
	 * 
	 * @param currentParking,
	 *            object from Parse server
	 * @throws ParseException
	 */
	public void setParseCurrentParking(ParseObject currentParking) throws ParseException {
		if (currentParking == null) {
			this.setCurrentParking(null);
			return;
		}
		this.currentParking = new ParkingSlot(currentParking);
		this.parseObject.save();
	}

	public void setPassword(String password) throws ParseException {
		this.password = password;
		this.parseObject.put(PASSWORD, password);
		this.parseObject.save();
	}

	public void setPhoneNumber(String phoneNumber) throws ParseException {
		this.phoneNumber = phoneNumber;
		this.parseObject.put(PHONE_NUMBER, phoneNumber);
		this.parseObject.save();
	}

	public void setSticker(StickersColor type) throws ParseException {
		this.sticker = type;
		this.parseObject.put(STICKER, type.ordinal());
		this.parseObject.save();
	}

	public void setCarName(String carNum) throws ParseException {
		this.carNumber = carNum;
		this.parseObject.put(CAR_NUMBER, carNum);
		this.parseObject.save();
	}

	public void setEmail(String email) throws ParseException {
		this.email = email;
		this.parseObject.put(EMAIL, email);
		this.parseObject.save();
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
		this.parseObject.save();
	}

	public void updatePassword(String newPassword, String passwordVerify) throws ParseException {
		if (!newPassword.equals(passwordVerify))
			return;
		this.password = newPassword;
		this.parseObject.put(PASSWORD, password);
		this.parseObject.save();
	}

}
