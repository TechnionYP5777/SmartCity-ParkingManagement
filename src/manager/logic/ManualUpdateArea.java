package manager.logic;

import java.text.SimpleDateFormat;

/**
 * @author Inbal Matityahu
 * @since 12.19.16
 * 
 *        This class will save the current given details of a manual update
 */

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

import data.members.DurationType;
import data.members.ParkingArea;
import data.members.ParkingSlot;
import data.members.ParkingSlotStatus;
import data.members.StickersColor;

public class ManualUpdateArea {
	private ParkingArea area;
	private int slotsAmount;
	private StickersColor demandColor;
	private DurationType duration;
	private Date endDate;

	public ManualUpdateArea() {
	}

	public ManualUpdateArea(final ParkingArea area, final int slotsAmount, final StickersColor demandColor, final DurationType duration,
			final Date untilDate) {
		this.area = area;
		this.demandColor = demandColor;
		this.duration = duration;
		this.slotsAmount = slotsAmount;
		endDate = untilDate;
	}

	public ParkingArea getAreaId() {
		return area;
	}

	public void setAreaId(final ParkingArea ¢) {
		area = ¢;
	}

	public int getSlotsAmount() {
		return slotsAmount;
	}

	public void setSlotsAmount(final int slotsAmount) {
		this.slotsAmount = slotsAmount;
	}

	public StickersColor getDemandColor() {
		return demandColor;
	}

	public void setDemandColor(final StickersColor demandColor) {
		this.demandColor = demandColor;
	}

	public DurationType getDuration() {
		return duration;
	}

	public void setDuration(final DurationType ¢) {
		duration = ¢;
	}

	public Date getUntilDate() {
		return endDate;
	}

	public void setUntilDate(final Date untilDate) {
		endDate = untilDate;
	}

	public void updateArea() {
		// update area details
		updateAreaDetails();
		final Set<ParkingSlot> slots = area.getSlotsByStatus(ParkingSlotStatus.FREE);
		// change randomly amount of slots in the area to the demand color
		// update slots
		int counter = 1;
		for (final ParkingSlot ¢ : slots) {
			if (counter > slotsAmount)
				break;
			updateParkingSlot(¢, demandColor, endDate, duration);
			++counter;
		}
	}

	void updateAreaDetails() {
		final ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("areaId", area.getAreaId());
		try {

			final List<ParseObject> areaList = query.find();
			if (areaList != null && !areaList.isEmpty())
				// update those slots information
				// note- its promise that there are at least slotsAmount of free
				// parkingSlots

				areaList.get(0).put("color", demandColor.ordinal());
			// check if the duration is permanent or temporary
			if (!duration.equals(DurationType.PERMANENTLY))
				// if temporary - insert the given endDate, change color
				areaList.get(0).put("endDate", endDate);
			else {
				areaList.get(0).put("endDate", new SimpleDateFormat("yyyy-MM-dd").parse("9999-12-31"));
				areaList.get(0).put("defaultColor", demandColor.ordinal());
			}
			areaList.get(0).save();
		} catch (final Exception e) {
			System.out.format("something went wrong looking for areaId: " + area.getAreaId());
		}

	}

	void updateParkingSlot(final ParkingSlot s, final StickersColor demandColor, final Date endDate, final DurationType t) {
		final ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingSlot");
		query.whereEqualTo("name", s.getName());
		try {
			final List<ParseObject> slotList = query.find();
			if (slotList != null && !slotList.isEmpty()) {
				slotList.get(0).put("color", demandColor.ordinal());
				// check if the duration is permanent or temporary
				if (!t.equals(DurationType.PERMANENTLY))
					slotList.get(0).put("endDate", endDate);
				else
					slotList.get(0).put("endTime", new SimpleDateFormat("yyyy-MM-dd").parse("9999-12-31"));
				slotList.get(0).save();
			}
		} catch (final Exception e) {
			System.out.format("something went wrong looking for slot name: " + s.getName());
		}
	}
}
