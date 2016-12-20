package manager.logic;

/**
 * @author Inbal Matityahu
 * @since 12.19.16
 * 
 *        This class deals with manual update of parkingSlots.
 */

import java.util.Date;

import data.members.DurationType;
import data.members.ParkingArea;
import data.members.StickersColor;

public class ManualUpdate {
	private Queries queries;
	
	public void updateArea(int areaId, int slotsAmount, StickersColor demandColor, 
			DurationType d, Date untilDate){

		//check if the area is exist
		ParkingArea givenArea = this.queries.returnArea(areaId);
		if (givenArea == null)
			System.out.println("demand area does not exist");
		//check if slots amounts is makes sense
		else if (givenArea.getNumOfFreeSlots() < slotsAmount)
			System.out.println("demand amount does not possible");
		//check if the demand color is different from the original one
		else if (givenArea.getColor().equals(demandColor))
			System.out.println("there is no change in colors");
		//check if the end day is in the future
		else if (untilDate == null || untilDate.before((new Date())))
			System.out.println("end time is not in the future");
		else
			//update
			(new ManualUpdateArea(areaId, slotsAmount, demandColor, d, untilDate)).updateArea();
	}
}
