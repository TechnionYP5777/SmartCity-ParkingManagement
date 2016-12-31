package data.members;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.parse4j.ParseException;
import org.parse4j.ParseGeoPoint;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

import Exceptions.*;
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

	private static ParseObject getDbDestinationObject(String name) {
		ParseQuery<ParseObject> query = ParseQuery.getQuery(TABLE_NAME);
		query.whereEqualTo(NAME, name);
		query.limit(1);
		try {
			List<ParseObject> result = query.find();
			if (result == null || result.size() == 0)
				return null;
			return result.get(0);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Map<String,Destination> getDestinations() {
		ParseQuery<ParseObject> query = ParseQuery.getQuery(TABLE_NAME);
		Map<String,Destination> $ = new HashMap<String,Destination>(); 
		try {
			List<ParseObject> result = query.find();
			if (result == null) return $;
			for (ParseObject dest : result) {
				String destName = dest.getString(NAME);
				$.put(destName, new Destination(destName));
			}
		} catch (Exception e) {}
		return $;
	}
	
	public static boolean destinationExists(String name){
		return getDbDestinationObject(name) != null;
	}
	
	public Destination(String name, MapLocation location) throws ParseException, AlreadyExists {
		DBManager.initialize();
		this.setParseObject(TABLE_NAME);
		
		if (destinationExists(name))
			throw new AlreadyExists("already exists");
		
		this.setEntrance(location);
		this.setDestinationName(name);
		this.setObjectId();
	}
	
	public Destination(String name, double latitude, double longitude) throws ParseException, AlreadyExists {
		DBManager.initialize();
		this.setParseObject(TABLE_NAME);
		
		if (destinationExists(name))
			throw new AlreadyExists("already exists");
		
		this.setEntrance(new MapLocation(latitude, longitude));
		this.setDestinationName(name);
		this.setObjectId();
	}
	
	public Destination(String name) throws ParseException, NotExists {
		DBManager.initialize();
		this.parseObject = getDbDestinationObject(name);
		if (this.parseObject == null) throw new NotExists("not exists");
		this.name = this.parseObject.getString(NAME);
		ParseGeoPoint geo = this.parseObject.getParseGeoPoint(ENTRANCE);
		this.entrance = new MapLocation(geo.getLatitude(), geo.getLongitude());		
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
