package data.members;

import java.util.Date;

import org.parse4j.ParseException;
import org.parse4j.ParseGeoPoint;
import org.parse4j.ParseObject;

import data.management.DBManager;

/**
 * @author Toma
 * @since 12.11.16
 * This class represent a parking slot
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
		this.parseObject = new ParseObject("ParkingSlot");
		this.setName(name);
		this.setStatus(status);
		this.setColor(color);
		this.setLocation(location);
		this.setDefaultColor(defaultColor);
		this.setEndTime(endTime);
		
		this.parseObject.save();
		this.objectId = this.parseObject.getObjectId();
	}

	public ParkingSlot(ParseObject parseObject) throws ParseException {
		this.parseObject = parseObject;
		this.setName((String)parseObject.get("name"));
		//replace all sets with parseObject.get
		this.setStatus(ParkingSlotStatus.values()[(Integer)parseObject.get("status")]);
		this.setColor(StickersColor.values()[(Integer)parseObject.get("color")]);
		this.setLocation((MapLocation)parseObject.get("location"));
		this.setDefaultColor(StickersColor.values()[(Integer)parseObject.get("defaultColor")]);
		this.setEndTime(((Date)parseObject.get("endTime")));
		this.objectId = this.parseObject.getObjectId();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		this.parseObject.put("name", name);
	}

	public ParkingSlotStatus getStatus() {
		return status;
	}

	public void setStatus(ParkingSlotStatus s) {
		this.status = s;
		this.parseObject.put("status", s.ordinal());
	}

	public StickersColor getColor() {
		return color;
	}

	public void setColor(StickersColor c) {
		this.color = c;
		this.parseObject.put("color", c.ordinal());
	}

	public MapLocation getLocation() {
		return location;
	}

	public void setLocation(MapLocation l) {
		this.location = l;

		this.parseObject.put("location", (new ParseGeoPoint(l.getLat(), l.getLon())));
	}

	public StickersColor getDefaultColor() {
		return defaultColor;
	}

	public void setDefaultColor(StickersColor defaultColor) {
		this.defaultColor = defaultColor;
		this.parseObject.put("defaultColor", defaultColor.ordinal());
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
		this.parseObject.put("endTime", endTime);
	}
	
	public void changeStatus(ParkingSlotStatus newStatus){
		this.setStatus(newStatus);
	};
	
	public void removeParkingSlot() throws ParseException{
		this.parseObject.delete();
		this.objectId = "";
	}

}
