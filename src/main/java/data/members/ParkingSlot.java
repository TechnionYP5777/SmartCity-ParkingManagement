package data.members;

import java.util.Date;
import java.util.HashMap;

import java.util.Map;
import java.util.logging.Logger;

import org.parse4j.ParseException;
import org.parse4j.ParseGeoPoint;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

import data.management.DBManager;
import util.Validation;

/**
 * @author Toma
 * 		   Inbal Matityahu
 * @since 12.11.16 This class represent a parking slot
 */
public class ParkingSlot extends dbMember {

	// The slot's name (id). Should be a unique value.
	private String name;

	// The slot's status. Can be either free, taken or unavailable
	private ParkingSlotStatus status;

	// The slot's color. Can be any sticker color
	private StickersColor color;

	// The slot's location
	private MapLocation location;

	// The slot's default color
	private StickersColor defaultColor;

	// The slot's endTime. if null the color permanent, else temporary
	private Date endTime;
	
	private final String objectClass = "ParkingSlot";
	
	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/* Constructors */

	// Create a new parking slot. Will result in a new slot in the DB.
	public ParkingSlot(final String name, final ParkingSlotStatus status, final StickersColor color, final StickersColor defaultColor,
			final MapLocation location, final Date endTime) throws ParseException {
		LOGGER.info("Create a new parking slot by name, status, color, sticker color, expiration time");
		
		DBManager.initialize();
		
		validateArgument(status, color, defaultColor, location);
		Map<String, Object> fields = new HashMap<String, Object>(), keyValues = new HashMap<String, Object>();
		fields.put("status", status.ordinal());
		fields.put("color", color.ordinal());
		fields.put("defaultColor", defaultColor.ordinal());
		fields.put("location", new ParseGeoPoint(location.getLat(), location.getLon()));
		fields.put("endTime", endTime);
		
		keyValues.put("name", name);
		DBManager.insertObject(objectClass, keyValues, fields);
	}

	public ParkingSlot(final ParseObject obj) throws ParseException {
		//TODO
		DBManager.initialize();
		parseObject = obj;
		name = parseObject.getString("name");
		status = ParkingSlotStatus.values()[parseObject.getInt("status")];
		color = StickersColor.values()[parseObject.getInt("color")];
		final ParseGeoPoint geo = parseObject.getParseGeoPoint("location");
		location = new MapLocation(geo.getLatitude(), geo.getLongitude());
		defaultColor = StickersColor.values()[parseObject.getInt("defaultColor")];
		endTime = parseObject.getDate("endTime");
		objectId = parseObject.getObjectId();
		parseObject.save();
	}

	public ParkingSlot(final String name) throws ParseException {
		LOGGER.info("Create a new parking slot by name");
		DBManager.initialize();

		Map<String, Object> keys = new HashMap<>();
		keys.put("name", name);
		Map<String,Object> returnV = DBManager.getObjectFieldsByKey(objectClass, keys);
		
		this.color=StickersColor.values()[(int)returnV.get("color")];
		
		this.defaultColor= StickersColor.values()[(int)returnV.get("defaultColor")];
		this.endTime= (Date) returnV.get("endTime");
		
		final ParseGeoPoint geo = (ParseGeoPoint) returnV.get("location");
	
		this.location = new MapLocation(geo.getLatitude(), geo.getLongitude());
	
		this.name=name;
		this.status=ParkingSlotStatus.values()[(int)returnV.get("status")];
	}

	
	/* Getters */

	public String getName() {
		Map<String, Object> key = new HashMap<String, Object>();
		key.put("name", name);
		return DBManager.getObjectFieldsByKey("ParkingSlot", key).get("name") + "";
	}

	public ParkingSlotStatus getStatus() {
		Map<String, Object> key = new HashMap<String, Object>();
		key.put("name", name);
		return ParkingSlotStatus.values()[(int)DBManager.getObjectFieldsByKey("ParkingSlot", key).get("status")];
	}

	public StickersColor getColor() {
		Map<String, Object> key = new HashMap<String, Object>();
		key.put("name", name);
		return StickersColor.values()[(int)DBManager.getObjectFieldsByKey("ParkingSlot", key).get("color")];
	}

	public MapLocation getLocation() {
		Map<String, Object> key = new HashMap<String, Object>();
		key.put("name", name);
		Map<String,Object> returnV = DBManager.getObjectFieldsByKey(objectClass, key);
		return new MapLocation(((ParseGeoPoint) returnV.get("location")).getLatitude(),
				((ParseGeoPoint) returnV.get("location")).getLongitude());
	}

	public StickersColor getDefaultColor() {
		Map<String, Object> key = new HashMap<String, Object>();
		key.put("name", name);
		return StickersColor.values()[(int)DBManager.getObjectFieldsByKey("ParkingSlot", key).get("defaultColor")];
	}

	public Date getEndTime() {
		Map<String, Object> key = new HashMap<String, Object>();
		key.put("name", name);
		return ((Date) DBManager.getObjectFieldsByKey(objectClass, key).get("endTime"));
	
	}

	/* Setters */

	public void setName(final String name) throws ParseException {
		LOGGER.info("Set parking slot name");
		if (name == null)
			throw new IllegalArgumentException("name can not be empty!");
		Map<String, Object> newFields = new HashMap<String, Object>();
		newFields.put("name", name);
		newFields.put("color", this.color.ordinal());
		newFields.put("defaultColor", this.defaultColor.ordinal());
		newFields.put("status", this.status.ordinal());
		newFields.put("endTime", this.endTime);
		newFields.put("location", new ParseGeoPoint(this.location.getLat(), this.location.getLon()));
		
		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("name", this.name);
		DBManager.update(objectClass, keys, newFields);
	}

