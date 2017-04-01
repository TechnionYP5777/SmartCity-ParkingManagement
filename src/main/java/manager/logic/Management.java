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
		users = new HashSet<User>();
		parkingSlots = new HashSet<ParkingSlot>();
		// init set of users
		final ParseQuery<ParseObject> query = ParseQuery.getQuery("PMUser");
		try {
			final List<ParseObject> usersList = query.find();
			if (usersList == null || usersList.isEmpty())
				throw new RuntimeException("There was a problem - Users table doesnt found");
			for (final ParseObject ¢ : usersList)
				users.add(new User(¢));
		} catch (final ParseException ¢) {
			¢.printStackTrace();
		}
		// init set of parking slots
		final ParseQuery<ParseObject> query2 = ParseQuery.getQuery("ParkingSlot");
		try {
			final List<ParseObject> slotList = query2.find();
			if (slotList == null || slotList.isEmpty())
				throw new RuntimeException("There was a problem - ParkingSlot table doesnt found");
			for (final ParseObject ¢ : slotList)
				parkingSlots.add(new ParkingSlot(¢));
		} catch (final ParseException ¢) {
			¢.printStackTrace();
		}
		parkingAreas = new ParkingAreas();
	}

	public Management(final Set<User> users, final Set<ParkingSlot> parkingSlots, final ParkingAreas parkingAreas) throws ParseException {
		this.parkingAreas = parkingAreas;
		this.parkingSlots = parkingSlots;
		this.users = users;
	}

	public ParkingAreas getParkingAreas() {
		return parkingAreas;
	}

	public void setParkingAreas(final ParkingAreas ¢) {
		parkingAreas = ¢;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(final Set<User> ¢) {
		users = ¢;

	}

	public Set<ParkingSlot> getParkingSlots() {
		return parkingSlots;
	}

	public void setParkingSlots(final Set<ParkingSlot> ¢) {
		parkingSlots = ¢;

	}

	public void addUser(final User newUser) {
		users.add(newUser);

	}

	public void addParkingSlot(final ParkingSlot newParkingSlot) {
		parkingSlots.add(newParkingSlot);

	}

	public void removeUser(final User userToRemove) {
		users.remove(userToRemove);

	}

	public void removeParkingSlot(final ParkingSlot slotToRemove) {
		parkingSlots.remove(slotToRemove);

	}

	// Return sticker type of a given user
	public StickersColor getColorByUser(final User u) {
		// search the given user
		final ParseQuery<ParseObject> query2 = ParseQuery.getQuery("PMUser");
		query2.whereEqualTo("carNumber", u.getCarNumber());
		try {
			if (query2.find().size() != 1) {
				System.out.format("Something went worng while searching for %d", u.getCarNumber());
				return null;
			}

		} catch (final ParseException ¢) {
			¢.printStackTrace();
			return null;
		}
		StickersColor $ = null;
		for (final User currentUser : users)
			if (currentUser.getCarNumber().equals(u.getCarNumber()))
				$ = currentUser.getSticker();
		return $;
	}

	// Return parking slot id by a given user
	public ParkingSlot getParkingslotByUser(final User u) {
		// search the given user
		final ParseQuery<ParseObject> query2 = ParseQuery.getQuery("PMUser");
		query2.whereEqualTo("carNumber", u.getCarNumber());
		try {
			if (query2.find().size() != 1) {
				System.out.format("Something went worng while searching for %d", u.getCarNumber());
				return null;
			}

		} catch (final ParseException ¢) {
			¢.printStackTrace();
			return null;
		}
		ParkingSlot $ = null;
		for (final User currentUser : users)
			if (currentUser.getCarNumber().equals(u.getCarNumber()))
				$ = currentUser.getCurrentParking();
		return $;
	}

	// Return parking slot id by a given user
	public User getUserByParkingslot(final ParkingSlot parkinSlot) {
		final ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingSlot");
		query.whereEqualTo("name", parkinSlot.getName());
		try {
			if (query.find().size() != 1) {
				System.out.format("Something went worng while searching for %d", parkinSlot.getName());
				return null;
			}

		} catch (final ParseException ¢) {
			¢.printStackTrace();
			return null;
		}
		final User $ = null;
		// search current user in parkingSlot
		for (final User ¢ : getUsers())
			if (¢.getCurrentParking() != null && ¢.getCurrentParking().getName().equals(parkinSlot.getName()))
				return ¢;

		return $;
	}

	// Return num of taken parking slots by a given area
	public int getNumOfTakenByArea(final ParkingArea ¢) {
		return parkingAreas.getNumOfTakenByArea(¢);
	}

	// Return num of free parking slots by given area
	public int getNumOfFreeByArea(final ParkingArea ¢) {
		return parkingAreas.getNumOfFreeByArea(¢);
	}

	// Return num of free parking slots
	public int getNumOfFreeSlots() {
		return parkingAreas.getNumOfFreeSlots();
	}

	// Return num of taken parking slots
	public int getNumOfTakenSlots() {
		return parkingAreas.getNumOfTakenSlots();
	}

	// Return parking slots per area
	public int getNumOfSlotsByArea(final ParkingArea ¢) {
		return parkingAreas.getNumOfSlotsByArea(¢);
	}

	// Return a free parking slot by a given area
	public ParkingSlot getParkingslotByArea(final ParkingArea ¢) throws ParseException {
		return parkingAreas.getParkingslotByArea(¢);
	}

}
