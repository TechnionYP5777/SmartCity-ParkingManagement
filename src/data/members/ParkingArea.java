package data.members;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;

import data.management.DBManager;

/** 
 * @author Inbal Matityahu
 * @since 12.11.16This class represent a parking area inside the Technion
 */

public class ParkingArea extends dbMember {

	private StickersColor color;
	private int areaId;
	private Set<ParkingSlot> parkingSlots;
	
	// Retrieve an exiting are from DB by the areaId
	public ParkingArea(int areaId){
		// TODO : How to create a query based on this ?
	}

	// Create a new parking area
	public ParkingArea(int areaId, Set<ParkingSlot> parkingSlots, StickersColor defaultColor ) throws ParseException {
		DBManager.initialize();
		this.parseObject = new ParseObject("ParkingArea");
		this.setAreaId(areaId);
		this.setColor(defaultColor);
		this.setParkingSlots(parkingSlots);
		
		this.parseObject.save();
		this.objectId = this.parseObject.getObjectId();
	}

	public int getAreaId() {
		return this.areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
		this.parseObject.put("areaId", areaId);
	}

	public int getNumOfParkingSlots() {
		return this.parkingSlots.size();
	}

	public Set<ParkingSlot> getParkingSlots() {
		return parkingSlots;
	}

	public void setParkingSlots(Set<ParkingSlot> ss) throws ParseException {
		this.parkingSlots = ss;
		updateSlotsArray();
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

	public void setColor(StickersColor c) {
		this.color = c;
		this.parseObject.put("color", c.ordinal());
	}

	/*
	 * add new parking slot to this area. notice - this new slot will count as
	 * free slot, and therefore increase the amount of free slots in this area,
	 * and the total count of parking
	 */
	public void addParkingSlot(ParkingSlot s) throws ParseException {
		this.parkingSlots.add(s);
		
		updateSlotsArray();
	}
	
	/*
	 * add new parking slot to this area. notice - this new slot will count as
	 * free slot, and therefore increase the amount of free slots in this area,
	 * and the total count of parking
	 */
	public void removeParkingSlot(ParkingSlot s) throws ParseException {
		this.parkingSlots.remove(s);
		s.removeParkingSlot();
		updateSlotsArray();
	}

	// Update the slots array in the DB according to the last update
	private void updateSlotsArray() throws ParseException {
		List<ParseObject> slots = new ArrayList<ParseObject>();
		for (ParkingSlot parkingSlot : this.parkingSlots)
			slots.add(DBManager.getParseObject(parkingSlot));
		this.parseObject.put("parkingSlots", slots);
		this.parseObject.save();
	}
}
