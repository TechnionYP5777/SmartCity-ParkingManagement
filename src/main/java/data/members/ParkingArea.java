package data.members;

import java.util.ArrayList;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.parse4j.ParseException;
import org.parse4j.ParseGeoPoint;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

import data.management.DBManager;

/**
 * @author Inbal Matityahu
 * @author David Cohen
 * @author Toma
 * @author dshames
 * @since 12.11.16 This class represent a parking area inside the Technion
 */

public class ParkingArea extends dbMember {

	private StickersColor color;
	private int areaId;
	private String name;
	private MapLocation location;
	private Set<ParkingSlot> parkingSlots;
	
	private final String objectClass = "ParkingArea";

	/* Constructors */

	// Retrieve an exiting area from DB by the name
	public ParkingArea(final String name) throws ParseException {
		DBManager.initialize();

		final ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("name", name);
		final ParseObject parseObject = query.find().get(0);

		this.parseObject = parseObject;
		areaId = (Integer) parseObject.get("areaId");
		this.name = (String) parseObject.get("name");
		color = StickersColor.values()[(Integer) parseObject.get("color")];
		final ParseGeoPoint geo = this.parseObject.getParseGeoPoint("location");
		location = new MapLocation(geo.getLatitude(), geo.getLongitude());
		parkingSlots = convertToSlots(getAllSlots());
		setObjectId();

	}

	// Create a new parking area
	public ParkingArea(final int areaId, final String name, final MapLocation location, final Set<ParkingSlot> parkingSlots,
			final StickersColor defaultColor) throws ParseException {
		DBManager.initialize();
		parseObject = new ParseObject("ParkingArea");
		setAreaId(areaId);
		setName(name);
		setColor(defaultColor);
		setLocation(location);
		setParkingSlots(parkingSlots);

		parseObject.save();
		objectId = parseObject.getObjectId();
	}

	public ParkingArea(final ParseObject parseObject) throws ParseException {
		this.parseObject = parseObject;
		areaId = (Integer) parseObject.get("areaId");
		name = (String) parseObject.get("name");
		color = StickersColor.values()[(Integer) parseObject.get("color")];
		final ParseGeoPoint geo = this.parseObject.getParseGeoPoint("location");
		location = new MapLocation(geo.getLatitude(), geo.getLongitude());
		parkingSlots = convertToSlots(getAllSlots());
		setObjectId();
	}

	/* Getters */

	public int getAreaId() {
		return this.areaId;
	}

	public String getName() {
		return this.name;
	}

	public int getNumOfParkingSlots() {
		final List<ParseObject> $ = getAllSlots();
		return $ == null ? 0 : $.size();
	}

	public MapLocation getLocation() {
		return this.location;
	}

	public Set<ParkingSlot> getParkingSlots() {
		return this.parkingSlots;
	}

	public int getNumOfFreeSlots() {
		final Set<ParkingSlot> $ = getSlotsByStatus(ParkingSlotStatus.FREE);
		return $ == null ? 0 : $.size();
	}

	public List<ParseObject> getAllSlots() {
		try {
			final List<ParseObject> slots = parseObject.getList("parkingSlots");
			final List<Object> ids = slots.stream().map(λ -> λ.getObjectId()).collect(Collectors.toList());
			final ParseQuery<ParseObject> $ = ParseQuery.getQuery("ParkingSlot");
			$.whereContainedIn("objectId", ids);
			return $.find();
		} catch (final ParseException ¢) {
			throw new RuntimeException("Problem getting all slots!", ¢);
		}
	}

	public Set<ParkingSlot> getSlotsByStatus(final ParkingSlotStatus s) {
		return parkingSlots.stream().filter(λ -> λ.getStatus().equals(s)).collect(Collectors.toSet());
	}

	public int getNumOfTakenSlots() {
		final Set<ParkingSlot> $ = getSlotsByStatus(ParkingSlotStatus.TAKEN);
		return $ == null ? 0 : $.size();
	}

	public Set<ParkingSlot> getFreeSlots() throws ParseException {
		return getSlotsByStatus(ParkingSlotStatus.FREE);
	}

