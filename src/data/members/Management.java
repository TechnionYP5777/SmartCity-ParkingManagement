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
	
	//TODO : extract to a different class containing a set of Areas
	// all parking areas
	private Set<ParkingArea> parkingAreas;

	public Set<ParkingArea> getParkingAreas() {
		return parkingAreas;
	}

	public void setParkingAreas(Set<ParkingArea> parkingAreas) {
		this.parkingAreas = parkingAreas;
	}

	public Management() {
		this.users = new HashSet<User>();
		this.parkingSlots = new HashSet<ParkingSlot>();
		this.parkingAreas = new HashSet<ParkingArea>();
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<ParkingSlot> getParkingSlots() {
		return parkingSlots;
	}

	public void setParkingSlots(Set<ParkingSlot> parkingSlots) {
		this.parkingSlots = parkingSlots;
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

	public void addParkingArea(ParkingArea newParkingArea) {
		this.parkingAreas.add(newParkingArea);
	}

	public void removeParkingArea(ParkingArea parkingArea) {
		this.parkingAreas.remove(parkingArea);
	}

	// Return sticker type of a given user
	public StickerType getColorByUser(User user) {
		StickerType stickerType = null;
		if (!this.users.contains(user)) {
			return stickerType;
		}
		for (User currentUser : this.users) {
			if (currentUser.equals(user)) {
				stickerType = currentUser.getSticker();
			}
		}
		return stickerType;
	}

	// Return parking slot id by a given user
	public ParkingSlot getParkingslotByUser(User user) {
		ParkingSlot parkingSlot = null;
		if (!this.users.contains(user)) {
			return parkingSlot;
		}
		for (User currentUser : this.users) {
			if (currentUser.equals(user)) {
				parkingSlot = currentUser.getCurrentLocation();
			}
		}
		return parkingSlot;
	}

	// Return parking slot id by a given user
	public User getUserByParkingslot(ParkingSlot parkinSlot) {
		User user = null;
		if (!this.parkingSlots.contains(parkinSlot)) {
			return user;
		}
		for (ParkingSlot currentSlot : this.parkingSlots) {
			if (currentSlot.equals(parkinSlot)) {
				user = currentSlot.currentUser;
			}
		}
		return user;
	}

	// Return num of taken parking slots by a given area
	public int getNumOfTakenByArea(ParkingArea area) {
		if (!this.parkingAreas.contains(area)) {
			return 0;
		}
		int count = 0;
		for (ParkingArea currentArea : this.parkingAreas) {
			if (currentArea.equals(area)) {
				count = currentArea.getNumOfTakenSlots();
			}
		}
		return count;
	}

	// Return num of free parking slots by given area
	public int getNumOfFreeByArea(ParkingArea area) {
		if (!this.parkingAreas.contains(area)) {
			return 0;
		}
		int count = 0;
		for (ParkingArea currentArea : this.parkingAreas) {
			if (currentArea.equals(area)) {
				count = currentArea.getNumOfFreeSlots();
			}
		}
		return count;
	}

	// Return num of free parking slots
	public int getNumOfFreeSlots() {
		int count = 0;
		for (ParkingArea currentArea : this.parkingAreas) {
			count += currentArea.getNumOfFreeSlots();
		}
		return count;
	}

	// Return num of taken parking slots
	public int getNumOfTakenSlots() {
		int count = 0;
		for (ParkingArea currentArea : this.parkingAreas) {
			count += currentArea.getNumOfTakenSlots();
		}
		return count;
	}

	// Return parking slots per area
	public int getNumOfSlotsByArea(ParkingArea area) {
		if (!this.parkingAreas.contains(area)) {
			return 0;
		}
		int count = 0;
		for (ParkingArea currentArea : this.parkingAreas) {
			if (currentArea.equals(area)) {
				count = currentArea.getNumOfParkingSlots();
			}
		}
		return count;
	}

	// Return a free parking slot by a given area
	public ParkingSlot getParkingslotByUser(ParkingArea area) {
		ParkingSlot parkingSlot = null;
		if (!this.parkingAreas.contains(area)) {
			return parkingSlot;
		}
		for (ParkingArea currentArea : this.parkingAreas) {
			if (currentArea.equals(area)) {
				for (ParkingSlot currentSlot : currentArea.getFreeSlots()) {
					parkingSlot = currentSlot;
				}
			}
		}
		return parkingSlot;
	}

}
