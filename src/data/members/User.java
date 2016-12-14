package data.members;

import org.parse4j.ParseObject;
import org.parse4j.ParseException;
import data.management.DBManager;

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

	// the type of sticker of the user, will determine where can he park
	private StickersColor sticker;

	// saves the parking slot of a user if he parked
	private ParkingSlot currentParking;

	private ParseObject user;

	public User(String name, String password, String phoneNumber, String carNumber, StickersColor type,
			ParkingSlot currentLocation) throws ParseException {
		DBManager.initialize();
		this.user = new ParseObject("User");
		this.setName(name);
		this.setPassword(password);
		this.phoneNumber = phoneNumber;
		this.carNumber = carNumber;
		this.sticker = type;
		this.currentParking = currentLocation;
		user.save();
	}

	
	/*Get functions*/
	
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
	
	/*Set functions*/
	public void setName(String name) {
		this.name = name;
		this.user.put("name", name);
	}
	
	public void setCurrentParking(ParkingSlot currentParking) {
		this.currentParking = currentParking;
		this.user.put("currentParking", currentParking);
	}
	
	public void setPassword(String password) {
		this.password = password;
		this.user.put("password", password);
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		this.user.put("phoneNumber", phoneNumber);
	}


	public void setSticker(StickersColor type) {
		this.sticker = type;
		this.user.put("sticker", type.ordinal());
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
	
	public void updatePassword(String newPassword, String passwordVerify) {
		if (!newPassword.equals(passwordVerify))
			return;
		this.password = newPassword;
		this.user.put("password", password);
	}

	
}
