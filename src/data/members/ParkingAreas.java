package data.members;

import java.util.List;
import java.util.Set;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

import data.management.DBManager;

import java.util.HashSet;

/**
 * @author Inbal Matityahu
 * @since 12.12.16
 * 
 *        This class represent all parking areas on Technion's site
 */

public class ParkingAreas {
	private Set<ParkingArea> parkingAreas;
	private ParseObject areas;

	public ParkingAreas(Set<ParkingArea> parkingAreas) throws ParseException {
		DBManager.initialize();
		this.areas = new ParseObject("ParkingAreas");
		this.parkingAreas = new HashSet<ParkingArea>();
		this.parkingAreas = parkingAreas;
		areas.save();
	}

	public Set<ParkingArea> getParkingAreas() {
		return parkingAreas;
	}

	public void setParkingAreas(Set<ParkingArea> parkingAreas) {
		this.parkingAreas = parkingAreas;
		this.areas.put("parkingAreas", parkingAreas);
	}

	public ParkingAreas() {
		this.parkingAreas = new HashSet<ParkingArea>();
	}

	public void addParkingArea(ParkingArea newParkingArea) {
		this.parkingAreas.add(newParkingArea);
		this.areas.put("parkingAreas", this.getParkingAreas());
	}

	public void removeParkingArea(ParkingArea parkingArea) {

		// search if parkingArea exist
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("areaId", parkingArea.getAreaId());
		try {
			List<ParseObject> areaList = query.find();
			if (areaList.size() != 1) {
				System.out.format("Something went worng while searching for %d", parkingArea.getAreaId());
				return;
			}

		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}

		this.parkingAreas.remove(parkingArea);
		this.areas.put("parkingAreas", this.getParkingAreas());
	}

	// Return num of taken parking slots by a given area
	public int getNumOfTakenByArea(ParkingArea a) {
		if (!this.parkingAreas.contains(a))
			return 0;

		int $ = 0;
		for (ParkingArea currentArea : this.parkingAreas)
			if (currentArea.equals(a))
				$ = currentArea.getNumOfTakenSlots();
		return $;
	}

	// Return num of free parking slots by given area
	public int getNumOfFreeByArea(ParkingArea a) {
		if (!this.parkingAreas.contains(a))
			return 0;
		int $ = 0;
		for (ParkingArea currentArea : this.parkingAreas)
			if (currentArea.equals(a))
				$ = currentArea.getNumOfFreeSlots();
		return $;
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
	public ParkingSlot getParkingslotByArea(ParkingArea area) {
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
