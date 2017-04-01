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
	private final Queries queries;

	public ManualUpdate() {
		queries = new Queries();
	}

	public void updateArea(final int areaId, final int slotsAmount, final StickersColor demandColor, final DurationType t, final Date untilDate) {
		final ParkingArea givenArea = queries.returnArea(areaId);
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
		new ManualUpdateArea(queries.returnArea(areaId), slotsAmount, demandColor, t, untilDate).updateArea();
	}
}
