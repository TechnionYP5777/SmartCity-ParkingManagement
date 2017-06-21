package logic;
import java.util.*;

public class PresentOrder {
	
	private String parkingSlotId;
	private Date startTime;
	private Date finishTime;
	private double price;
	
	public PresentOrder(String parkingSlotId, Date startTime, Date finishTime, double price){
		this.parkingSlotId = parkingSlotId;
		this.startTime = startTime;
		this.finishTime = finishTime;
		this.price = price;
	}
	public String getParkingSlotId(){
		return this.parkingSlotId;
	}
	public Date getStartTime(){
		return this.startTime;
	}
	public Date getFinishTime(){
		return this.finishTime;
	}
	public double getPrice(){
		return this.price;
	}
	public void setParkingSlotId(String parkingSlotId){
		this.parkingSlotId = parkingSlotId;
	}
	public void setStartTime(Date startTime){
		this.startTime = startTime;
	}
	public void setFinishTime(Date finishTime){
		this.finishTime = finishTime;
	}
	public void setPrice(double price){
		this.price = price;
	}
}
