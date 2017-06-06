package logic;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.parse4j.ParseGeoPoint;
import org.parse4j.ParseObject;

import data.management.DatabaseManager;
import data.members.ParkingSlot;
import data.members.ParkingSlotStatus;
import data.members.StickersColor;


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
	private int hoursAmunt;
	private DatabaseManager manager;
	
	public class PresentParkingSlot{
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
	
	public void setDestenation(ParseGeoPoint dest){
		this.destenation = dest;
	}
	
	public void setDate (Date date){
		this.dateToPark = date;
	}
	
	public ParkingSlotRequest (ParseGeoPoint destenation,Date date,int hoursAmunt, DatabaseManager manager){
		this.manager = manager;
		this.destenation = destenation;
		this.dateToPark = date;
		this.hoursAmunt = hoursAmunt;
	}
	
	private List<String> noHourCollisionParking(List<ParseObject> tempListOrders){
		List<String> validParking = new ArrayList<String>();
		for(ParseObject p : tempListOrders){
			int orderTime = Integer.parseInt((p.getString("hour")));
			Date orderDate = p.getDate("date");
			if(orderDate.getMonth() == this.dateToPark.getMonth() && orderDate.getDay() == this.dateToPark.getDay()){
				if(orderTime < this.dateToPark.getHours() || orderTime > this.dateToPark.getHours()+this.hoursAmunt){
					validParking.add(p.getString("slotId"));
				}
			}
		}
		
		return validParking;
	}
	
	private static double distance(ParseGeoPoint p1, ParseGeoPoint p2) {

		double lat1 = p1.getLatitude();
		double lat2 = p1.getLatitude();
		double lon1 = p1.getLongitude();
		double lon2 = p1.getLongitude();
		
	    final int R = 6371; // Radius of the earth

	    double latDistance = Math.toRadians(lat2 - lat1);
	    double lonDistance = Math.toRadians(lon2 - lon1);
	    double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
	            + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
	            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	    double distance = R * c * 1000; // convert to meters

	    return distance;
	}
	
	/**
	 * Negative value represent no restriction
	 * @param maxDistance
	 * @param maxCost
	 * @return
	 */
	public List<PresentParkingSlot> getAllAvailableParkingSlot(BillingClass costCalculator){
		List<ParseObject> tempListParkingSlot = manager.getAllObjects("ParkingSlot", 600);
		List<ParseObject> tempListOrders = manager.getAllObjects("Order", 600);
		List<String> validParkings = this.noHourCollisionParking(tempListOrders);
		
		List<PresentParkingSlot> returnList = new ArrayList<ParkingSlotRequest.PresentParkingSlot>();
		for(ParseObject p : tempListParkingSlot){
			String parkingName = p.getString("name");
			if(validParkings.contains(parkingName))
			returnList.add(new PresentParkingSlot(parkingName, 
				costCalculator.calculateCost((StickersColor) p.get("rank"), distance(p.getParseGeoPoint("location"),this.destenation)),
				distance(p.getParseGeoPoint("location"),this.destenation)));
			
		}
		return returnList;
	}
	
}
