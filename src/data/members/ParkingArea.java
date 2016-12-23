package data.members;

import java.util.HashSet;

import java.util.Set;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;

import data.management.DBManager;

/** 
 * @author Inbal Matityahu
 * @since 12.11.16This class represent a parking area inside the Technion
 * [[SuppressWarningsSpartan]]
 */

public class ParkingArea {

	private StickersColor color;
	private int areaId;
	private Set<ParkingSlot> parkingSlots;

	private ParseObject area;
	
	public ParkingArea(){
		
	}

	public ParkingArea(StickersColor color, int areaId, int free, int taken, int total, Set<ParkingSlot> parkingSlots,
			Set<ParkingSlot> freeSlots, Set<ParkingSlot> takenSlots) throws ParseException {
		DBManager.initialize();
		this.area = new ParseObject("ParkingArea");
		this.setAreaId(areaId);
		this.setColor(color);
		this.setParkingSlots(parkingSlots);
		area.save();
	}

	public int getAreaId() {
		return this.areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
		this.area.put("areaId", areaId);
	}

	public int getNumOfParkingSlots() {
		return this.parkingSlots.size();
	}

	public Set<ParkingSlot> getParkingSlots() {
		return parkingSlots;
	}

	public void setParkingSlots(Set<ParkingSlot> slots) {
		this.parkingSlots = slots;
				
		//this.area.put("parkingSlots", slots);
	}

	public int getNumOfFreeSlots(){
		// TODO: add a query from DB that returns only the free slots
		
		return 0;
	}

	public int getNumOfTakenSlots() {
		// TODO: add a query from DB that returns only the taken slots
		return 0;
	}

	public Set<ParkingSlot> getFreeSlots() {
		// TODO: add a query from DB that returns only the free slots
		return new HashSet<ParkingSlot>();
	}

	public Set<ParkingSlot> getTakenSlots() {
		// TODO: add a query from DB that returns only the taken slots
		return new HashSet<ParkingSlot>();
	}

	public StickersColor getColor() {
		return color;
	}

	public void setColor(StickersColor color) {
		this.color = color;
		this.area.put("color", color.ordinal());
	}

	/*
	 * add new parking slot to this area. notice - this new slot will count as
	 * free slot, and therefore increase the amount of free slots in this area,
	 * and the total count of parking
	 */
	public void addParkingSlot(ParkingSlot parkingSlot) {
		this.parkingSlots.add(parkingSlot);

		// TODO: add to the array of slots
	}
	
	/*
	 * add new parking slot to this area. notice - this new slot will count as
	 * free slot, and therefore increase the amount of free slots in this area,
	 * and the total count of parking
	 */
	public void removeParkingSlot(ParkingSlot parkingSlot) {
		this.parkingSlots.remove(parkingSlot);

		// TODO: remove from the array of slots
	}
}
