package data.members;

import java.util.Set;

/**
 * @author Inbal Matityahu
 * @since 12.11.16
 * 
 *        This class represent a parking area inside the Technion
 */

public class ParkingArea {

	private int areaId;
	private int numOfParkingSlots;
	private int numOfFreeSlots;
	private int numOfTakenSlots;
	private Set<ParkingSlot> parkingSlots;
	private Set<ParkingSlot> freeSlots;
	private Set<ParkingSlot> takenSlots;

	public ParkingArea(){
		// will populate private fields according to the data stored in the DB
	}
	
	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}

	public int getNumOfParkingSlots() {
		return numOfParkingSlots;
	}

	public void setNumOfParkingSlots(int numOfParkingSlots) {
		this.numOfParkingSlots = numOfParkingSlots;
	}

	public Set<ParkingSlot> getParkingSlot() {
		return parkingSlots;
	}

	public void setParkingSlot(Set<ParkingSlot> parkingSlots) {
		this.parkingSlots = parkingSlots;
	}

	public int getNumOfFreeSlots() {
		return numOfFreeSlots;
	}

	public void setNumOfFreeSlots(int numOfFreeSlots) {
		this.numOfFreeSlots = numOfFreeSlots;
	}

	public int getNumOfTakenSlots() {
		return numOfTakenSlots;
	}

	public void setNumOfTakenSlots(int numOfTakenSlots) {
		this.numOfTakenSlots = numOfTakenSlots;
	}

	public Set<ParkingSlot> getFreeSlots() {
		return freeSlots;
	}

	public void setFreeSlots(Set<ParkingSlot> freeSlots) {
		this.freeSlots = freeSlots;
	}

	public Set<ParkingSlot> getTakenSlots() {
		return takenSlots;
	}

	public void setTakenSlots(Set<ParkingSlot> takenSlots) {
		this.takenSlots = takenSlots;
	}

	/*
	 * add new parking slot to this area. notice - this new slot will count as
	 * free slot, and therefore increase the amount of free slots in this area,
	 * and the total count of parking
	 */
	public void addParkingSlot(ParkingSlot slot) {
		this.getFreeSlots().add(slot);
		this.getParkingSlot().add(slot);
		this.setNumOfFreeSlots(this.getNumOfFreeSlots() + 1);
		this.setNumOfParkingSlots(this.getNumOfParkingSlots() + 1);
	}

	/*
	 * change a specific parking slot status from taken to free
	 */
	public void changeTakenToFree(ParkingSlot slot) {
		if (!this.getTakenSlots().contains(slot)) {
			return;
		}

		this.getTakenSlots().remove(slot);
		this.setNumOfTakenSlots(this.getNumOfTakenSlots() - 1);
		slot.status = ParkingSlotStatus.FREE;
		slot.currentUser = null;
		this.getFreeSlots().add(slot);
		this.setNumOfFreeSlots(this.getNumOfFreeSlots() + 1);
	}

	/*
	 * change a specific parking slot status from free to taken
	 */
	public void changeFreeToTaken(ParkingSlot slot, User user) {
		if (!this.getFreeSlots().contains(slot)) {
			return;
		}
		this.getFreeSlots().remove(slot);
		this.setNumOfFreeSlots(this.getNumOfFreeSlots() - 1);
		slot.status = ParkingSlotStatus.TAKEN;
		user.setCurrentLocation(slot);
		slot.currentUser = user;
		this.getTakenSlots().add(slot);
		this.setNumOfTakenSlots(this.getNumOfTakenSlots() + 1);
	}
}
