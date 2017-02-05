package data.members;

import java.util.Date;
import java.util.List;

import org.parse4j.ParseException;
import org.parse4j.ParseGeoPoint;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

import data.management.DBManager;

/**
 * @author Toma
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

	/* Constructors */

	// Create a new parking slot. Will result in a new slot in the DB.
	public ParkingSlot(String name, ParkingSlotStatus status, StickersColor color, StickersColor defaultColor,
			MapLocation location, Date endTime) throws ParseException {
		validateArgument(status, color, defaultColor, location);

		DBManager.initialize();
		this.setParseObject("ParkingSlot");
		this.setName(name);
		this.setStatus(status);
		this.setColor(color);
		this.setLocation(location);
		this.setDefaultColor(defaultColor);
		this.setEndTime(endTime);
		this.setObjectId();
		this.parseObject.save();
	}

	public ParkingSlot(ParseObject obj) throws ParseException {
		DBManager.initialize();
		this.parseObject = obj;
		this.name = this.parseObject.getString("name");
		this.status = ParkingSlotStatus.values()[this.parseObject.getInt("status")];
		this.color = StickersColor.values()[this.parseObject.getInt("color")];
		ParseGeoPoint geo = this.parseObject.getParseGeoPoint("location");
		this.location = new MapLocation(geo.getLatitude(), geo.getLongitude());
		this.defaultColor = StickersColor.values()[this.parseObject.getInt("defaultColor")];
		this.endTime = this.parseObject.getDate("endTime");
		this.objectId = this.parseObject.getObjectId();
		this.parseObject.save();
	}
	
	public ParkingSlot(String name) throws ParseException {
		DBManager.initialize();
		
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingSlot");
		query.whereEqualTo("name", name);
		ParseObject parseObject = query.find().get(0);
		
		this.parseObject = parseObject;
		this.name = this.parseObject.getString("name");
		this.status = ParkingSlotStatus.values()[this.parseObject.getInt("status")];
		this.color = StickersColor.values()[this.parseObject.getInt("color")];
		ParseGeoPoint geo = this.parseObject.getParseGeoPoint("location");
		this.location = new MapLocation(geo.getLatitude(), geo.getLongitude());
		this.defaultColor = StickersColor.values()[this.parseObject.getInt("defaultColor")];
		this.endTime = this.parseObject.getDate("endTime");
		this.objectId = this.parseObject.getObjectId();
		this.parseObject.save();
	}

	
	public static String ParkingNameByLocation(MapLocation l){
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingSlot");
		query.whereNear("location", new ParseGeoPoint(l.getLat(),l.getLon()));
		query.limit(1);
		try {
			List<ParseObject> $ = query.find();
			return $ == null || $.isEmpty() ? null : $.get(0).getString("name");
		} catch (Exception e) {
			return null;
		}
	}
	
	/* Getters */

	public String getName() {
		return name;
	}

	public ParkingSlotStatus getStatus() {
		return status;
	}

	public StickersColor getColor() {
		return color;
	}

	public MapLocation getLocation() {
		return location;
	}

	public StickersColor getDefaultColor() {
		return defaultColor;
	}
	
	public Date getEndTime() {
		return endTime;
	}

	/* Setters */

	private void setName(String name) throws ParseException {
		this.name = name;
		this.parseObject.put("name", name);
		this.parseObject.save();
	}

	private void setStatus(ParkingSlotStatus ¢) throws ParseException {
		this.status = ¢;
		this.parseObject.put("status", ¢.ordinal());
		this.parseObject.save();
	}

	public void setColor(StickersColor ¢) throws ParseException {
		this.color = ¢;
		this.parseObject.put("color", ¢.ordinal());
		this.parseObject.save();
	}

	private void setLocation(MapLocation ¢) throws ParseException {
		this.location = ¢;
		this.parseObject.put("location", new ParseGeoPoint(¢.getLat(), ¢.getLon()));
		this.parseObject.save();
	}

	private void setDefaultColor(StickersColor defaultColor) throws ParseException {
		this.defaultColor = defaultColor;
		this.parseObject.put("defaultColor", defaultColor.ordinal());
		this.parseObject.save();
	}
	
	public void setEndTime(Date endTime) throws ParseException {
		// TODO: check what happens if null (should be OK)
		this.endTime = endTime;
		this.parseObject.put("endTime", endTime);
		this.parseObject.save();
	}

	/* Methods */

	private void validateArgument(ParkingSlotStatus s, StickersColor c, StickersColor defaultColor, MapLocation l)
			throws IllegalArgumentException {
		if (s == null)
			throw new IllegalArgumentException("status can not be empty!");
		if (c == null)
			throw new IllegalArgumentException("color can not be empty!");
		if (defaultColor == null)
			throw new IllegalArgumentException("defaultColor can not be empty!");
		if (l == null)
			throw new IllegalArgumentException("location can not be empty!");
	}

	private ParseObject findContaingParkingArea() {
		ParseQuery<ParseObject> $ = ParseQuery.getQuery("ParkingArea");
		$.whereEqualTo("parkingSlots", this.parseObject);
		try {
			return $.find().get(0);
		} catch (ParseException ¢) {
			System.out.println("Could not find the containing area");
			¢.printStackTrace();
			return null;
		}
	}

	public String findContainingParkingArea() {
		return (String)findContaingParkingArea().get("name");
	}

	public void changeStatus(ParkingSlotStatus newStatus) {
		try {
			this.setStatus(newStatus);
		} catch (ParseException ¢) {
			System.out.println("could not set the slot's status");
			¢.printStackTrace();
		}
	};

	public void removeParkingSlotFromDB() throws ParseException {
		this.deleteParseObject();
	}

	public void removeParkingSlotFromAreaAndDB() throws ParseException {
		(new ParkingArea(findContainingParkingArea())).removeParkingSlot(this);
	}

	/***
	 * for now only delete from the DB the current parking
	 */
	@Override
	public void deleteParseObject() throws ParseException {
		ParseQuery<ParseObject> query = ParseQuery.getQuery("PMUser");
		query.whereEqualTo("currentParking", this.getParseObject());
		try {
			List<ParseObject> users = query.find();
			if (users != null) {
				users.get(0).remove("currentParking");
				users.get(0).save();
			}
		} catch (Exception e) {
			return;
		}
		parseObject.delete();
	}

}
