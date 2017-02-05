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
 *  This class represent all parking areas on Technion's site
 */

public class ParkingAreas extends dbMember {
	private Set<ParkingArea> parkingAreas;

	/* Constructors */
	
	public ParkingAreas(Set<ParkingArea> parkingAreas) throws ParseException{
		DBManager.initialize();
		this.parseObject = new ParseObject("ParkingAreas");
		this.setParkingAreas(parkingAreas);

		this.parseObject.save();
		this.objectId = this.parseObject.getObjectId();
	}

	public ParkingAreas() {
		DBManager.initialize();
		this.parkingAreas = new HashSet<ParkingArea>();
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		try {
			List<ParseObject> areaList = query.find();
			if (areaList == null || areaList.isEmpty())
				throw new RuntimeException("There was a problem - ParkingArea table doesnt found");
			for (ParseObject ¢: areaList)
				this.parkingAreas.add(new ParkingArea(¢));
		}
		catch (ParseException ¢) {
			¢.printStackTrace();
		}
	}

	/* Getters */
	
	public Set<ParkingArea> getParkingAreas() {
		return parkingAreas;
	}

	/* Setters */
	
	public void setParkingAreas(Set<ParkingArea> ¢) throws ParseException {
		this.parkingAreas = ¢;
		updateAreasArray();
	}
	
	/* Methods */
	
	public void addParkingArea(ParkingArea ¢) throws ParseException {
		this.parkingAreas.add(¢);

		updateAreasArray();
	}

	public void removeParkingArea(ParkingArea a) throws ParseException {

		// search if parkingArea exist
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("areaId", a.getAreaId());
		if (query.find().size() != 1 || this.parkingAreas == null) {
				System.out.format("The area %d doest exist", a.getAreaId());
				return;
		}			

		// remove the are
		for (ParkingArea ¢ : this.parkingAreas)
			if (¢.objectId.equals(a.objectId))
				this.parkingAreas.remove(¢);
		a.removeParkingAreaFromDB();
		updateAreasArray();
	}

	// Return num of taken parking slots by a given area
	public int getNumOfTakenByArea(ParkingArea a) {
		int $ = 0;
		for (ParkingArea currentArea : this.parkingAreas)
			if (currentArea.getAreaId()==(a.getAreaId()))
				$ = currentArea.getNumOfTakenSlots();
		return $;
	}

	// Return num of free parking slots by given area
	public int getNumOfFreeByArea(ParkingArea a) {
		int $ = 0;
		for (ParkingArea currentArea : this.parkingAreas)
			if (currentArea.getAreaId()==(a.getAreaId()))
				$ = currentArea.getNumOfFreeSlots();
		return $;
	}

	// Return num of free parking slots
	public int getNumOfFreeSlots() {
		int $ = 0;
		for (ParkingArea currentArea : this.parkingAreas)
			$ += currentArea.getNumOfFreeSlots();
		return $;
	}

	// Return num of taken parking slots
	public int getNumOfTakenSlots() {
		int $ = 0;
		for (ParkingArea currentArea : this.parkingAreas)
			$ += currentArea.getNumOfTakenSlots();
		return $;
	}


	// Return parking slots per area
	public int getNumOfSlotsByArea(ParkingArea a) {
		int $ = 0;
		for (ParkingArea currentArea : this.parkingAreas)
			if (currentArea.getAreaId()==(a.getAreaId()))
				$ = currentArea.getNumOfParkingSlots();
		return $;
	}

	
	// Return a free parking slot by a given area
	public ParkingSlot getParkingslotByArea(ParkingArea a) throws ParseException {
		ParkingSlot $ = null;
		for (ParkingArea currentArea : this.parkingAreas)
			if (currentArea.getAreaId()==(a.getAreaId()))
				for (ParkingSlot currentSlot : currentArea.getFreeSlots())
					$ = currentSlot;
		return $;
	}
	
	public List<String> getParkingAreasNames() throws ParseException{
		List<String> $=new ArrayList<String>();
		for (ParkingArea currentArea : this.parkingAreas)
			$.add(currentArea.getName());
		return $;
	}
	
	public HashMap<String,StickersColor> getParkingAreasColor() throws ParseException{
		 HashMap<String,StickersColor> $ = new  HashMap<String,StickersColor>();
		for (ParkingArea currentArea : this.parkingAreas)
			$.put(currentArea.getName(),currentArea.getColor());
		return $;
	}
	
	public HashMap<String,MapLocation> getParkingAreasLocation() throws ParseException{
		 HashMap<String,MapLocation> $ = new  HashMap<String,MapLocation>();
		for (ParkingArea currentArea : this.parkingAreas)
			$.put(currentArea.getName(),currentArea.getLocation());
		return $;
	}
	
	// Update the areas array in the DB according to the last update
		private void updateAreasArray() throws ParseException {
			List<ParseObject> areas = new ArrayList<ParseObject>();
			if (!this.parkingAreas.isEmpty())
				for (ParkingArea ¢ : this.parkingAreas)
					areas.add(¢.getParseObject()); 

			this.parseObject.put("areas", areas);
			this.parseObject.save();
		}

}
