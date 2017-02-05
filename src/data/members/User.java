package data.members;

import java.util.List;
import java.util.Date;

import org.parse4j.ParseException;

import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

import Exceptions.LoginException;
import data.management.DBManager;

/**
 * @author DavidCohen55
 * @author sefialbo
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

	// last log in time
	private Date lastLoginTime;

	private static final String USER_NAME = "username";
	private static final String PASSWORD = "password";
	private static final String PHONE_NUMBER = "phoneNumber";
	private static final String CAR_NUMBER = "carNumber";
	private static final String STICKER = "sticker";
	private static final String EMAIL = "email";
	private static final String PARKING = "currentParking";
	private static final String LAST_LOGIN_TIME = "lastLoginTime";
	private static final String TABLE_NAME = "PMUser";

	public User(final String name, final String password, final String phoneNumber, final String carNumber, final String email, final StickersColor type,
			final ParkingSlot currentLocation) throws ParseException {
		DBManager.initialize();
		setParseObject(TABLE_NAME);
		setName(name);
		setPassword(password);
		setPhoneNumber(phoneNumber);
		setCarName(carNumber);
		setSticker(type);
		setEmail(email);
		setCurrentParking(currentLocation);
		setLastLoginTime(null);
		setObjectId();
	}

	public User(final String carNumber) throws LoginException {
		DBManager.initialize();
		parseObject = getDbUserObject(carNumber);
		if (parseObject == null)
			throw new LoginException("user doesn't exist");
		name = parseObject.getString(USER_NAME);
		password = parseObject.getString(PASSWORD);
		phoneNumber = parseObject.getString(PHONE_NUMBER);
		this.carNumber = parseObject.getString(CAR_NUMBER);
		sticker = StickersColor.values()[parseObject.getInt(STICKER)];
		email = parseObject.getString(EMAIL);
		lastLoginTime = parseObject.getDate(LAST_LOGIN_TIME);
		objectId = parseObject.getObjectId();
		if (parseObject.getParseObject(PARKING) == null)
			return;
		final ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingSlot");
		try {
			currentParking = new ParkingSlot(query.get(parseObject.getParseObject(PARKING).getObjectId()));
		} catch (final ParseException e) {
		}
	}

	public User(final ParseObject obj) throws ParseException {
		DBManager.initialize();
		parseObject = obj;
		name = parseObject.getString("username");
		carNumber = parseObject.getString("carNumber");
		email = parseObject.getString("email");
		password = parseObject.getString("password");
		phoneNumber = parseObject.getString("phoneNumber");
		sticker = StickersColor.values()[parseObject.getInt("sticker")];
		currentParking = parseObject.getParseObject("currentParking") == null ? null
				: new ParkingSlot(parseObject.getParseObject("currentParking"));
		lastLoginTime = parseObject.getDate(LAST_LOGIN_TIME);

		objectId = parseObject.getObjectId();
	}

	/* Get functions */

	private static ParseObject getDbUserObject(final String carNumber) {
		final ParseQuery<ParseObject> query = ParseQuery.getQuery(TABLE_NAME);
		query.whereEqualTo(CAR_NUMBER, carNumber);
		try {
			final List<ParseObject> $ = query.find();
			return $ == null || $.size() != 1 ? null : $.get(0);
		} catch (final Exception e) {
			return null;
		}
	}

	public ParkingSlot getCurrentParking() {
		ParseObject o = null;
		if (currentParking != null)
			try {
				o = new ParseQuery<ParseObject>("ParkingSlot").get(currentParking.getObjectId());
			} catch (final ParseException e) {
				System.out.println("could'nt find");
				return null;
			}
		try {
			setParseCurrentParking(o);
		} catch (final ParseException e) {
			System.out.println("error");
			return null;
		}
		return currentParking;
	}

	public String getName() {
		return name;
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

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	/* Set functions */
	public void setName(final String name) throws ParseException {
		this.name = name;
		parseObject.put(USER_NAME, name);
		parseObject.save();
	}

	/***
	 * 
	 * @param currentParking,
	 *            object from ParkingSlot class
	 * @throws ParseException
	 */
	public void setCurrentParking(final ParkingSlot currentParking) throws ParseException {
		this.currentParking = currentParking;
		if (currentParking == null)
			parseObject.remove(PARKING);
		else
			parseObject.put(PARKING, currentParking.getParseObject());
		parseObject.save();
	}

	/***
	 * 
	 * @param currentParking,
	 *            object from Parse server
	 * @throws ParseException
	 */
	public void setParseCurrentParking(final ParseObject currentParking) throws ParseException {
		if (currentParking == null) {
			setCurrentParking(null);
			return;
		}
		this.currentParking = new ParkingSlot(currentParking);
		parseObject.save();
	}

	public void setLastLoginTime(final Date lastLoginTime) throws ParseException {
		this.lastLoginTime = lastLoginTime;
		if (lastLoginTime == null)
			parseObject.remove(LAST_LOGIN_TIME);
		else
			parseObject.put(LAST_LOGIN_TIME, lastLoginTime);
		parseObject.save();
	}

	public void setPassword(final String password) throws ParseException {
		this.password = password;
		parseObject.put(PASSWORD, password);
		parseObject.save();
	}

	public void setPhoneNumber(final String phoneNumber) throws ParseException {
		this.phoneNumber = phoneNumber;
		parseObject.put(PHONE_NUMBER, phoneNumber);
		parseObject.save();
	}

	public void setSticker(final StickersColor type) throws ParseException {
		sticker = type;
		parseObject.put(STICKER, type.ordinal());
		parseObject.save();
	}

	public void setCarName(final String carNum) throws ParseException {
		carNumber = carNum;
		parseObject.put(CAR_NUMBER, carNum);
		parseObject.save();
	}

	public void setEmail(final String email) throws ParseException {
		this.email = email;
		parseObject.put(EMAIL, email);
		parseObject.save();
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
	public void updateUser(final String name, final String phoneNumber) throws ParseException {
		setName(name);
		setPhoneNumber(phoneNumber);
		parseObject.save();
	}

	public void updatePassword(final String newPassword, final String passwordVerify) throws ParseException {
		if (!newPassword.equals(passwordVerify))
			return;
		password = newPassword;
		parseObject.put(PASSWORD, password);
		parseObject.save();
	}

}
