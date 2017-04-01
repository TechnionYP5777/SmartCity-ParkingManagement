package manager.logic;

import org.parse4j.ParseException;

import data.members.MapLocation;
import data.members.ParkingArea;
import data.members.ParkingSlot;
import data.members.StickersColor;
import data.members.ParkingSlotStatus;

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

	// Return parkingArea according to areaId
	public ParkingArea returnArea(final int areaID) {
		for (final ParkingArea $ : managment.getParkingAreas().getParkingAreas())
			if ($.getAreaId() == areaID)
				return $;
		throw new RuntimeException("There is no area with " + areaID + " on DB");
	}
}
