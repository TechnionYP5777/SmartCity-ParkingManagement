package data.members;

import java.util.Set;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

import data.management.DBManager;

/**
 * @author Inbal Matityahu
 * @since 12.11.16
 * 
 *        This class represent a parking area inside the Technion
 */

public class ParkingArea {

	private StickersColor color;
	private int areaId;
	private int numOfParkingSlots;
	private int numOfFreeSlots;
	private int numOfTakenSlots;
	private Set<ParkingSlot> parkingSlots;
	private Set<ParkingSlot> freeSlots;
	private Set<ParkingSlot> takenSlots;
	private ParseObject area;
	
	public ParkingArea(){
		
	}

	public ParkingArea(StickersColor color, int areaId, int free, int taken, int total, Set<ParkingSlot> parkingSlots,
			Set<ParkingSlot> freeSlots, Set<ParkingSlot> takenSlots) throws ParseException {
		// will populate private fields according to the data stored in the DB
		DBManager.initialize();
		this.area = new ParseObject("ParkingArea");
		this.setAreaId(areaId);
		this.setColor(color);
		this.setFreeSlots(freeSlots);
		this.setNumOfFreeSlots(free);
		this.setNumOfParkingSlots(total);
		this.setNumOfTakenSlots(taken);
		this.setParkingSlots(parkingSlots);
		this.setTakenSlots(takenSlots);
		area.save();
	}

	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
		this.area.put("areaId", areaId);
	}

	public int getNumOfParkingSlots() {
		return numOfParkingSlots;
	}

	public void setNumOfParkingSlots(int numOfParkingSlots) {
		this.numOfParkingSlots = numOfParkingSlots;
		this.area.put("numOfParkingSlots", numOfParkingSlots);
	}

	public Set<ParkingSlot> getParkingSlots() {
		return parkingSlots;
	}

	public void setParkingSlots(Set<ParkingSlot> ¢) {
		this.parkingSlots = ¢;
		this.area.put("parkingSlots", ¢);
	}

	public int getNumOfFreeSlots() {
		return numOfFreeSlots;
	}

	public void setNumOfFreeSlots(int numOfFreeSlots) {
		this.numOfFreeSlots = numOfFreeSlots;
		this.area.put("numOfFreeSlots", numOfFreeSlots);
	}

	public int getNumOfTakenSlots() {
		return numOfTakenSlots;
	}

	public void setNumOfTakenSlots(int numOfTakenSlots) {
		this.numOfTakenSlots = numOfTakenSlots;
		this.area.put("numOfTakenSlots", numOfTakenSlots);
	}

	public Set<ParkingSlot> getFreeSlots() {
		return freeSlots;
	}

	public void setFreeSlots(Set<ParkingSlot> freeSlots) {
		this.freeSlots = freeSlots;
		this.area.put("freeSlots", freeSlots);
	}

	public Set<ParkingSlot> getTakenSlots() {
		return takenSlots;
	}

	public void setTakenSlots(Set<ParkingSlot> takenSlots) {
		this.takenSlots = takenSlots;
		this.area.put("takenSlots", takenSlots);
	}

	public StickersColor getColor() {
		return color;
	}

	public void setColor(StickersColor ¢) {
		this.color = ¢;
		this.area.put("color", ¢);
	}

	/*
	 * add new parking slot to this area. notice - this new slot will count as
	 * free slot, and therefore increase the amount of free slots in this area,
	 * and the total count of parking
	 */
	public void addParkingSlot(ParkingSlot ¢) {
		this.getFreeSlots().add(¢);
		this.getParkingSlots().add(¢);
		this.setNumOfFreeSlots(this.getNumOfFreeSlots() + 1);
		this.setNumOfParkingSlots(this.getNumOfParkingSlots() + 1);

		this.area.put("freeSlots", this.getFreeSlots());
		this.area.put("parkingSlot", this.getParkingSlots());
		this.area.put("numOfFreeSlots", this.getNumOfFreeSlots());
		this.area.put("numOfParkingSlots", this.getNumOfParkingSlots());
	}

	/*
	 * change a specific parking slot status from taken to free
	 */
	public void changeTakenToFree(ParkingSlot s) {
		//search if parking slot is exist
		if (!this.getTakenSlots().contains(s))
			return;

		// search the parkingSlot objectId from parse
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingSlot");
		query.whereEqualTo("name", s.getName());
		try {
			if (query.find().size() != 1) {
				System.out.format("Something went worng while searching for %d", s.getName());
				return;
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}

		this.getTakenSlots().remove(s);
		this.setNumOfTakenSlots(this.getNumOfTakenSlots() - 1);
		s.setStatus(ParkingSlotStatus.FREE);
		this.getFreeSlots().add(s);
		this.setNumOfFreeSlots(this.getNumOfFreeSlots() + 1);

		
		this.area.put("takenSlot", this.getTakenSlots());
		this.area.put("numOfTakenSlots", this.getNumOfTakenSlots());
		this.area.put("freeSlots", this.getFreeSlots());
		this.area.put("numOfFreeSlots", this.getNumOfFreeSlots());
		
	}

	/*
	 * change a specific parking slot status from free to taken
	 */
	public void changeFreeToTaken(ParkingSlot s, User u) {
		//search if parking slot and user are exist
		if (!this.getFreeSlots().contains(s))
			return;	
		// search the parkingSlot objectId from parse
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingSlot");
		query.whereEqualTo("name", s.getName());
		try {
			if (query.find().size() != 1) {
				System.out.format("Something went worng while searching for %d", s.getName());
				return;
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}
		
		//search the given user
		ParseQuery<ParseObject> query2 = ParseQuery.getQuery("User");
		query2.whereEqualTo("carNumber", u.getCarNumber());
		try {
			if (query2.find().size() != 1) {
				System.out.format("Something went worng while searching for %d", u.getCarNumber());
				return;
			}
			
		} catch (ParseException e) {
			e.printStackTrace();
			return;
		}
		
		this.getFreeSlots().remove(s);
		this.setNumOfFreeSlots(this.getNumOfFreeSlots() - 1);
		s.setStatus(ParkingSlotStatus.TAKEN);
		u.setCurrentParking(s);
		this.getTakenSlots().add(s);
		this.setNumOfTakenSlots(this.getNumOfTakenSlots() + 1);
		
		this.area.put("takenSlot", this.getTakenSlots());
		this.area.put("numOfTakenSlots", this.getNumOfTakenSlots());
		this.area.put("freeSlots", this.getFreeSlots());
		this.area.put("numOfFreeSlots", this.getNumOfFreeSlots());
		
	}
}
