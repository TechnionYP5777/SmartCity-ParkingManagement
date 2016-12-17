package data.members;

import java.util.HashSet;
import java.util.Set;
import data.members.StickersColor;
import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

import data.management.DBManager;

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
	private ParseObject management;

	public Management() {
		this.users = new HashSet<User>();
		this.parkingSlots = new HashSet<ParkingSlot>();
	}

	public Management(Set<User> users, Set<ParkingSlot> parkingSlots, ParkingAreas parkingAreas) throws ParseException {
		// will populate private fields according to the data stored in the DB
		DBManager.initialize();
		this.management = new ParseObject("Management");
		this.parkingAreas = parkingAreas;
		this.parkingSlots = parkingSlots;
		this.users = users;
		management.save();
	}

	public ParkingAreas getParkingAreas() {
		return parkingAreas;
	}

	public void setParkingAreas(ParkingAreas ¢) {
		this.parkingAreas = ¢;
		this.management.put("parkingAreas", this.getParkingAreas());
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> ¢) {
		this.users = ¢;
		this.management.put("Users", this.getUsers());
	}

	public Set<ParkingSlot> getParkingSlots() {
		return parkingSlots;
	}

	public void setParkingSlots(Set<ParkingSlot> ¢) {
		this.parkingSlots = ¢;
		this.management.put("parkingSlots", this.getParkingSlots());
	}

	public void addUser(User newUser) {
		this.users.add(newUser);
		this.management.put("Users", this.getUsers());

	}

	public void addParkingSlot(ParkingSlot newParkingSlot) {
		this.parkingSlots.add(newParkingSlot);
		this.management.put("parkingSlots", this.getParkingSlots());
	}

	public void removeUser(User userToRemove) {
		this.users.remove(userToRemove);
		this.management.put("Users", this.getUsers());
	}

	public void removeParkingSlot(ParkingSlot slotToRemove) {
		this.parkingSlots.remove(slotToRemove);
		this.management.put("parkingSlots", this.getParkingSlots());
	}

	// Return sticker type of a given user
	public StickersColor getColorByUser(User u) {
		// search the given user
		ParseQuery<ParseObject> query2 = ParseQuery.getQuery("PMUser");
		query2.whereEqualTo("carNumber", u.getCarNumber());
		try {
			if (query2.find().size() != 1) {
				System.out.format("Something went worng while searching for %d", u.getCarNumber());
				return null;
			}

		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		StickersColor $ = null;
		if (!this.users.contains(u))
			return $;
		for (User currentUser : this.users)
			if (currentUser.getCarNumber().equals(u.getCarNumber()))
				$ = currentUser.getSticker();
		return $;
	}

	// Return parking slot id by a given user
	public ParkingSlot getParkingslotByUser(User u) {
		// search the given user
		ParseQuery<ParseObject> query2 = ParseQuery.getQuery("PMUser");
		query2.whereEqualTo("carNumber", u.getCarNumber());
		try {
			if (query2.find().size() != 1) {
				System.out.format("Something went worng while searching for %d", u.getCarNumber());
				return null;
			}

		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		ParkingSlot $ = null;
		if (!this.users.contains(u))
			return $;
		for (User currentUser : this.users)
			if (currentUser.getCarNumber().equals(u.getCarNumber()))
				$ = currentUser.getCurrentParking();
		return $;
	}

	// Return parking slot id by a given user
	public User getUserByParkingslot(ParkingSlot parkinSlot) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingSlot");
		query.whereEqualTo("name", parkinSlot.getName());
		try {
			if (query.find().size() != 1) {
				System.out.format("Something went worng while searching for %d", parkinSlot.getName());
				return null;
			}

		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		User $ = null;
		if (!this.parkingSlots.contains(parkinSlot))
			return null;
		//search current user in parkingSlot
		for (User ¢ : this.getUsers())
			if (¢.getCurrentParking().getName().equals(parkinSlot.getName()))
				return ¢;
		return $;
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
