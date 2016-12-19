package manager.logic;

/**
 * @author Inbal Matityahu
 * @since 12.19.16
 * 
 *        This class will save the current given details of a manual update
 */

import java.util.Date;
import java.util.List;

import org.parse4j.ParseException;
import org.parse4j.ParseObject;
import org.parse4j.ParseQuery;

import data.members.ChangeDuration;
import data.members.StickersColor;

public class ManualUpdateArea {
	private int areaId;
	private int slotsAmount;
	private StickersColor demandColor;
	private ChangeDuration duration;
	private Date endDate;
	
	public ManualUpdateArea(int areaId, int slotsAmount, StickersColor demandColor, 
			ChangeDuration duration, Date untilDate){
		this.areaId=areaId;
		this.demandColor=demandColor;
		this.duration=duration;
		this.slotsAmount=slotsAmount;
		this.endDate=untilDate;
	}
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	public int getSlotsAmount() {
		return slotsAmount;
	}
	public void setSlotsAmount(int slotsAmount) {
		this.slotsAmount = slotsAmount;
	}
	public StickersColor getDemandColor() {
		return demandColor;
	}
	public void setDemandColor(StickersColor demandColor) {
		this.demandColor = demandColor;
	}
	public ChangeDuration getDuration() {
		return duration;
	}
	public void setDuration(ChangeDuration ¢) {
		this.duration = ¢;
	}
	public Date getUntilDate() {
		return endDate;
	}
	public void setUntilDate(Date untilDate) {
		this.endDate = untilDate;
	}	


	public void updateArea(){
		//get area by areaId
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("areaId", this.areaId);
		try {

			List<ParseObject> areaList = query.find();
			if (areaList != null && !areaList.isEmpty())
				//change randomly amount of slots in the area to the demand color
				for (int ¢=0; ¢<this.slotsAmount; ++¢){
					//update those slots information
					//note- its promise that there are at least slotsAmount of free parkingSlots
					areaList.get(¢).put("color", this.demandColor);
					//check if the duration is permanent or temporary
					if (!this.duration.equals(ChangeDuration.PERMANENTLY))
						//if temporary - insert the given endDate, change color
						areaList.get(¢).put("endDate", this.endDate);
					else {
						//if permanent - insert to endDate null, change default color and color
						areaList.get(¢).put("endDate", null);
						areaList.get(¢).put("defaultColor", this.demandColor);
					}
					areaList.get(¢).save();
				}	

		} catch (ParseException e) {
			System.out.format("something went wrong looking for areaId: " + this.areaId);
		}
		
	}
}

