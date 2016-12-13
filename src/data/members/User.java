package data.members;

/**
 * @Author DavidCohen55
 */

public class User {

	private String name; // the name of the user
	private String password; // the password of the user
	private String phoneNumber; // the phone number of the user, maybe be use to send notifications to the user
	private String carNumber; // the serial number of the users car will use to identify the user
	private StickerType sticker; // the type of sticker of the user, will determine where can he park
	private ParkingSlot currentParking; // saves the parking slot of a user if he parked 

	public User(String name, String password, String phoneNumber, String carNumber, StickerType type,
			ParkingSlot currentLocation) {
		this.name = name;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.carNumber = carNumber;
		this.sticker = type;
		this.currentParking = currentLocation;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ParkingSlot getCurrentParking() {
		return currentParking;
	}

	public void setCurrentParking(ParkingSlot currentParking) {
		this.currentParking = currentParking;
	}

	public String getName() {
		return this.name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String newPassword, String passwordVerify) {
		if (newPassword.equals(passwordVerify))
			this.password = newPassword;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public StickerType getSticker() {
		return sticker;
	}

	public void setSticker(StickerType type) {
		this.sticker = type;
	}
	
	/**
	 * will be use to update the properties of a user
	 * won't change stickerType because you will need a manager approve
	 * won't change carNumber because this will be identification of a user
	 * @param name
	 * @param phoneNumber
	 */
	 public void updateUser(String name, String phoneNumber){
			this.name = name;
			this.phoneNumber = phoneNumber;
			/*need to update DB*/
	 }
}
