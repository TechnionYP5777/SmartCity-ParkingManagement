package logic;
import java.util.*;

public class PresentOrder implements Comparator<PresentOrder>,Comparable<PresentOrder> {
	
	private String parkingSlotId;
	private Date startTime;
	private Date finishTime;
	private double price;
	private String orderId;
	
	public PresentOrder(String parkingSlotId, Date startTime, Date finishTime, double price,String orderId){
		this.parkingSlotId = parkingSlotId;
		this.startTime = startTime;
		this.finishTime = finishTime;
		this.price = price;
		this.orderId = orderId;
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
	public void setOrderId(String orderId){
		this.orderId = orderId;
	}
	public String getOrderId(){
		return this.orderId;
	}
	public boolean isLaterThan(PresentOrder order){
		if(order == null)
			return true;
		return this.startTime.after(order.getStartTime());
	}
	
	@Override
	public int compare(PresentOrder o1, PresentOrder o2) {
		if(!o1.isLaterThan(o2))
			return 1;
		return -1;
	}
	@Override
	public int compareTo(PresentOrder o) {
		return compare(this,o);
	}
}
