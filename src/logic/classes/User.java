package logic.classes;

/**
 * @Author DavidCohen55
 */

enum StickerType{BLUE,BLUE_WHITE,YELLOW,GREEN,GREEN_RED,RED,ORANGE}
	
public class User {
	
	private String name;
	private String password;
	private String phoneNumber;
	private String carNumber;
	private StickerType sticker; 

	public User(String name,String password,String phoneNumber,String carNumber,StickerType type){
			this.name=name;
			this.password=password;
			this.phoneNumber=phoneNumber;
			this.carNumber=carNumber;
			this.sticker=type;
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
	
	public void setSticker(StickerType ¢) {
		this.sticker = ¢;
	}
}
