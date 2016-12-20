package manager.logic;

import javax.tools.JavaFileManager.Location;

import data.members.Management;
import data.members.ParkingArea;
import data.members.ParkingSlot;
import data.members.StickersColor;
import data.members.ParkingSlotStatus;
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
	public StickersColor getColorByUser(User ¢) {
		return this.managment.getColorByUser(¢);
	}

	// Return parking slot id by a given user
	public ParkingSlot getParkingslotByUser(User ¢) {
		return this.managment.getParkingslotByUser(¢);
	}

	// Return parking slot id by a given user
	public User getUserByParkingslot(ParkingSlot parkinSlot) {
		return this.managment.getUserByParkingslot(parkinSlot);
	}

	// Return num of taken parking slots by a given area
	public int getNumOfTakenByArea(ParkingArea ¢) {
		return this.managment.getNumOfTakenByArea(¢);
	}

	// Return num of free parking slots by given area
	public int getNumOfFreeByArea(ParkingArea ¢) {
		return this.managment.getNumOfFreeByArea(¢);
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
	public int getNumOfSlotsByArea(ParkingArea ¢) {
		return this.managment.getNumOfSlotsByArea(¢);
	}

	// Return a free parking slot by a given area
	public ParkingSlot getParkingslotByArea(ParkingArea ¢) {
		return this.managment.getParkingslotByArea(¢);
	}
	
	//Return user according to user carNum
	public User returnUser(String carNum){
		for (User $ : this.managment.getUsers())
			if ($.getCarNumber().equals(carNum))
				return $;
		return null;
	}
	
	//Return user userName according to user carNum
	public String returnUserName(String carNum){
		User currentUser = this.returnUser(carNum);
		return currentUser == null ? null : currentUser.getName();
	}
	
	//Return user password according to user carNum
	public String returnUserPassword(String carNum){
		User currentUser = this.returnUser(carNum);
		return currentUser == null ? null : currentUser.getPassword();
	}
	
	//Return user phoneNum according to user carNum
	public String returnUserPhoneNum(String carNum){
		User currentUser = this.returnUser(carNum);
		return currentUser == null ? null : currentUser.getPhoneNumber();
	}
	
	//Return user sticker according to user carNum
	public StickersColor returnUserSticker(String carNum){
		User currentUser = this.returnUser(carNum);
		return currentUser == null ? null : currentUser.getSticker();
	}
	
	//Return user current parking according to user carNum
	public ParkingSlot returnUserCurrentParking(String carNum){
		User currentUser = this.returnUser(carNum);
		return currentUser == null ? null : currentUser.getCurrentParking();
	}

	//Return parking slot according to given location
	public ParkingSlot returnParkingSlot(Location ¢){
		for (ParkingSlot $ : this.managment.getParkingSlots())
			if ($.getLocation().equals(¢))
				return $;
		return null;
	}
	
	//Return parking slot's status according to given location
	public ParkingSlotStatus returnParkingSlotStatus(Location l){
		ParkingSlot currentSlot = this.returnParkingSlot(l);
		return currentSlot == null ? null : currentSlot.getStatus();
	}
	
	//Return parking slot's color according to given location
	public StickersColor returnParkingSlotColor(Location l){
		ParkingSlot currentSlot = this.returnParkingSlot(l);
		return currentSlot == null ? null : currentSlot.getColor();
	}
	
	//Return parking slot's current user according to given location
	public User returnParkingSlotCurrentUser(Location l){
		ParkingSlot currentSlot=this.returnParkingSlot(l);
		for (User $ : this.managment.getUsers())
			if ($.getCurrentParking().getName().equals(currentSlot.getName()))
				return $;
		return null;
	}
	
	//Return parkingArea according to areaId
	public ParkingArea returnArea(int areaID){
		for (ParkingArea $ : this.managment.getParkingAreas().getParkingAreas())
			if ($.getAreaId() == areaID)
				return $;
		return null;
	}
}