	public Set<ParkingSlot> getTakenSlots() throws ParseException {
		return getSlotsByStatus(ParkingSlotStatus.TAKEN);
	}

	public StickersColor getColor() {
		return this.color;
	}

	/* Setters */

	private void setAreaId(final int areaId) {
		this.areaId = areaId;
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("color", this.color);
		fields.put("areaId", areaId);
		fields.put("name", this.name);
		fields.put("location", this.location);
		fields.put("parkingSlots", this.parkingSlots);
		DBManager.update(objectClass, fields);	
	}

	private void setName(final String name) {
		this.name = name;
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("color", this.color);
		fields.put("areaId", this.areaId);
		fields.put("name", name);
		fields.put("location", this.location);
		fields.put("parkingSlots", this.parkingSlots);
		DBManager.update(objectClass, fields);	
	}

	public void setLocation(final MapLocation l) {
		this.location = l;
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("color", this.color);
		fields.put("areaId", this.areaId);
		fields.put("name", this.name);
		fields.put("location", new ParseGeoPoint(l.getLat(), l.getLon()));
		fields.put("parkingSlots", this.parkingSlots);
		DBManager.update(objectClass, fields);	
	}

	private void setParkingSlots(final Set<ParkingSlot> slots) throws ParseException {
		this.parkingSlots = slots;
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("color", this.color);
		fields.put("areaId", this.areaId);
		fields.put("name", this.name);
		fields.put("location", this.location);
		fields.put("parkingSlots", parkingSlotsSetToParseList());
		DBManager.update(objectClass, fields);	
	}

	private void setColor(final StickersColor c) {
		this.color = c;
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("color", c.ordinal());
		fields.put("areaId", this.areaId);
		fields.put("name", this.name);
		fields.put("location", this.location);
		fields.put("parkingSlots", this.parkingSlots);
		DBManager.update(objectClass, fields);	
	}

	/* Methods */
	public void removeParkingAreaFromDB() throws ParseException {
		deleteParseObject();
	}

	public Set<ParkingSlot> convertToSlots(final List<ParseObject> slots) {
		final List<ParkingSlot> $ = new ArrayList<ParkingSlot>();
		if (slots == null)
			return null;
		for (final ParseObject p : slots)
			try {
				$.add(new ParkingSlot(p));
			} catch (final ParseException ¢) {
				System.out.println("could not add the slot");
				¢.printStackTrace();
			}
		return new HashSet<ParkingSlot>($);
	}

	/*
	 * add new parking slot to this area. notice - this new slot will count as
	 * free slot, and therefore increase the amount of free slots in this area,
	 * and the total count of parking
	 */
	public void addParkingSlot(final ParkingSlot ¢) throws ParseException {
		parkingSlots.add(¢);

		updateSlotsArray();
	}

	/*
	 * add new parking slot to this area. notice - this new slot will count as
	 * free slot, and therefore increase the amount of free slots in this area,
	 * and the total count of parking
	 */
	public void removeParkingSlot(final ParkingSlot s) throws ParseException {
		if (parkingSlots == null)
			return;
		for (final Iterator<ParkingSlot> ¢ = parkingSlots.iterator(); ¢.hasNext();)
			if (¢.next().objectId.equalsIgnoreCase(s.objectId))
				¢.remove();
		s.removeParkingSlotFromDB();
		updateSlotsArray();
	}

	// Update the slots array in the DB according to the last update
	private void updateSlotsArray() throws ParseException {
		final List<ParseObject> slots = new ArrayList<ParseObject>();
		if (!parkingSlots.isEmpty())
			for (final ParkingSlot ¢ : parkingSlots)
				slots.add(¢.getParseObject());

		parseObject.put("parkingSlots", slots);
		parseObject.save();
		
	}
	
	private List<ParseObject> parkingSlotsSetToParseList(){
		List<ParseObject> list = new ArrayList<ParseObject>();
		if(!parkingSlots.isEmpty())
			for(final ParkingSlot s: parkingSlots)
				list.add(DBManager.getParseObject(s));
		return list;	
	}
		
}
