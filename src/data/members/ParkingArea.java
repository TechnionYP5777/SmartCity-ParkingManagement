package data.members;

import java.util.ArrayList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.parse4j.ParseException;

import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

import data.management.DBManager;

/**
 * @author Inbal Matityahu
 * @author David Cohen
 * @author Tom Nof
 * @since 12.11.16 This class represent a parking area inside the Technion
 */

public class ParkingArea extends dbMember {

	private StickersColor color;
	private int areaId;
	private String name;
	private Set<ParkingSlot> parkingSlots;

	/* Constructors */

	// Retrieve an exiting area from DB by the objectId
	public ParkingArea(String objectId) throws ParseException {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("objectId", objectId);
		ParseObject parseObject = query.find().get(0);

		this.parseObject = parseObject;
		this.areaId = (Integer) parseObject.get("areaId");
		this.name = (String) parseObject.get("name");
		this.color = StickersColor.values()[(Integer) parseObject.get("color")];
		this.parkingSlots = convertToSlots(getAllSlots());
		this.setObjectId();

	}

	// Create a new parking area
	public ParkingArea(int areaId, String name, Set<ParkingSlot> parkingSlots, StickersColor defaultColor) throws ParseException {
		DBManager.initialize();
		this.parseObject = new ParseObject("ParkingArea");
		this.setAreaId(areaId);
		this.setName(name);
		this.setColor(defaultColor);
		this.setParkingSlots(parkingSlots);

		this.parseObject.save();
		this.objectId = this.parseObject.getObjectId();
	}

	public ParkingArea(ParseObject parseObject) throws ParseException {
		this.parseObject = parseObject;
		this.areaId = (Integer) parseObject.get("areaId");
		this.name = (String) parseObject.get("name");
		this.color = StickersColor.values()[(Integer) parseObject.get("color")];
		this.parkingSlots = convertToSlots(getAllSlots());
		this.setObjectId();
	}

	/* Getters */

	public int getAreaId() {
		return this.areaId;
	}

	public String getName() {
		return this.name;
	}
	
	public int getNumOfParkingSlots() {
		List<ParseObject> parkingSlots = this.getAllSlots();
		return parkingSlots == null ? 0 : parkingSlots.size();
	}

	public Set<ParkingSlot> getParkingSlots() {
		return this.parkingSlots;
	}

	public int getNumOfFreeSlots() {
		Set<ParkingSlot> spots = getSlotsByStatus(ParkingSlotStatus.FREE);
		return spots == null ? 0 : spots.size();
	}

	public List<ParseObject> getAllSlots() {
		try {
			List<ParseObject> slots = parseObject.getList("parkingSlots");
			List<Object> ids = slots.stream().map(p -> p.getObjectId()).collect(Collectors.toList());
			ParseQuery<ParseObject> slotQuery = ParseQuery.getQuery("ParkingSlot");
			slotQuery.whereContainedIn("objectId", ids);
			return slotQuery.find();
		} catch (ParseException e) {
			throw new RuntimeException("Problem getting all slots!", e);
		}
	}

	public Set<ParkingSlot> getSlotsByStatus(ParkingSlotStatus s) {
		return parkingSlots.stream().filter((slot) -> slot.getStatus().equals(s)).collect(Collectors.toSet());
	}

	public int getNumOfTakenSlots() {
		Set<ParkingSlot> spots = getSlotsByStatus(ParkingSlotStatus.TAKEN);
		return spots == null ? 0 : spots.size();
	}

	public Set<ParkingSlot> getFreeSlots() throws ParseException {
		return getSlotsByStatus(ParkingSlotStatus.FREE);
	}

	public Set<ParkingSlot> getTakenSlots() throws ParseException {
		return getSlotsByStatus(ParkingSlotStatus.TAKEN);
	}

	public StickersColor getColor() {
		return color;
	}

	/* Setters */

	private void setAreaId(int areaId) {
		this.areaId = areaId;
		this.parseObject.put("areaId", areaId);
	}

	private void setName(String name) {
		this.name = name;
		this.parseObject.put("name", name);
	}
	
	private void setParkingSlots(Set<ParkingSlot> ¢) throws ParseException {
		this.parkingSlots = ¢;
		updateSlotsArray();
	}

	private void setColor(StickersColor ¢) {
		this.color = ¢;
		this.parseObject.put("color", ¢.ordinal());
	}

	/* Methods */

	public Set<ParkingSlot> convertToSlots(List<ParseObject> slots) {
		List<ParkingSlot> freeSlots = new ArrayList<ParkingSlot>();
		if (slots == null)
			return null;
		for (ParseObject p : slots)
			try {
				freeSlots.add(new ParkingSlot(p));
			} catch (ParseException e) {
				System.out.println("could not add the slot");
				e.printStackTrace();
			}
		return new HashSet<ParkingSlot>(freeSlots);
	}

	/*
	 * add new parking slot to this area. notice - this new slot will count as
	 * free slot, and therefore increase the amount of free slots in this area,
	 * and the total count of parking
	 */
	public void addParkingSlot(ParkingSlot ¢) throws ParseException {
		this.parkingSlots.add(¢);

		updateSlotsArray();
	}

	/*
	 * add new parking slot to this area. notice - this new slot will count as
	 * free slot, and therefore increase the amount of free slots in this area,
	 * and the total count of parking
	 */
	public void removeParkingSlot(ParkingSlot s) throws ParseException {
		if (this.parkingSlots == null)
			return;
		for (ParkingSlot ¢ : this.parkingSlots)
			if (¢.objectId.equals(s.objectId))
				this.parkingSlots.remove(¢);
		s.removeParkingSlotFromDB();
		updateSlotsArray();
	}

	// Update the slots array in the DB according to the last update
	private void updateSlotsArray() throws ParseException {
		List<ParseObject> slots = new ArrayList<ParseObject>();
		if (!this.parkingSlots.isEmpty())
			for (ParkingSlot ¢ : this.parkingSlots)
				slots.add(¢.getParseObject()); // slots.add(DBManager.getParseObject(p));

		this.parseObject.put("parkingSlots", slots);
		this.parseObject.save();
	}
	
//	public String getName() throws ParseException{
//		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
//		query.whereEqualTo("objectId", objectId);
//		ParseObject parseObject = query.find().get(0);
//		String $ = parseObject.getString("areaName");
//		return $;
//	}

}
