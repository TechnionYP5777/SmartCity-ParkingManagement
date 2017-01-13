package manager.logic;

import java.util.Set;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

import data.management.DBManager;
import data.members.ParkingArea;
import data.members.ParkingSlot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author Inbal Matityahu
 * @since 12.12.16
 * 
 *        This class represent all parking areas on Technion's site
 */

public class ParkingAreas {
	private Set<ParkingArea> parkingAreas;

	public ParkingAreas(Set<ParkingArea> parkingAreas){
		this.parkingAreas = new HashSet<ParkingArea>();
		this.parkingAreas = parkingAreas;
	}

	public Set<ParkingArea> getParkingAreas() {
		return parkingAreas;
	}

	public void setParkingAreas(Set<ParkingArea> ¢) {
		this.parkingAreas = ¢;
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

	public void addParkingArea(ParkingArea newParkingArea) {
		this.parkingAreas.add(newParkingArea);
		
		//TODO: add newParking to DB
	}

	public void removeParkingArea(ParkingArea a) {

		// search if parkingArea exist
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("areaId", a.getAreaId());
		try {
			if (query.find().size() != 1) {
				System.out.format("Something went worng while searching for %d", a.getAreaId());
				return;
			//TODO: remove a from DB
			}

		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}

		this.parkingAreas.remove(a);
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
		for (ParkingArea currentArea : this.parkingAreas){
			$.add(currentArea.getName());
		}
		return $;
	}

}
