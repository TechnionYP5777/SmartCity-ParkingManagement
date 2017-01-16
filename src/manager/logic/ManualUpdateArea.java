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
	
	public ManualUpdateArea(){
	}
	
	public ManualUpdateArea(ParkingArea area, int slotsAmount, StickersColor demandColor, 
			DurationType duration, Date untilDate){
		this.area=area;
		this.demandColor=demandColor;
		this.duration=duration;
		this.slotsAmount=slotsAmount;
		this.endDate=untilDate;
	}
	public ParkingArea getAreaId() {
		return area;
	}
	public void setAreaId(ParkingArea ¢) {
		this.area = ¢;
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
	public DurationType getDuration() {
		return duration;
	}
	public void setDuration(DurationType ¢) {
		this.duration = ¢;
	}
	public Date getUntilDate() {
		return endDate;
	}
	public void setUntilDate(Date untilDate) {
		this.endDate = untilDate;
	}	


	public void updateArea(){
		//update area details
		updateAreaDetails();
		Set<ParkingSlot> slots = area.getSlotsByStatus(ParkingSlotStatus.FREE);
		//change randomly amount of slots in the area to the demand color
		//update slots
		int counter=1;
		for (ParkingSlot ¢: slots){
			if (counter>this.slotsAmount)
				break;
			updateParkingSlot(¢, this.demandColor, this.endDate, this.duration);
			++counter;
		}	
	}
	
	void updateAreaDetails(){
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingArea");
		query.whereEqualTo("areaId", this.area.getAreaId());
		try {

			List<ParseObject> areaList = query.find();
			if (areaList != null && !areaList.isEmpty())
				//update those slots information
				//note- its promise that there are at least slotsAmount of free parkingSlots
				
				areaList.get(0).put("color", this.demandColor.ordinal());
				//check if the duration is permanent or temporary
				if (!this.duration.equals(DurationType.PERMANENTLY))
					//if temporary - insert the given endDate, change color
					areaList.get(0).put("endDate", this.endDate);
				else {
					//if permanent - insert to endDate 31/12/9999, change default color and color
					Date newDate = (new SimpleDateFormat("yyyy-MM-dd")).parse("9999-12-31");
					areaList.get(0).put("endDate", newDate);
					areaList.get(0).put("defaultColor", this.demandColor.ordinal());
				}
				areaList.get(0).save();
		} catch (Exception e) {
			System.out.format("something went wrong looking for areaId: " + this.area.getAreaId());
		}
		
	}

	void updateParkingSlot(ParkingSlot s, StickersColor demandColor, Date endDate, DurationType t){
		ParseQuery<ParseObject> query = ParseQuery.getQuery("ParkingSlot");
		query.whereEqualTo("name", s.getName());
		try {
			List<ParseObject> slotList = query.find();
			if (slotList != null && !slotList.isEmpty()){
				slotList.get(0).put("color", demandColor.ordinal());
				//check if the duration is permanent or temporary
				if (!t.equals(DurationType.PERMANENTLY)){
					//if temporary - insert the given endDate, change color
					slotList.get(0).put("endDate", endDate);
					
				}
				else {
					//if permanent - insert to endDate 31/12/9999, change default color and color
					Date newDate = (new SimpleDateFormat("yyyy-MM-dd")).parse("9999-12-31");
					slotList.get(0).put("endTime", newDate);
					//System.out.println("***after change***");
				}
				slotList.get(0).save();
			}
		}catch (Exception e) {
			System.out.format("something went wrong looking for slot name: " + s.getName());
		}
	}
}

