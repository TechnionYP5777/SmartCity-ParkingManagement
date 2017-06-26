package data.members;

import java.util.Date;
import java.util.HashMap;

import java.util.Map;
import java.util.logging.Logger;

import org.parse4j.ParseException;
import org.parse4j.ParseGeoPoint;
import org.parse4j.ParseObject;

import data.management.DatabaseManager;
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

	// The slot's rank. Can be any sticker color
	private StickersColor rank;

	// The slot's location
	private MapLocation location;

	// The slot's default rank
	private StickersColor defaultColor;

	// The slot's endTime. if null the color permanent, else temporary
	private Date endTime;
	
	// The slot's area
	private Area area;
	
	// The slot's total rating
	private int rating;
	
	//number of voting to the specific slot
	private int numOfVoters;
	
	private DatabaseManager dbm;
	
	private final String objectClass = "ParkingSlot";
	
	private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/* Constructors */

	// Create a new parking slot. Will result in a new slot in the DB.
	public ParkingSlot(final String name, final ParkingSlotStatus status, final StickersColor rank, final StickersColor defaultColor,
			final MapLocation location, final Date endTime, final Area area, final int rating, final int voting, DatabaseManager manager) throws ParseException {
		LOGGER.info("Create a new parking slot by name, status, color, sticker color, expiration time");
		this.dbm = manager;
		validateArgument(status, rank, defaultColor, location, area, rating, voting);
		Map<String, Object> fields = new HashMap<String, Object>(), keyValues = new HashMap<String, Object>();
		fields.put("status", status.ordinal());
		fields.put("rank", rank.ordinal());
		fields.put("defaultColor", defaultColor.ordinal());
		fields.put("location", new ParseGeoPoint(location.getLat(), location.getLon()));
		fields.put("endTime", endTime);
		fields.put("area", area.ordinal());
		fields.put("rating", rating);
		fields.put("numOfVoters", voting);
		keyValues.put("name", name);
		dbm.insertObject(objectClass, keyValues, fields);
	}

	public ParkingSlot(final ParseObject obj) throws ParseException {
		dbm.initialize();
		parseObject = obj;
		name = parseObject.getString("name");
		status = ParkingSlotStatus.values()[parseObject.getInt("status")];
		rank = StickersColor.values()[parseObject.getInt("rank")];
		final ParseGeoPoint geo = parseObject.getParseGeoPoint("location");
		location = new MapLocation(geo.getLatitude(), geo.getLongitude());
		defaultColor = StickersColor.values()[parseObject.getInt("defaultColor")];
		endTime = parseObject.getDate("endTime");
		area = Area.values()[parseObject.getInt("area")];
		objectId = parseObject.getObjectId();
		parseObject.save();
	}

	public ParkingSlot(final String name, DatabaseManager manager) throws ParseException {
		LOGGER.info("Create a new parking slot by name");
		this.dbm = manager;
		dbm.initialize();

		Map<String, Object> keys = new HashMap<>();
		keys.put("name", name);
		Map<String,Object> returnV = dbm.getObjectFieldsByKey(objectClass, keys);
		
		this.rank=StickersColor.values()[(int)returnV.get("rank")];
		this.numOfVoters=(int)returnV.get("numOfVoters");
		this.rating=(int)returnV.get("rating");
		this.defaultColor= StickersColor.values()[(int)returnV.get("defaultColor")];
		this.area= Area.values()[(int)returnV.get("area")];
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
		return dbm.getObjectFieldsByKey("ParkingSlot", key).get("name") + "";
	}
	
	public int getRating() {
		Map<String, Object> key = new HashMap<String, Object>();
		key.put("name", name);
		return (int) dbm.getObjectFieldsByKey("ParkingSlot", key).get("rating");
	}
	
	public int getNumOfVoters() {
		Map<String, Object> key = new HashMap<String, Object>();
		key.put("name", name);
		return (int) dbm.getObjectFieldsByKey("ParkingSlot", key).get("numOfVoters");
	}

	public ParkingSlotStatus getStatus() {
		Map<String, Object> key = new HashMap<String, Object>();
		key.put("name", name);
		return ParkingSlotStatus.values()[(int)dbm.getObjectFieldsByKey("ParkingSlot", key).get("status")];
	}

	public StickersColor getRank() {
		Map<String, Object> key = new HashMap<String, Object>();
		key.put("name", name);
		return StickersColor.values()[(int)dbm.getObjectFieldsByKey("ParkingSlot", key).get("rank")];
	}

	public MapLocation getLocation() {
		Map<String, Object> key = new HashMap<String, Object>();
		key.put("name", name);
		Map<String,Object> returnV = dbm.getObjectFieldsByKey(objectClass, key);
		return new MapLocation(((ParseGeoPoint) returnV.get("location")).getLatitude(),
				((ParseGeoPoint) returnV.get("location")).getLongitude());
	}
	
	public ParseGeoPoint getParseGeoPoint(){
		return new ParseGeoPoint(location.getLat(), location.getLon());
	}

	public StickersColor getDefaultColor() {
		Map<String, Object> key = new HashMap<String, Object>();
		key.put("name", name);
		return StickersColor.values()[(int)dbm.getObjectFieldsByKey("ParkingSlot", key).get("defaultColor")];
	}

	public Date getEndTime() {
		Map<String, Object> key = new HashMap<String, Object>();
		key.put("name", name);
		return ((Date) dbm.getObjectFieldsByKey(objectClass, key).get("endTime"));
	
	}
	
	public Area getArea() {
		Map<String, Object> key = new HashMap<String, Object>();
		key.put("name", name);
		return Area.values()[(int)dbm.getObjectFieldsByKey("ParkingSlot", key).get("area")];
	}

	/* Setters */

	public void setName(final String name) throws ParseException {
		LOGGER.info("Set parking slot name");
		if (name == null)
			throw new IllegalArgumentException("name can not be empty!");
		Map<String, Object> newFields = new HashMap<String, Object>();
		newFields.put("name", name);
		newFields.put("rating", this.rating);
		newFields.put("numOfVoters", this.numOfVoters);
		newFields.put("rank", this.rank.ordinal());
		newFields.put("defaultColor", this.defaultColor.ordinal());
		newFields.put("status", this.status.ordinal());
		newFields.put("endTime", this.endTime);
		newFields.put("area", this.area.ordinal());
		newFields.put("location", new ParseGeoPoint(this.location.getLat(), this.location.getLon()));
		
		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("name", this.name);
		dbm.update(objectClass, keys, newFields);
	}

	public void setStatus(final ParkingSlotStatus s) throws ParseException {
		LOGGER.info("Set parking slot status");
		
		if (s == null)
			throw new IllegalArgumentException("status can not be empty!");
		Map<String, Object> newFields = new HashMap<String, Object>();
		newFields.put("name", this.name);
		newFields.put("rating", this.rating);
		newFields.put("numOfVoters", this.numOfVoters);
		newFields.put("rank", this.rank.ordinal());
		newFields.put("defaultColor", this.defaultColor.ordinal());
		newFields.put("status", s.ordinal());
		newFields.put("endTime", this.endTime);
		newFields.put("area", this.area.ordinal());
		newFields.put("location", new ParseGeoPoint(this.location.getLat(), this.location.getLon()));
		
		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("name", this.name);
		dbm.update(objectClass, keys, newFields);
	}

	public void setRank(final StickersColor c) throws ParseException {
		LOGGER.info("Set parking slot color");
		
		if (c == null)
			throw new IllegalArgumentException("rank can not be empty!");
		Map<String, Object> newFields = new HashMap<String, Object>();
		newFields.put("name", this.name);
		newFields.put("rating", this.rating);
		newFields.put("numOfVoters", this.numOfVoters);
		newFields.put("rank", c.ordinal());
		newFields.put("defaultColor", this.defaultColor.ordinal());
		newFields.put("status", this.status.ordinal());
		newFields.put("endTime", this.endTime);
		newFields.put("area", this.area.ordinal());
		newFields.put("location", new ParseGeoPoint(this.location.getLat(), this.location.getLon()));
		
		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("name", this.name);
		dbm.update(objectClass, keys, newFields);
	}

	public void setLocation(final MapLocation l) throws ParseException {
		LOGGER.info("Set parking slot location");
		
		if (l == null)
			throw new IllegalArgumentException("location can not be empty!");
		Map<String, Object> newFields = new HashMap<String, Object>();
		newFields.put("name", this.name);
		newFields.put("rating", this.rating);
		newFields.put("numOfVoters", this.numOfVoters);
		newFields.put("rank", this.rank.ordinal());
		newFields.put("defaultColor", this.defaultColor.ordinal());
		newFields.put("status", this.status.ordinal());
		newFields.put("endTime", this.endTime);
		newFields.put("area", this.area.ordinal());
		newFields.put("location", new ParseGeoPoint(l.getLat(), l.getLon()));
		
		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("name", this.name);
		dbm.update(objectClass, keys, newFields);
	}

	public void setDefaultColor(final StickersColor defaultColor) throws ParseException {
		LOGGER.info("Set parking slot default color");
		
		if (defaultColor == null)
			throw new IllegalArgumentException("default color can not be empty!");
		Map<String, Object> newFields = new HashMap<String, Object>();
		newFields.put("name", this.name);
		newFields.put("rating", this.rating);
		newFields.put("numOfVoters", this.numOfVoters);
		newFields.put("rank", this.rank.ordinal());
		newFields.put("defaultColor", defaultColor.ordinal());
		newFields.put("status", this.status.ordinal());
		newFields.put("endTime", this.endTime);
		newFields.put("area", this.area.ordinal());
		newFields.put("location", new ParseGeoPoint(this.location.getLat(), this.location.getLon()));
		
		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("name", this.name);
		dbm.update(objectClass, keys, newFields);
	}
	
	public void setArea(final Area newArea) throws ParseException {
		LOGGER.info("Set parking slot area");
		
		if (newArea == null)
			throw new IllegalArgumentException("area can not be empty!");
		Map<String, Object> newFields = new HashMap<String, Object>();
		newFields.put("name", this.name);
		newFields.put("rating", this.rating);
		newFields.put("numOfVoters", this.numOfVoters);
		newFields.put("rank", this.rank.ordinal());
		newFields.put("defaultColor", this.defaultColor.ordinal());
		newFields.put("status", this.status.ordinal());
		newFields.put("endTime", this.endTime);
		newFields.put("area", newArea.ordinal());
		newFields.put("location", new ParseGeoPoint(this.location.getLat(), this.location.getLon()));
		
		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("name", this.name);
		dbm.update(objectClass, keys, newFields);
	}

	public void setEndTime(final Date endTime) throws ParseException {
		LOGGER.info("Set parking slot expiration time");
		
		if (endTime == null)
			throw new IllegalArgumentException("end time can not be empty!");
		Map<String, Object> newFields = new HashMap<String, Object>();
		newFields.put("name", this.name);
		newFields.put("rating", this.rating);
		newFields.put("numOfVoters", this.numOfVoters);
		newFields.put("rank", this.rank.ordinal());
		newFields.put("defaultColor", this.defaultColor.ordinal());
		newFields.put("status", this.status.ordinal());
		newFields.put("endTime", endTime);
		newFields.put("area", this.area.ordinal());
		newFields.put("location", new ParseGeoPoint(this.location.getLat(), this.location.getLon()));
		
		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("name", this.name);
		dbm.update(objectClass, keys, newFields);
	}
	
	public void setRating(final double newRating) throws ParseException {
		LOGGER.info("Set parking slot rating");
		if (newRating < 0)
			throw new IllegalArgumentException("end time can not be empty!");
		Map<String, Object> newFields = new HashMap<String, Object>();
		newFields.put("name", this.name);
		newFields.put("rating", newRating);
		newFields.put("numOfVoters", this.numOfVoters);
		newFields.put("rank", this.rank.ordinal());
		newFields.put("defaultColor", this.defaultColor.ordinal());
		newFields.put("status", this.status.ordinal());
		newFields.put("endTime", endTime);
		newFields.put("area", this.area.ordinal());
		newFields.put("location", new ParseGeoPoint(this.location.getLat(), this.location.getLon()));
		
		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("name", this.name);
		dbm.update(objectClass, keys, newFields);
	}
	
	public void setNumOfVoters(final int newNumOfVoters) throws ParseException {
		LOGGER.info("Set parking slot rating");
		
		if (newNumOfVoters < 0)
			throw new IllegalArgumentException("end time can not be empty!");
		Map<String, Object> newFields = new HashMap<String, Object>();
		newFields.put("name", this.name);
		newFields.put("rating", this.rating);
		newFields.put("numOfVoters", newNumOfVoters);
		newFields.put("rank", this.rank.ordinal());
		newFields.put("defaultColor", this.defaultColor.ordinal());
		newFields.put("status", this.status.ordinal());
		newFields.put("endTime", endTime);
		newFields.put("area", this.area.ordinal());
		newFields.put("location", new ParseGeoPoint(this.location.getLat(), this.location.getLon()));
		
		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put("name", this.name);
		dbm.update(objectClass, keys, newFields);
	}
	
	/* Methods */

	private void validateArgument(final ParkingSlotStatus s, final StickersColor c, final StickersColor defaultColor, final MapLocation l, final Area a, final int r, final int numOfV) {
		Validation.validateNewParkingSlot(s, c, defaultColor, l,a, r, numOfV);
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
		fields.put("rank", rank.ordinal());
		fields.put("defaultColor", defaultColor.ordinal());
		fields.put("area", area.ordinal());
		fields.put("location", new ParseGeoPoint(location.getLat(), location.getLon()));
		fields.put("rating", rating);
		fields.put("numOfVoters", numOfVoters);
		fields.put("endTime", endTime);
		fields.put("name", name);
		dbm.deleteObject(objectClass, fields);
	}




}
