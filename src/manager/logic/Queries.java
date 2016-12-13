package manager.logic;

import javax.tools.JavaFileManager.Location;

import data.members.Management;
import data.members.ParkingArea;
import data.members.ParkingSlot;
import data.members.ParkingSlotColor;
import data.members.ParkingSlotStatus;
import data.members.StickerType;
import data.members.User;

/**
 * @author Inbal Matityahu
 * @since 12.13.16
 * 
 *       The management's system can send or receive data by queries to the databases.
 *       This class holds the possible queries.
 */

public class Queries {
	private Management managment;

	public Management getManagment() {
		return managment;
	}

	public void setManagment(Management managment) {
		this.managment = managment;
	}
	
	// Return sticker type of a given user
	public StickerType getColorByUser(User user) {
		return this.managment.getColorByUser(user);
	}

	// Return parking slot id by a given user
	public ParkingSlot getParkingslotByUser(User user) {
		return this.managment.getParkingslotByUser(user);
	}

	// Return parking slot id by a given user
	public User getUserByParkingslot(ParkingSlot parkinSlot) {
		return this.managment.getUserByParkingslot(parkinSlot);
	}

	// Return num of taken parking slots by a given area
	public int getNumOfTakenByArea(ParkingArea area) {
		return this.managment.getNumOfTakenByArea(area);
	}

	// Return num of free parking slots by given area
	public int getNumOfFreeByArea(ParkingArea area) {
		return this.managment.getNumOfFreeByArea(area);
	}

	// Return num of free parking slots
	public int getNumOfFreeSlots() {
		return this.managment.getNumOfFreeSlots();
	}

	// Return num of taken parking slots
	public int getNumOfTakenSlots() {
		return this.managment.getNumOfTakenSlots();
	}

	// Return parking slots per area
	public int getNumOfSlotsByArea(ParkingArea area) {
		return this.managment.getNumOfSlotsByArea(area);
	}

	// Return a free parking slot by a given area
	public ParkingSlot getParkingslotByArea(ParkingArea area) {
		return this.managment.getParkingslotByArea(area);
	}
	
	//Return user according to user carNum
	public User returnUser(String carNum){
		for (User currentUser : this.managment.getUsers()) {
			if (currentUser.getCarNumber().equals(carNum)) {
				return currentUser;
			}
		}
		return null;
	}
	
	//Return user userName according to user carNum
	public String returnUserName(String carNum){
		User currentUser=this.returnUser(carNum);
		if (currentUser!=null)
			return currentUser.getName();
		return null;
	}
	
	//Return user password according to user carNum
	public String returnUserPassword(String carNum){
		User currentUser=this.returnUser(carNum);
		if (currentUser!=null)
			return currentUser.getPassword();
		return null;
	}
	
	//Return user phoneNum according to user carNum
	public String returnUserPhoneNum(String carNum){
		User currentUser=this.returnUser(carNum);
		if (currentUser!=null)
			return currentUser.getPhoneNumber();
		return null;
	}
	
	//Return user sticker according to user carNum
	public StickerType returnUserSticker(String carNum){
		User currentUser=this.returnUser(carNum);
		if (currentUser!=null)
			return currentUser.getSticker();
		return null;
	}
	
	//Return user current parking according to user carNum
	public ParkingSlot returnUserCurrentParking(String carNum){
		User currentUser=this.returnUser(carNum);
		if (currentUser!=null)
			return currentUser.getCurrentParking();
		return null;
	}

	//Return parking slot according to given location
	public ParkingSlot returnParkingSlot(Location ¢){
		for (ParkingSlot $ : this.managment.getParkingSlots())
			if ($.getLocation().equals(¢)) {
				return $;
			}
		return null;
	}
	
	//Return parking slot's status according to given location
	public ParkingSlotStatus returnParkingSlotStatus(Location l){
		ParkingSlot currentSlot=this.returnParkingSlot(l);
		if (currentSlot!=null){
			return currentSlot.getStatus();
		}
		return null;
	}
	
	//Return parking slot's color according to given location
	public ParkingSlotColor returnParkingSlotColor(Location l){
		ParkingSlot currentSlot=this.returnParkingSlot(l);
		if (currentSlot!=null){
			return currentSlot.getColor();
		}
		return null;
	}
	
	//Return parking slot's area according to given location
	public ParkingArea returnParkingSlotArea(Location l){
		ParkingSlot currentSlot=this.returnParkingSlot(l);
		if (currentSlot!=null){
			return currentSlot.getParkingArea();
		}
		return null;
	}
	
	//Return parking slot's current user according to given location
	public User returnParkingSlotCurrentUser(Location l){
		ParkingSlot currentSlot=this.returnParkingSlot(l);
		if (currentSlot!=null){
			return currentSlot.getCurrentUser();
		}
		return null;
	}
	
	//Return parkingArea according to areaId
	public ParkingArea returnArea(int areaID){
		for (ParkingArea currentArea : this.managment.getParkingAreas().getParkingAreas()) {
			if (currentArea.getAreaId()==areaID) {
				return currentArea;
			}
		}
		return null;
	}
}
