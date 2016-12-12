package data.members;

/**
 * @Author DavidCohen55
 */

public class User {

	private String name;
	private String password;
	private String phoneNumber;
	private String carNumber;
	private StickerType sticker;
	private ParkingSlot currentParking;

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

	public ParkingSlot getCurrentLocation() {
		return currentParking;
	}

	public void setCurrentLocation(ParkingSlot currentLocation) {
		this.currentParking = currentLocation;
	}

	public String getName() {
		return this.name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public StickerType getSticker() {
		return sticker;
	}

	public void setSticker(StickerType type) {
		this.sticker = type;
	}
}
