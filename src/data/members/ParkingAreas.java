package data.members;

import java.util.Set;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

import data.management.DBManager;

import java.util.ArrayList;
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
				this.parkingAreas.add((new ParkingArea(¢)));
		}
		catch (ParseException e) {
			e.printStackTrace();
		}
	}

	/* Getters */
	
	public Set<ParkingArea> getParkingAreas() {
		return parkingAreas;
	}

	/* Setters */
	
	public void setParkingAreas(Set<ParkingArea> as) throws ParseException {
		this.parkingAreas = as;
		updateAreasArray();
	}
	
	/* Methods */
	
	public void addParkingArea(ParkingArea a) throws ParseException {
		this.parkingAreas.add(a);

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
		for (ParkingArea p : this.parkingAreas)
			if (p.objectId.equals(a.objectId))
				this.parkingAreas.remove(p);
		a.removeParkingAreaFromDB();
		updateAreasArray();
	}

	// Return num of taken parking slots by a given area
	public int getNumOfTakenByArea(ParkingArea a) {
		int s = 0;
		for (ParkingArea currentArea : this.parkingAreas)
			if (currentArea.getAreaId()==(a.getAreaId()))
				s = currentArea.getNumOfTakenSlots();
		return s;
	}

	// Return num of free parking slots by given area
	public int getNumOfFreeByArea(ParkingArea a) {
		int s = 0;
		for (ParkingArea currentArea : this.parkingAreas)
			if (currentArea.getAreaId()==(a.getAreaId()))
				s = currentArea.getNumOfFreeSlots();
		return s;
	}

	// Return num of free parking slots
	public int getNumOfFreeSlots() {
		int s = 0;
		for (ParkingArea currentArea : this.parkingAreas)
			s += currentArea.getNumOfFreeSlots();
		return s;
	}

	// Return num of taken parking slots
	public int getNumOfTakenSlots() {
		int s = 0;
		for (ParkingArea currentArea : this.parkingAreas)
			s += currentArea.getNumOfTakenSlots();
		return s;
	}


	// Return parking slots per area
	public int getNumOfSlotsByArea(ParkingArea a) {
		int s = 0;
		for (ParkingArea currentArea : this.parkingAreas)
			if (currentArea.getAreaId()==(a.getAreaId()))
				s = currentArea.getNumOfParkingSlots();
		return s;
	}

	
	// Return a free parking slot by a given area
	public ParkingSlot getParkingslotByArea(ParkingArea a) throws ParseException {
		ParkingSlot s = null;
		for (ParkingArea currentArea : this.parkingAreas)
			if (currentArea.getAreaId()==(a.getAreaId()))
				for (ParkingSlot currentSlot : currentArea.getFreeSlots())
					s = currentSlot;
		return s;
	}
	
	public List<String> getParkingAreasNames() throws ParseException{
		List<String> s=new ArrayList<String>();
		for (ParkingArea currentArea : this.parkingAreas)
			s.add(currentArea.getName());
		return s;
	}
	
	// Update the areas array in the DB according to the last update
		private void updateAreasArray() throws ParseException {
			List<ParseObject> areas = new ArrayList<ParseObject>();
			if (!this.parkingAreas.isEmpty())
				for (ParkingArea p : this.parkingAreas)
					areas.add(p.getParseObject()); 

			this.parseObject.put("areas", areas);
			this.parseObject.save();
		}

}
