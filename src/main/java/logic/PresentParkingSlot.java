package logic;

public class PresentParkingSlot {
	private String slotName;
	private Double cost;
	private Double distance;
	
	 public PresentParkingSlot(String slotName, Double cost, Double distance){
		 this.slotName = slotName;
		 this.cost = cost;
		 this.distance = distance;
	 }
	
	 public Double getCost(){
		return cost;
	 }
	 
	 public Double getDistance(){
		return distance;
	 }
	 
	 public String getSlotName(){
		return slotName;
	 }
}
