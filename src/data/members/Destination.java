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

	private static ParseObject getDbDestinationObject(final String name) {
		final ParseQuery<ParseObject> query = ParseQuery.getQuery(TABLE_NAME);
		query.whereEqualTo(NAME, name);
		query.limit(1);
		try {
			final List<ParseObject> $ = query.find();
			return $ == null || $.isEmpty() ? null : $.get(0);
		} catch (final Exception e) {
			return null;
		}
	}

	public static Map<String, Destination> getDestinations() {
		final ParseQuery<ParseObject> query = ParseQuery.getQuery(TABLE_NAME);
		final Map<String, Destination> $ = new HashMap<String, Destination>();
		try {
			final List<ParseObject> result = query.find();
			if (result == null)
				return $;
			for (final ParseObject dest : result) {
				final String destName = dest.getString(NAME);
				$.put(destName, new Destination(destName));
			}
		} catch (final Exception e) {
		}
		return $;
	}

	public static boolean destinationExists(final String name) {
		return getDbDestinationObject(name) != null;
	}

	public Destination(final String name, final MapLocation location) throws ParseException, AlreadyExists {
		DBManager.initialize();
		setParseObject(TABLE_NAME);

		if (destinationExists(name))
			throw new AlreadyExists("already exists");

		this.setEntrance(location);
		setDestinationName(name);
		setObjectId();
	}

	public Destination(final String name, final double latitude, final double longitude) throws ParseException, AlreadyExists {
		DBManager.initialize();
		setParseObject(TABLE_NAME);

		if (destinationExists(name))
			throw new AlreadyExists("already exists");

		this.setEntrance(new MapLocation(latitude, longitude));
		setDestinationName(name);
		setObjectId();
	}

	public Destination(final String name) throws ParseException, NotExists {
		DBManager.initialize();
		parseObject = getDbDestinationObject(name);
		if (parseObject == null)
			throw new NotExists("not exists");
		this.name = parseObject.getString(NAME);
		final ParseGeoPoint geo = parseObject.getParseGeoPoint(ENTRANCE);
		entrance = new MapLocation(geo.getLatitude(), geo.getLongitude());
	}

	public Destination(final MapLocation location) throws ParseException, NotExists {
		DBManager.initialize();

		final ParseQuery<ParseObject> query = ParseQuery.getQuery(TABLE_NAME);
		query.whereNear(ENTRANCE, new ParseGeoPoint(location.getLat(), location.getLon()));
		query.limit(1);
		final List<ParseObject> result = query.find();
		parseObject = result == null || result.isEmpty() ? null : result.get(0);
		if (parseObject == null)
			throw new NotExists("not exists");
		name = parseObject.getString(NAME);
		final ParseGeoPoint geo = parseObject.getParseGeoPoint(ENTRANCE);
		entrance = new MapLocation(geo.getLatitude(), geo.getLongitude());
	}

	public String getDestinationName() {
		return name;
	}

	public MapLocation getEntrance() {
		return entrance;
	}

	public void setDestinationName(final String name) throws ParseException, AlreadyExists {

		if (destinationExists(name))
			throw new AlreadyExists("already exists");

		this.name = name;
		parseObject.put(NAME, name);
		parseObject.save();
	}

	public void setEntrance(final MapLocation ¢) throws ParseException {
		entrance = ¢;
		parseObject.put(ENTRANCE, new ParseGeoPoint(¢.getLat(), ¢.getLon()));
		parseObject.save();
	}

	public void setEntrance(final double latitude, final double longitude) throws ParseException {
		setEntrance(new MapLocation(latitude, longitude));
	}
}
