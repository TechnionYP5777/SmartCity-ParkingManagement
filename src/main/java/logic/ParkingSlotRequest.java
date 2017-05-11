package logic;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.parse4j.ParseGeoPoint;

import data.members.ParkingSlot;


/**
 * 
 * @author assaflu
 * @since 11.5.2017
 * 
 * The purpose of this class is to implement the logic behind the parking slot request screen
 */

public class ParkingSlotRequest {

	private ParseGeoPoint destenation;
	private Date dateToPark;
	
	public class PresentParkingSlot{
		private ParkingSlot slot;
		private Double cost;
		private Double distance;
		private List<PresentParkingSlot> allSlots = null;
		
		 public PresentParkingSlot(ParkingSlot slot, Double cost, Double distance){
			 this.slot = slot;
			 this.cost = cost;
			 this.distance = distance;
		 }
		
		 public Double getCost(){
			return cost;
		 }
		 
		 public Double getDistance(){
			return distance;
		 }
		 
		 public ParkingSlot getSlot(){
			return slot;
		 }
	}
	
	public void setDestenation(ParseGeoPoint dest){
		this.destenation = dest;
	}
	
	public void setDate (Date date){
		this.dateToPark = date;
	}
	
	public ParkingSlotRequest (ParseGeoPoint destenation,Date date){
		this.destenation = destenation;
		this.dateToPark = date;
	}
	
	/**
	 * Negative value represent no restriction
	 * @param maxDistance
	 * @param maxCost
	 * @return
	 */
	public List<PresentParkingSlot> getAllAvailableParkingSlot(Double maxDistance, Double maxCost){
		return null;
		//TODO: need to implement
	}
	
}
