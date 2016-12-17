package data.members;

import java.util.List;

//import javax.tools.JavaFileManager.Location;

import org.parse4j.ParseException;
//import org.parse4j.ParseGeoPoint;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;
//import org.parse4j.callback.FindCallback;

import data.management.DBManager;
//import sun.awt.SunHints.Value;

/** 
 * @author Toma
 * @since 12.11.16This class represent a parking slot
 * [[SuppressWarningsSpartan]]
 */
public class ParkingSlot {
	
	// The slot's name (id). Should be a unique value. 
	private String name;

	// The slot's status. Can be either free, taken or unavailable
	private ParkingSlotStatus status;

	// The slot's color. Can be any sticker color
	private StickersColor color;

	//	 The slot's location
	private MapLocation location;

	// The slot's area
	private ParkingArea parkingArea;
	
	private ParseObject slot;

	// FIXME: at this point, it is difficult to have a double pointer relation in the DB
	// current user which park on this parking slot
	//	private User currentUser;

	// Create a new parking slot. Will result in a new slot in the DB.
	public ParkingSlot(String name, ParkingSlotStatus status, StickersColor color, MapLocation location,
			ParkingArea parkingArea) throws ParseException{
		DBManager.initialize();
		this.slot = new ParseObject("ParkingSlot");
		this.setName(name);
		this.setStatus(status);
		this.setColor(color);
		this.setLocation(location);
		this.setParkingArea(parkingArea);
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
		this.slot.put("status", status);
	}

	public StickersColor getColor() {
		return color;
	}

	public void setColor(StickersColor color) {
		this.color = color;
		this.slot.put("color", color);
	}

	public MapLocation getLocation() {
		return location;
	}

	public void setLocation(MapLocation location) {
		this.location = location;
		
		// FIXME: fix this to use actual value!
		//ParseGeoPoint point = new ParseGeoPoint(location.getLat(), location.getLon());
		
		this.slot.put("location", location);
	}

	public ParkingArea getParkingArea() {
		return parkingArea;
	}

	public void setParkingArea(ParkingArea parkingArea) {
		this.parkingArea = parkingArea;
		
		// get the parkingArea objectId from parse
		String objId;
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("areaId", parkingArea.getAreaId());
		try {
			List<ParseObject> areaList = query.find();
			if(areaList.size()!=1){
	        	   System.out.format("Something went worng while searching for %d", parkingArea.getAreaId() );
	        	   return;
	           }
			objId = areaList.get(0).getObjectId();
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}

		// add the pointer to this id
		this.slot.put("ParkingArea", objId);
	}

}
