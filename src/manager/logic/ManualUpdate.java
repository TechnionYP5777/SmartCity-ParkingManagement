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

	public ManualUpdate() {
		this.queries = new Queries();
	}

	public void updateArea(int areaId, int slotsAmount, StickersColor demandColor, DurationType t, Date untilDate) {
		ParkingArea givenArea = this.queries.returnArea(areaId);
		if (givenArea == null)
			throw new RuntimeException("demand area does not exist");
		if (givenArea.getNumOfFreeSlots() < slotsAmount)
			throw new RuntimeException("demand amount does not possible");
		if (givenArea.getColor().equals(demandColor))
			throw new RuntimeException("there is no change in colors");
		if (untilDate != null && untilDate.before(new Date()))
			throw new RuntimeException("end time is not in the future");
		if (untilDate == null && t.equals(DurationType.TEMPORARY))
			throw new RuntimeException("temporary without end date");
		(new ManualUpdateArea(this.queries.returnArea(areaId), slotsAmount, demandColor, t, untilDate)).updateArea();
	}
}
