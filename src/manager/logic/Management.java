package manager.logic;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import data.members.ParkingArea;
import data.members.ParkingAreas;
import data.members.ParkingSlot;
import data.members.StickersColor;
import data.members.User;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

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

	public Management() {
		this.users = new HashSet<User>();
		this.parkingSlots = new HashSet<ParkingSlot>();
		// init set of users
		ParseQuery<ParseObject> query = ParseQuery.getQuery("PMUser");
		try {
			List<ParseObject> usersList = query.find();
			if (usersList == null || usersList.isEmpty())
				throw new RuntimeException("There was a problem - Users table doesnt found");
			for (ParseObject ¢ : usersList)
				this.users.add(new User(¢));
		} catch (ParseException ¢) {
			¢.printStackTrace();
		}
		// init set of parking slots
		ParseQuery<ParseObject> query2 = ParseQuery.getQuery("ParkingSlot");
		try {
			List<ParseObject> slotList = query2.find();
			if (slotList == null || slotList.isEmpty())
				throw new RuntimeException("There was a problem - ParkingSlot table doesnt found");
			for (ParseObject ¢ : slotList)
				this.parkingSlots.add(new ParkingSlot(¢));
		} catch (ParseException ¢) {
			¢.printStackTrace();
		}
		this.parkingAreas = new ParkingAreas();
	}

	public Management(Set<User> users, Set<ParkingSlot> parkingSlots, ParkingAreas parkingAreas) throws ParseException {
		this.parkingAreas = parkingAreas;
		this.parkingSlots = parkingSlots;
		this.users = users;
	}

	public ParkingAreas getParkingAreas() {
		return parkingAreas;
	}

	public void setParkingAreas(ParkingAreas ¢) {
		this.parkingAreas = ¢;
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
	public StickersColor getColorByUser(User u) {
		// search the given user
		ParseQuery<ParseObject> query2 = ParseQuery.getQuery("PMUser");
		query2.whereEqualTo("carNumber", u.getCarNumber());
		try {
			if (query2.find().size() != 1) {
				System.out.format("Something went worng while searching for %d", u.getCarNumber());
				return null;
			}

		} catch (ParseException ¢) {
			¢.printStackTrace();
			return null;
		}
		StickersColor $ = null;
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

		} catch (ParseException ¢) {
			¢.printStackTrace();
			return null;
		}
		ParkingSlot $ = null;
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

		} catch (ParseException ¢) {
			¢.printStackTrace();
			return null;
		}
		User $ = null;
		// search current user in parkingSlot
		for (User ¢ : this.getUsers())
			if (¢.getCurrentParking() != null && ¢.getCurrentParking().getName().equals(parkinSlot.getName()))
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
	public ParkingSlot getParkingslotByArea(ParkingArea ¢) throws ParseException {
		return this.parkingAreas.getParkingslotByArea(¢);
	}

}
