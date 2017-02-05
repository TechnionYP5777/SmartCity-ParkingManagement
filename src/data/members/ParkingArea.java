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
		return areaId;
	}

	public String getName() {
		return name;
	}

	public int getNumOfParkingSlots() {
		final List<ParseObject> $ = getAllSlots();
		return $ == null ? 0 : $.size();
	}

	public MapLocation getLocation() {
		return location;
	}

	public Set<ParkingSlot> getParkingSlots() {
		return parkingSlots;
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
		return color;
	}

	/* Setters */

	private void setAreaId(final int areaId) {
		this.areaId = areaId;
		parseObject.put("areaId", areaId);
	}

	private void setName(final String name) {
		this.name = name;
		parseObject.put("name", name);
	}

	public void setLocation(final MapLocation ¢) {
		location = ¢;
		parseObject.put("location", new ParseGeoPoint(¢.getLat(), ¢.getLon()));
	}

	private void setParkingSlots(final Set<ParkingSlot> ¢) throws ParseException {
		parkingSlots = ¢;
		updateSlotsArray();
	}

	private void setColor(final StickersColor ¢) {
		color = ¢;
		parseObject.put("color", ¢.ordinal());
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
}
