package data.members;

import java.util.Set;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

import data.management.DBManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * @author Inbal Matityahu
 * @author Toma
 * @since 12.12.16
 * 
 *        This class represent all parking areas on Technion's site
 */

public class ParkingAreas extends dbMember {
	private Set<ParkingArea> parkingAreas;

	/* Constructors */

	public ParkingAreas(final Set<ParkingArea> parkingAreas) throws ParseException {
		DBManager.initialize();
		parseObject = new ParseObject("ParkingAreas");
		setParkingAreas(parkingAreas);

		parseObject.save();
		objectId = parseObject.getObjectId();
	}

	public ParkingAreas() {
		DBManager.initialize();
		parkingAreas = new HashSet<ParkingArea>();
		final ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		try {
			final List<ParseObject> areaList = query.find();
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There was a problem - ParkingArea table doesnt found");
			for (final ParseObject ¢ : areaList)
				parkingAreas.add(new ParkingArea(¢));
		} catch (final ParseException ¢) {
			¢.printStackTrace();
		}
	}

	/* Getters */

	public Set<ParkingArea> getParkingAreas() {
		return parkingAreas;
	}

	/* Setters */

	public void setParkingAreas(final Set<ParkingArea> ¢) throws ParseException {
		parkingAreas = ¢;
		updateAreasArray();
	}

	/* Methods */

	public void addParkingArea(final ParkingArea ¢) throws ParseException {
		parkingAreas.add(¢);

		updateAreasArray();
	}

	public void removeParkingArea(final ParkingArea a) throws ParseException {

		// search if parkingArea exist
		final ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("areaId", a.getAreaId());
		if (query.find().size() != 1 || parkingAreas == null) {
			System.out.format("The area %d doest exist", a.getAreaId());
			return;
		}

		// remove the are
		for (final ParkingArea ¢ : parkingAreas)
			if (¢.objectId.equals(a.objectId))
				parkingAreas.remove(¢);
		a.removeParkingAreaFromDB();
		updateAreasArray();
	}

	// Return num of taken parking slots by a given area
	public int getNumOfTakenByArea(final ParkingArea a) {
		int $ = 0;
		for (final ParkingArea currentArea : parkingAreas)
			if (currentArea.getAreaId() == a.getAreaId())
				$ = currentArea.getNumOfTakenSlots();
		return $;
	}

	// Return num of free parking slots by given area
	public int getNumOfFreeByArea(final ParkingArea a) {
		int $ = 0;
		for (final ParkingArea currentArea : parkingAreas)
			if (currentArea.getAreaId() == a.getAreaId())
				$ = currentArea.getNumOfFreeSlots();
		return $;
	}

	// Return num of free parking slots
	public int getNumOfFreeSlots() {
		int $ = 0;
		for (final ParkingArea currentArea : parkingAreas)
			$ += currentArea.getNumOfFreeSlots();
		return $;
	}

	// Return num of taken parking slots
	public int getNumOfTakenSlots() {
		int $ = 0;
		for (final ParkingArea currentArea : parkingAreas)
			$ += currentArea.getNumOfTakenSlots();
		return $;
	}

	// Return parking slots per area
	public int getNumOfSlotsByArea(final ParkingArea a) {
		int $ = 0;
		for (final ParkingArea currentArea : parkingAreas)
			if (currentArea.getAreaId() == a.getAreaId())
				$ = currentArea.getNumOfParkingSlots();
		return $;
	}

	// Return a free parking slot by a given area
	public ParkingSlot getParkingslotByArea(final ParkingArea a) throws ParseException {
		ParkingSlot $ = null;
		for (final ParkingArea currentArea : parkingAreas)
			if (currentArea.getAreaId() == a.getAreaId())
				for (final ParkingSlot currentSlot : currentArea.getFreeSlots())
					$ = currentSlot;
		return $;
	}

	public List<String> getParkingAreasNames() throws ParseException {
		final List<String> $ = new ArrayList<String>();
		for (final ParkingArea currentArea : parkingAreas)
			$.add(currentArea.getName());
		return $;
	}

	public HashMap<String, StickersColor> getParkingAreasColor() throws ParseException {
		final HashMap<String, StickersColor> $ = new HashMap<String, StickersColor>();
		for (final ParkingArea currentArea : parkingAreas)
			$.put(currentArea.getName(), currentArea.getColor());
		return $;
	}

	public HashMap<String, MapLocation> getParkingAreasLocation() throws ParseException {
		final HashMap<String, MapLocation> $ = new HashMap<String, MapLocation>();
		for (final ParkingArea currentArea : parkingAreas)
			$.put(currentArea.getName(), currentArea.getLocation());
		return $;
	}

	// Update the areas array in the DB according to the last update
	private void updateAreasArray() throws ParseException {
		final List<ParseObject> areas = new ArrayList<ParseObject>();
		if (!parkingAreas.isEmpty())
			for (final ParkingArea ¢ : parkingAreas)
				areas.add(¢.getParseObject());

		parseObject.put("areas", areas);
		parseObject.save();
	}

}
