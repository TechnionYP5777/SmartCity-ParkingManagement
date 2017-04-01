package manager.logic;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import data.members.ParkingArea;
import data.members.ParkingAreas;
import data.members.ParkingSlot;
import data.members.StickersColor;

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

	// all parking slots on Technion area
	private Set<ParkingSlot> parkingSlots;
	// all parking areas
	private ParkingAreas parkingAreas;

	public Management() {
		parkingSlots = new HashSet<ParkingSlot>();
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

	public Management(final Set<ParkingSlot> parkingSlots, final ParkingAreas parkingAreas) throws ParseException {
		this.parkingAreas = parkingAreas;
		this.parkingSlots = parkingSlots;
	}

	public ParkingAreas getParkingAreas() {
		return parkingAreas;
	}

	public void setParkingAreas(final ParkingAreas ¢) {
		parkingAreas = ¢;
	}

	public Set<ParkingSlot> getParkingSlots() {
		return parkingSlots;
	}

	public void setParkingSlots(final Set<ParkingSlot> ¢) {
		parkingSlots = ¢;

	}

	public void addParkingSlot(final ParkingSlot newParkingSlot) {
		parkingSlots.add(newParkingSlot);

	}

	public void removeParkingSlot(final ParkingSlot slotToRemove) {
		parkingSlots.remove(slotToRemove);

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
