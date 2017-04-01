package manager.logic;

import org.parse4j.ParseException;

import data.members.MapLocation;
import data.members.ParkingArea;
import data.members.ParkingSlot;
import data.members.StickersColor;
import data.members.ParkingSlotStatus;
import data.members.User;

/**
 * @author Inbal Matityahu
 * @since 12.13.16
 * 
 *        The management's system can send or receive data by queries to the
 *        databases. This class holds the possible queries.
 */

public class Queries {
	private Management managment;

	public Queries() {
		managment = new Management();
	}

	public Management getManagment() {
		return managment;
	}

	public void setManagment(final Management managment) {
		this.managment = managment;
	}

	// Return sticker type of a given user
	public StickersColor getColorByUser(final User ¢) {
		return managment.getColorByUser(¢);
	}

	// Return parking slot id by a given user
	public ParkingSlot getParkingslotByUser(final User ¢) {
		return managment.getParkingslotByUser(¢);
	}

	// Return parking slot id by a given user
	public User getUserByParkingslot(final ParkingSlot parkinSlot) {
		return managment.getUserByParkingslot(parkinSlot);
	}

	// Return num of taken parking slots by a given area
	public int getNumOfTakenByArea(final ParkingArea ¢) {
		return managment.getNumOfTakenByArea(¢);
	}

	// Return num of free parking slots by given area
	public int getNumOfFreeByArea(final ParkingArea ¢) {
		return managment.getNumOfFreeByArea(¢);
	}

	// Return num of free parking slots
	public int getNumOfFreeSlots() {
		return managment.getNumOfFreeSlots();
	}

	// Return num of taken parking slots
	public int getNumOfTakenSlots() {
		return managment.getNumOfTakenSlots();
	}

	// Return parking slots per area
	public int getNumOfSlotsByArea(final ParkingArea ¢) {
		return managment.getNumOfSlotsByArea(¢);
	}

	// Return a free parking slot by a given area
	public ParkingSlot getParkingslotByArea(final ParkingArea ¢) throws ParseException {
		return managment.getParkingslotByArea(¢);
	}

	// Return user according to user carNum
	public User returnUser(final String carNum) {
		for (final User $ : managment.getUsers())
			if ($.getCarNumber().equals(carNum))
				return $;
		return null;
	}

	// Return user userName according to user carNum
	public String returnUserName(final String carNum) {
		final User $ = returnUser(carNum);
		return $ == null ? null : $.getName();
	}

	// Return user password according to user carNum
	public String returnUserPassword(final String carNum) {
		final User $ = returnUser(carNum);
		return $ == null ? null : $.getPassword();
	}

	// Return user phoneNum according to user carNum
	public String returnUserPhoneNum(final String carNum) {
		final User $ = returnUser(carNum);
		return $ == null ? null : $.getPhoneNumber();
	}

	// Return user sticker according to user carNum
	public StickersColor returnUserSticker(final String carNum) {
		final User $ = returnUser(carNum);
		return $ == null ? null : $.getSticker();
	}

	// Return user current parking according to user carNum
	public ParkingSlot returnUserCurrentParking(final String carNum) {
		final User $ = returnUser(carNum);
		return $ == null ? null : $.getCurrentParking();
	}

	// Return parking slot according to given location
	public ParkingSlot returnParkingSlot(final MapLocation ¢) {
		for (final ParkingSlot $ : managment.getParkingSlots())
			if ($.getLocation().getLat() == ¢.getLat() && $.getLocation().getLon() == ¢.getLon())
				return $;
		return null;
	}

	// Return parking slot's status according to given location
	public ParkingSlotStatus returnParkingSlotStatus(final MapLocation ¢) {
		final ParkingSlot $ = returnParkingSlot(¢);
		return $ == null ? null : $.getStatus();
	}

	// Return parking slot's color according to given location
	public StickersColor returnParkingSlotColor(final MapLocation ¢) {
		final ParkingSlot $ = returnParkingSlot(¢);
		return $ == null ? null : $.getColor();
	}

	// Return parking slot's current user according to given location
	public User returnParkingSlotCurrentUser(final MapLocation l) {
		final ParkingSlot currentSlot = returnParkingSlot(l);
		for (final User $ : managment.getUsers())
			if ($.getCurrentParking() != null && $.getCurrentParking().getName().equals(currentSlot.getName()))
				return $;
		return null;
	}

	// Return parkingArea according to areaId
	public ParkingArea returnArea(final int areaID) {
		for (final ParkingArea $ : managment.getParkingAreas().getParkingAreas())
			if ($.getAreaId() == areaID)
				return $;
		throw new RuntimeException("There is no area with " + areaID + " on DB");
	}
}
