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

	// Create a new parking slot. Will result in a new slot in the DB.
	public ParkingSlot(String name, ParkingSlotStatus status, StickersColor color, StickersColor defaultColor,
			MapLocation location, Date endTime) throws ParseException {

		// TODO: Check for NULL values !

		DBManager.initialize();
		this.setParseObject("ParkingSlot");
		this.setName(name);
		this.setStatus(status);
		this.setColor(color);
		this.setLocation(location);
		this.setDefaultColor(defaultColor);
		this.setEndTime(endTime);
		this.setObjectId();
		// this.parseObject = new ParseObject("ParkingSlot");
		// this.parseObject.save();
		// this.objectId = this.parseObject.getObjectId();
	}

	/***
	 * @author David the constructor gets an ParseObject and return a new
	 *         ParkingSlot class according to it
	 * @param obj
	 */
	// public ParkingSlot(ParseObject obj) {
	// DBManager.initialize();
	// this.parseObject = obj;
	// this.name = this.parseObject.getString("name");
	// this.status =
	// ParkingSlotStatus.values()[this.parseObject.getInt("status")];
	// this.color = StickersColor.values()[this.parseObject.getInt("color")];
	// ParseGeoPoint geo = this.parseObject.getParseGeoPoint("location");
	// this.location = new MapLocation(geo.getLatitude(), geo.getLongitude());
	// this.defaultColor =
	// StickersColor.values()[this.parseObject.getInt("defaultColor")];
	// this.endTime = this.parseObject.getDate("endTime");
	// this.objectId = this.parseObject.getObjectId();
	// }

	public ParkingSlot(ParseObject parseObject) throws ParseException {
		this.parseObject = parseObject;
		this.setName((String) parseObject.get("name"));
		// replace all sets with parseObject.get
		this.setStatus(ParkingSlotStatus.values()[(Integer) parseObject.get("status")]);
		this.setColor(StickersColor.values()[(Integer) parseObject.get("color")]);
		ParseGeoPoint geo = this.parseObject.getParseGeoPoint("location");
		this.setLocation(new MapLocation(geo.getLatitude(), geo.getLongitude()));
		this.setDefaultColor(StickersColor.values()[(Integer) parseObject.get("defaultColor")]);
		this.setEndTime(((Date) parseObject.get("endTime")));
		this.setObjectId();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) throws ParseException {
		this.name = name;
		this.parseObject.put("name", name);
		this.parseObject.save();
	}

	public ParkingSlotStatus getStatus() {
		try {
			setStatus(ParkingSlotStatus.values()[this.parseObject.getInt("status")]);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
		}
		return status;
	}

	public void setStatus(ParkingSlotStatus ¢) throws ParseException {
		this.status = ¢;
		this.parseObject.put("status", ¢.ordinal());
		this.parseObject.save();
	}

	public StickersColor getColor() {
		return color;
	}

	public void setColor(StickersColor ¢) throws ParseException {
		this.color = ¢;
		this.parseObject.put("color", ¢.ordinal());
		this.parseObject.save();
	}

	public MapLocation getLocation() {
		return location;
	}

	public void setLocation(MapLocation ¢) throws ParseException {
		this.location = ¢;
		this.parseObject.put("location", (new ParseGeoPoint(¢.getLat(), ¢.getLon())));
		this.parseObject.save();
	}

	public StickersColor getDefaultColor() {
		return defaultColor;
	}

	public void setDefaultColor(StickersColor defaultColor) throws ParseException {
		this.defaultColor = defaultColor;
		this.parseObject.put("defaultColor", defaultColor.ordinal());
		this.parseObject.save();
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) throws ParseException {
		this.endTime = endTime;
		this.parseObject.put("endTime", endTime);
		this.parseObject.save();
	}

	public void changeStatus(ParkingSlotStatus newStatus) {
		try {
			this.setStatus(newStatus);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
		}
	};

	public void removeParkingSlot() throws ParseException {
		this.parseObject.delete();
		this.objectId = "";
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
