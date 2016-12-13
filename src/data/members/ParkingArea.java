package data.members;

import java.util.Set;

/**
 * @author Inbal Matityahu
 * @since 12.11.16
 * 
 *        This class represent a parking area inside the Technion
 */

public class ParkingArea {

	private StickerType color;
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

	public void setParkingSlot(Set<ParkingSlot> ¢) {
		this.parkingSlots = ¢;
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
	public StickerType getColor() {
		return color;
	}

	public void setColor(StickerType color) {
		this.color = color;
	}

	public Set<ParkingSlot> getParkingSlots() {
		return parkingSlots;
	}

	public void setParkingSlots(Set<ParkingSlot> parkingSlots) {
		this.parkingSlots = parkingSlots;
	}

	/*
	 * add new parking slot to this area. notice - this new slot will count as
	 * free slot, and therefore increase the amount of free slots in this area,
	 * and the total count of parking
	 */
	public void addParkingSlot(ParkingSlot ¢) {
		this.getFreeSlots().add(¢);
		this.getParkingSlot().add(¢);
		this.setNumOfFreeSlots(this.getNumOfFreeSlots() + 1);
		this.setNumOfParkingSlots(this.getNumOfParkingSlots() + 1);
	}

	/*
	 * change a specific parking slot status from taken to free
	 */
	public void changeTakenToFree(ParkingSlot ¢) {
		if (!this.getTakenSlots().contains(¢))
			return;

		this.getTakenSlots().remove(¢);
		this.setNumOfTakenSlots(this.getNumOfTakenSlots() - 1);
<<<<<<< HEAD
		slot.setStatus(ParkingSlotStatus.FREE);
//		slot.setCurrentUser(null);
		this.getFreeSlots().add(slot);
=======
		¢.setStatus(ParkingSlotStatus.FREE);
		¢.setCurrentUser(null);
		this.getFreeSlots().add(¢);
>>>>>>> 77e2db89f4a16dbc2d3d8a532e201116ed8716bd
		this.setNumOfFreeSlots(this.getNumOfFreeSlots() + 1);
	}

	/*
	 * change a specific parking slot status from free to taken
	 */
	public void changeFreeToTaken(ParkingSlot s, User user) {
		if (!this.getFreeSlots().contains(s)) {
			return;
		}
		this.getFreeSlots().remove(s);
		this.setNumOfFreeSlots(this.getNumOfFreeSlots() - 1);
<<<<<<< HEAD
		slot.setStatus(ParkingSlotStatus.TAKEN);
		user.setCurrentParking(slot);
//		slot.setCurrentUser(user);
		this.getTakenSlots().add(slot);
=======
		s.setStatus(ParkingSlotStatus.TAKEN);
		user.setCurrentParking(s);
		s.setCurrentUser(user);
		this.getTakenSlots().add(s);
>>>>>>> 77e2db89f4a16dbc2d3d8a532e201116ed8716bd
		this.setNumOfTakenSlots(this.getNumOfTakenSlots() + 1);
	}
}
