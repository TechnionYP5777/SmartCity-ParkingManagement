package data.members;

import org.parse4j.ParseException;
import org.parse4j.ParseGeoPoint;

import data.management.DBManager;

/**
 * @author dshames
 * @Author DavidCohen55
 */
public class Destination extends dbMember {
	
	// destination name, for example: Pool, Taub, etc..
	private String name;
	
	// destination's entrance geo location
	private MapLocation entrance;
	
	private static final String NAME = "name";
	private static final String ENTRANCE = "entrance";
	private static final String TABLE_NAME = "Destination";

	public Destination(String name, MapLocation location) throws ParseException {
		
		DBManager.initialize();
		this.setParseObject(TABLE_NAME);
		this.setEntrance(location);
		this.setDestinationName(name);
		this.setObjectId();
	}
	
	public Destination(String name, double latitude, double longitude) throws ParseException {
		DBManager.initialize();
		this.setParseObject(TABLE_NAME);
		this.setEntrance(new MapLocation(latitude, longitude));
		this.setDestinationName(name);
		this.setObjectId();
	}

	public String getDestinationName() {
		return this.name;
	}

	public MapLocation getEntrance() {
		return this.entrance;
	}

	public void setDestinationName(String name) throws ParseException {
		
		this.name = name;
		this.parseObject.put(NAME, name);
		this.parseObject.save();
	}
	public void setEntrance(MapLocation ¢) throws ParseException {
		this.entrance = ¢;
		this.parseObject.put(ENTRANCE, new ParseGeoPoint(¢.getLat(), ¢.getLon()));
		this.parseObject.save();
		
	}

	public void setEntrance(double latitude, double longitude) throws ParseException {
		setEntrance(new MapLocation(latitude, longitude));
	}


}
