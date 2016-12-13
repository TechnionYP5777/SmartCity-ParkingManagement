package manager.logic;

import data.members.ParkingArea;
import data.members.StickerType;

/**
 * @author Inbal Matityahu
 * @since 12.13.16
 * 
 *        This class represent an info about a given area.
 *        we will use this class, when clicking on a parking area in the map
 */

public class SelectAnArea {
	private Queries queries;

	public Queries getQueries() {
		return queries;
	}

	public void setQueries(Queries queries) {
		this.queries = queries;
	}

	//return the number of free slots in a given area
	public int getNumOfFreeSlotsPerArea(int areaId){	
		ParkingArea currentArea = this.queries.returnArea(areaId);
		if (currentArea!=null){
			return this.queries.getNumOfFreeByArea(currentArea);
		}
		return -1;
	}
	
	//return the number of taken slots in a given area
	public int getNumOfTakenSlotsPerArea(int areaId){	
		ParkingArea currentArea = this.queries.returnArea(areaId);
		if (currentArea!=null){
			return this.queries.getNumOfTakenByArea(currentArea);
		}
		return -1;
	}
	
	//return the total number of slots in a given area
	public int getTotalNumOfSlotsPerArea(int areaId){	
		ParkingArea currentArea = this.queries.returnArea(areaId);
		if (currentArea!=null){
			return currentArea.getNumOfParkingSlots();
		}
		return -1;
	}
	
	//return the color of slots in a given area
	public StickerType getColorOfArea(int areaId){
		ParkingArea currentArea = this.queries.returnArea(areaId);
		if (currentArea!=null){
			return currentArea.getColor();
		}
		return null;
	}
}