	public void setStatus(final ParkingSlotStatus s) throws ParseException {
		LOGGER.info("Set parking slot status");
		
		if (s == null)
			throw new IllegalArgumentException("status can not be empty!");
		Map<String, Object> newFields = new HashMap<String, Object>();
		newFields.put("name", this.name);
		newFields.put("color", this.color.ordinal());
		newFields.put("defaultColor", this.defaultColor.ordinal());
		newFields.put("status", s.ordinal());
		newFields.put("endTime", this.endTime);
		newFields.put("location", new ParseGeoPoint(this.location.getLat(), this.location.getLon()));
		
		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("name", this.name);
		DBManager.update(objectClass, keys, newFields);
	}

	public void setColor(final StickersColor c) throws ParseException {
		LOGGER.info("Set parking slot color");
		
		if (c == null)
			throw new IllegalArgumentException("color can not be empty!");
		Map<String, Object> newFields = new HashMap<String, Object>();
		newFields.put("name", this.name);
		newFields.put("color", c.ordinal());
		newFields.put("defaultColor", this.defaultColor.ordinal());
		newFields.put("status", this.status.ordinal());
		newFields.put("endTime", this.endTime);
		newFields.put("location", new ParseGeoPoint(this.location.getLat(), this.location.getLon()));
		
		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("name", this.name);
		DBManager.update(objectClass, keys, newFields);
	}

	public void setLocation(final MapLocation l) throws ParseException {
		LOGGER.info("Set parking slot location");
		
		if (l == null)
			throw new IllegalArgumentException("location can not be empty!");
		Map<String, Object> newFields = new HashMap<String, Object>();
		newFields.put("name", this.name);
		newFields.put("color", this.color.ordinal());
		newFields.put("defaultColor", this.defaultColor.ordinal());
		newFields.put("status", this.status.ordinal());
		newFields.put("endTime", this.endTime);
		newFields.put("location", new ParseGeoPoint(l.getLat(), l.getLon()));
		
		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("name", this.name);
		DBManager.update(objectClass, keys, newFields);
	}

	public void setDefaultColor(final StickersColor defaultColor) throws ParseException {
		LOGGER.info("Set parking slot default color");
		
		if (defaultColor == null)
			throw new IllegalArgumentException("default color can not be empty!");
		Map<String, Object> newFields = new HashMap<String, Object>();
		newFields.put("name", this.name);
		newFields.put("color", this.color.ordinal());
		newFields.put("defaultColor", defaultColor.ordinal());
		newFields.put("status", this.status.ordinal());
		newFields.put("endTime", this.endTime);
		newFields.put("location", new ParseGeoPoint(this.location.getLat(), this.location.getLon()));
		
		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("name", this.name);
		DBManager.update(objectClass, keys, newFields);
	}

	public void setEndTime(final Date endTime) throws ParseException {
		LOGGER.info("Set parking slot expiration time");
		
		if (endTime == null)
			throw new IllegalArgumentException("end time can not be empty!");
		Map<String, Object> newFields = new HashMap<String, Object>();
		newFields.put("name", this.name);
		newFields.put("color", this.color.ordinal());
		newFields.put("defaultColor", this.defaultColor.ordinal());
		newFields.put("status", this.status.ordinal());
		newFields.put("endTime", endTime);
		newFields.put("location", new ParseGeoPoint(this.location.getLat(), this.location.getLon()));
		
		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("name", this.name);
		DBManager.update(objectClass, keys, newFields);
	}
	
	/* Methods */

	private void validateArgument(final ParkingSlotStatus s, final StickersColor c, final StickersColor defaultColor, final MapLocation l) {
		Validation.validateNewParkingSlot(s, c, defaultColor, l);
	}

	private ParseObject findContaingParkingArea() {
		//TODO: are we still want to hold to each parkingArea a list of slots?
		//or maybe for each slot to hold an area?
		final ParseQuery<ParseObject> $ = ParseQuery.getQuery("ParkingArea");
		$.whereEqualTo("parkingSlots", parseObject);
		try {
			return $.find().get(0);
		} catch (final ParseException ¢) {
			System.out.println("Could not find the containing area");
			¢.printStackTrace();
			return null;
		}
	}

	public String findContainingParkingArea() {
		return (String) findContaingParkingArea().get("name");
	}

	public void changeStatus(final ParkingSlotStatus newStatus) {
		try {
			setStatus(newStatus);
		} catch (final ParseException e) {
			LOGGER.severe("could not set the slot's status");
		}
	};

	public void removeParkingSlotFromDB() throws ParseException {
		LOGGER.fine("Removing parking slot from DB");
		
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("status", status.ordinal());
		fields.put("color", color.ordinal());
		fields.put("defaultColor", defaultColor.ordinal());
		
		fields.put("location", new ParseGeoPoint(location.getLat(), location.getLon()));
		
		fields.put("endTime", endTime);
		fields.put("name", name);
		DBManager.deleteObject(objectClass, fields);
	}

	public void removeParkingSlotFromAreaAndDB() throws ParseException {
		new ParkingArea(findContainingParkingArea()).removeParkingSlot(this);
	}



}
