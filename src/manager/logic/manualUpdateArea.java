package manager.logic;

/**
 * @author Inbal Matityahu
 * @since 12.19.16
 * 
 *        This class will save the current given details of a manual update
 */

import java.util.Date;

import data.members.ChangeDuration;
import data.members.StickersColor;

public class manualUpdateArea {
	private int areaId;
	private int slotsAmount;
	private StickersColor demandColor;
	private ChangeDuration duration;
	private Date endDate;
	
	public manualUpdateArea(int areaId, int slotsAmount, StickersColor demandColor, 
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
	

}

