package data.members;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Inbal Matityahu
 * @since 12.11.16
 * 
 *        This class represent the data of Technion's security unit
 */
public class Management {

	// users in the system
	private Set<User> users;
	// all parking slots on Technion area
	private Set<ParkingSlot> parkingSlots;
	// all parking areas
	private ParkingAreas parkingAreas;

	public ParkingAreas getParkingAreas() {
		return parkingAreas;
	}

	public void setParkingAreas(ParkingAreas ¢) {
		this.parkingAreas = ¢;
	}

	public Management() {
		this.users = new HashSet<User>();
		this.parkingSlots = new HashSet<ParkingSlot>();
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> ¢) {
		this.users = ¢;
	}

	public Set<ParkingSlot> getParkingSlots() {
		return parkingSlots;
	}

	public void setParkingSlots(Set<ParkingSlot> ¢) {
		this.parkingSlots = ¢;
	}

	public void addUser(User newUser) {
		this.users.add(newUser);
	}

	public void addParkingSlot(ParkingSlot newParkingSlot) {
		this.parkingSlots.add(newParkingSlot);
	}

	public void removeUser(User userToRemove) {
		this.users.remove(userToRemove);
	}

	public void removeParkingSlot(ParkingSlot slotToRemove) {
		this.parkingSlots.remove(slotToRemove);
	}

	// Return sticker type of a given user
	public StickerType getColorByUser(User u) {
		StickerType $ = null;
		if (!this.users.contains(u))
			return $;
		for (User currentUser : this.users)
			if (currentUser.equals(u))
				$ = currentUser.getSticker();
		return $;
	}

	// Return parking slot id by a given user
	public ParkingSlot getParkingslotByUser(User u) {
		ParkingSlot $ = null;
		if (!this.users.contains(u))
			return $;
		for (User currentUser : this.users)
			if (currentUser.equals(u))
				$ = currentUser.getCurrentParking();
		return $;
	}

	// Return parking slot id by a given user
	public User getUserByParkingslot(ParkingSlot parkinSlot) {
<<<<<<< HEAD
		User user = null;
		if (!this.parkingSlots.contains(parkinSlot)) {
			return user;
		}
		for (ParkingSlot currentSlot : this.parkingSlots) {
			if (currentSlot.equals(parkinSlot)) {
//				user = currentSlot.getCurrentUser();
			}
		}
		return user;
=======
		User $ = null;
		if (!this.parkingSlots.contains(parkinSlot))
			return $;
		for (ParkingSlot currentSlot : this.parkingSlots)
			if (currentSlot.equals(parkinSlot))
				$ = currentSlot.getCurrentUser();
		return $;
>>>>>>> 77e2db89f4a16dbc2d3d8a532e201116ed8716bd
	}

	// Return num of taken parking slots by a given area
	public int getNumOfTakenByArea(ParkingArea ¢) {
		return this.parkingAreas.getNumOfTakenByArea(¢);
	}

	// Return num of free parking slots by given area
	public int getNumOfFreeByArea(ParkingArea ¢) {
		return this.parkingAreas.getNumOfFreeByArea(¢);
	}

	// Return num of free parking slots
	public int getNumOfFreeSlots() {
		return this.parkingAreas.getNumOfFreeSlots();
	}

	// Return num of taken parking slots
	public int getNumOfTakenSlots() {
		return this.parkingAreas.getNumOfTakenSlots();
	}

	// Return parking slots per area
	public int getNumOfSlotsByArea(ParkingArea ¢) {
		return this.parkingAreas.getNumOfSlotsByArea(¢);
	}

	// Return a free parking slot by a given area
	public ParkingSlot getParkingslotByArea(ParkingArea ¢) {
		return this.parkingAreas.getParkingslotByArea(¢);
	}

}
