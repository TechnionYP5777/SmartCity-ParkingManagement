package data.members;

import java.util.Date;

import org.parse4j.ParseException;
import org.parse4j.ParseGeoPoint;
import org.parse4j.ParseObject;

import data.management.DBManager;

/**
 * @author Toma
 * @since 12.11.16This class represent a parking slot
 *        [[SuppressWarningsSpartan]]
 */
public class ParkingSlot {

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

	private ParseObject slot;

	// Create a new parking slot. Will result in a new slot in the DB.
	public ParkingSlot(String name, ParkingSlotStatus status, StickersColor color, StickersColor defaultColor,
			MapLocation location, Date endTime) throws ParseException {

		// TODO: Check for NULL values !

		DBManager.initialize();
		this.slot = new ParseObject("ParkingSlot");
		this.setName(name);
		this.setStatus(status);
		this.setColor(color);
		this.setLocation(location);
		this.setDefaultColor(defaultColor);
		this.setEndTime(endTime);
		slot.save();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		this.slot.put("name", name);
	}

	public ParkingSlotStatus getStatus() {
		return status;
	}

	public void setStatus(ParkingSlotStatus status) {
		this.status = status;
		this.slot.put("status", status.ordinal());
	}

	public StickersColor getColor() {
		return color;
	}

	public void setColor(StickersColor color) {
		this.color = color;
		this.slot.put("color", color.ordinal());
	}

	public MapLocation getLocation() {
		return location;
	}

	public void setLocation(MapLocation location) {
		this.location = location;

		ParseGeoPoint point = new ParseGeoPoint(location.getLat(), location.getLon());
		this.slot.put("location", point);
	}

	public StickersColor getDefaultColor() {
		return defaultColor;
	}

	public void setDefaultColor(StickersColor defaultColor) {
		this.defaultColor = defaultColor;
		this.slot.put("defaultColor", defaultColor.ordinal());
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
		this.slot.put("endTime", endTime);
	}
	
	public void changeStatus(ParkingSlotStatus newStatus){
		this.setStatus(newStatus);
	};

}
