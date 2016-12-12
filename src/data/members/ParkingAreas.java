package data.members;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Inbal Matityahu
 * @since 12.12.16
 * 
 *        This class represent all parking areas on Technion's site
 */

public class ParkingAreas {
	private Set<ParkingArea> parkingAreas;

	public Set<ParkingArea> getParkingAreas() {
		return parkingAreas;
	}

	public void setParkingAreas(Set<ParkingArea> parkingAreas) {
		this.parkingAreas = parkingAreas;
	}

	public ParkingAreas() {
		this.parkingAreas = new HashSet<ParkingArea>();
	}

	public void addParkingArea(ParkingArea newParkingArea) {
		this.parkingAreas.add(newParkingArea);
	}

	public void removeParkingArea(ParkingArea parkingArea) {
		this.parkingAreas.remove(parkingArea);
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
