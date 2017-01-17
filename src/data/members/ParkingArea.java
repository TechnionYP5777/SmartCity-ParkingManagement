package data.members;

import java.util.ArrayList;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.parse4j.ParseException;
import org.parse4j.ParseGeoPoint;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

import data.management.DBManager;

/**
 * @author Inbal Matityahu
 * @author David Cohen
 * @author Toma
 * @since 12.11.16 This class represent a parking area inside the Technion
 */

public class ParkingArea extends dbMember {

	private StickersColor color;
	private int areaId;
	private String name;
	private MapLocation location;
	private Set<ParkingSlot> parkingSlots;

	/* Constructors */

	// Retrieve an exiting area from DB by the name
	public ParkingArea(String name) throws ParseException {
		DBManager.initialize();
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("name", name);
		ParseObject parseObject = query.find().get(0);

		this.parseObject = parseObject;
		this.areaId = (Integer) parseObject.get("areaId");
		this.name = (String) parseObject.get("name");
		this.color = StickersColor.values()[(Integer) parseObject.get("color")];
		ParseGeoPoint geo = this.parseObject.getParseGeoPoint("location");
		this.location = new MapLocation(geo.getLatitude(), geo.getLongitude());
		this.parkingSlots = convertToSlots(getAllSlots());
		this.setObjectId();

	}

	// Create a new parking area
	public ParkingArea(int areaId, String name, MapLocation location, Set<ParkingSlot> parkingSlots, StickersColor defaultColor) throws ParseException {
		DBManager.initialize();
		this.parseObject = new ParseObject("ParkingArea");
		this.setAreaId(areaId);
		this.setName(name);
		this.setColor(defaultColor);
		this.setLocation(location);
		this.setParkingSlots(parkingSlots);

		this.parseObject.save();
		this.objectId = this.parseObject.getObjectId();
	}

	public ParkingArea(ParseObject parseObject) throws ParseException {
		this.parseObject = parseObject;
		this.areaId = (Integer) parseObject.get("areaId");
		this.name = (String) parseObject.get("name");
		this.color = StickersColor.values()[(Integer) parseObject.get("color")];
		ParseGeoPoint geo = this.parseObject.getParseGeoPoint("location");
		this.location = new MapLocation(geo.getLatitude(), geo.getLongitude());
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
	
	public MapLocation getLocation() {
		return location;
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
	
	public void setLocation(MapLocation l) {
		this.location = l;
		this.parseObject.put("location", (new ParseGeoPoint(l.getLat(), l.getLon())));
	}
	
	private void setParkingSlots(Set<ParkingSlot> p) throws ParseException {
		this.parkingSlots = p;
		updateSlotsArray();
	}

	private void setColor(StickersColor c) {
		this.color = c;
		this.parseObject.put("color", c.ordinal());
	}

	/* Methods */
	public void removeParkingAreaFromDB() throws ParseException {
		this.deleteParseObject();
	}
	
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
	public void addParkingSlot(ParkingSlot p) throws ParseException {
		this.parkingSlots.add(p);

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
		for (Iterator<ParkingSlot> iterator = this.parkingSlots.iterator(); iterator.hasNext();) {
			ParkingSlot slot = iterator.next();
			if (slot.objectId.equalsIgnoreCase(s.objectId))
				iterator.remove();
		}
		s.removeParkingSlotFromDB();
		updateSlotsArray();
	}

	// Update the slots array in the DB according to the last update
	private void updateSlotsArray() throws ParseException {
		List<ParseObject> slots = new ArrayList<ParseObject>();
		if (!this.parkingSlots.isEmpty())
			for (ParkingSlot p : this.parkingSlots)
				slots.add(p.getParseObject()); 

		this.parseObject.put("parkingSlots", slots);
		this.parseObject.save();
	}
}
