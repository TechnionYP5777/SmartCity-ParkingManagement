package manager.logic;

import java.util.Set;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

import data.members.ParkingArea;
import data.members.ParkingSlot;

import java.util.HashSet;

/**
 * @author Inbal Matityahu
 * @since 12.12.16
 * 
 *        This class represent all parking areas on Technion's site
 */

public class ParkingAreas {
	private Set<ParkingArea> parkingAreas;

	public ParkingAreas(Set<ParkingArea> parkingAreas){
		this.parkingAreas = new HashSet<ParkingArea>();
		this.parkingAreas = parkingAreas;
	}

	public Set<ParkingArea> getParkingAreas() {
		return parkingAreas;
	}

	public void setParkingAreas(Set<ParkingArea> ¢) {
		this.parkingAreas = ¢;
	}

	public ParkingAreas() {
		this.parkingAreas = new HashSet<ParkingArea>();
	}

	public void addParkingArea(ParkingArea newParkingArea) {
		this.parkingAreas.add(newParkingArea);
		
		//TODO: add newParking to DB
	}

	public void removeParkingArea(ParkingArea a) {

		// search if parkingArea exist
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("areaId", a.getAreaId());
		try {
			if (query.find().size() != 1) {
				System.out.format("Something went worng while searching for %d", a.getAreaId());
				return;
			//TODO: remove a from DB
			}

		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}

		this.parkingAreas.remove(a);
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
		////TODO: rewrite this function
		return $;
	}

	// Return num of free parking slots
	public int getNumOfFreeSlots() {
		int $ = 0;
		for (ParkingArea currentArea : this.parkingAreas) {
			//TODO: rewrite this function
			$ += currentArea.getNumOfFreeSlots();
		}
		return $;
	}

	// Return num of taken parking slots
	public int getNumOfTakenSlots() {
		int $ = 0;
		for (ParkingArea currentArea : this.parkingAreas) {
			//TODO: rewrite this function
			$ += currentArea.getNumOfTakenSlots();
		}
		return $;
	}


	// Return parking slots per area
	public int getNumOfSlotsByArea(ParkingArea a) {
		if (!this.parkingAreas.contains(a))
			return 0;
		int $ = 0;
		for (ParkingArea currentArea : this.parkingAreas)
			if (currentArea.equals(a))
				$ = currentArea.getNumOfParkingSlots();
		return $;
	}

	
	// Return a free parking slot by a given area
	public ParkingSlot getParkingslotByArea(ParkingArea a) throws ParseException {
		ParkingSlot $ = null;
		if (!this.parkingAreas.contains(a))
			return $;
		for (ParkingArea currentArea : this.parkingAreas)
			if (currentArea.equals(a))
				for (ParkingSlot currentSlot : currentArea.getFreeSlots())
					$ = currentSlot;
		return $;
	}

}
