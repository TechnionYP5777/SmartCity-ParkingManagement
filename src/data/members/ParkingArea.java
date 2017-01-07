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
 * @since 12.11.16This class represent a parking area inside the Technion
 */

public class ParkingArea extends dbMember {

	private StickersColor color;
	private int areaId;
	private Set<ParkingSlot> parkingSlots;

	/* Constructors */

	// Retrieve an exiting area from DB by the objectId
	public ParkingArea(String objectId) throws ParseException {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("objectId", objectId);
		ParseObject parseObject = query.find().get(0);

		this.parseObject = parseObject;
		this.areaId = (Integer) parseObject.get("areaId");
		this.color = StickersColor.values()[(Integer) parseObject.get("color")];
		this.parkingSlots = convertToSlots(getAllSlots());
		this.setObjectId();

	}

	// Create a new parking area
	public ParkingArea(int areaId, Set<ParkingSlot> parkingSlots, StickersColor defaultColor) throws ParseException {
		DBManager.initialize();
		this.parseObject = new ParseObject("ParkingArea");
		this.setAreaId(areaId);
		this.setColor(defaultColor);
		this.setParkingSlots(parkingSlots);

		this.parseObject.save();
		this.objectId = this.parseObject.getObjectId();
	}

	public ParkingArea(ParseObject parseObject) throws ParseException {
		this.parseObject = parseObject;
		this.areaId = (Integer) parseObject.get("areaId");
		this.color = StickersColor.values()[(Integer) parseObject.get("color")];
		this.parkingSlots = convertToSlots(getAllSlots());
		this.setObjectId();
	}

	/* Getters */

	public int getAreaId() {
		return this.areaId;
	}

	public int getNumOfParkingSlots() {
		return this.getAllSlots().size();
	}

	public Set<ParkingSlot> getParkingSlots() {
		return this.parkingSlots;
	}

	public int getNumOfFreeSlots() {
		return this.getSlotsByStatus(ParkingSlotStatus.FREE).size();
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

	private void setParkingSlots(Set<ParkingSlot> ss) throws ParseException {
		this.parkingSlots = ss;
		updateSlotsArray();
	}

	private void setColor(StickersColor s) {
		this.color = s;
		this.parseObject.put("color", s.ordinal());
	}

	/* Methods */

	public Set<ParkingSlot> convertToSlots(List<ParseObject> slots) {
		List<ParkingSlot> freeSlots = new ArrayList<ParkingSlot>();
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
		for (ParkingSlot p : this.parkingSlots)
			if (p.objectId.equals(s.objectId))
				this.parkingSlots.remove(p);
		s.removeParkingSlotFromDB();
		updateSlotsArray();
	}

	// Update the slots array in the DB according to the last update
	private void updateSlotsArray() throws ParseException {
		List<ParseObject> slots = new ArrayList<ParseObject>();
		if (!this.parkingSlots.isEmpty())
			for (ParkingSlot p : this.parkingSlots)
				slots.add(DBManager.getParseObject(p));

		this.parseObject.put("parkingSlots", slots);
		this.parseObject.save();
	}

}
