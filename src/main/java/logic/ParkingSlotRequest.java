package logic;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.parse4j.ParseException;
import org.parse4j.ParseGeoPoint;
import org.parse4j.ParseObject;


import data.management.DatabaseManager;
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
	
	private List<String> noHourCollisionParking(List<ParseObject> tempListOrders, List<ParseObject> tempListParkingSlot){
		List<String> validParking = new ArrayList<String>();
		for(ParseObject p : tempListParkingSlot){
			validParking.add(p.getString("name"));
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.dateToPark);
		int wantedStartingHour = cal.get(Calendar.HOUR_OF_DAY);
		int wantedStartingQuarter = cal.get(Calendar.MINUTE);
		int wantedStartTime = wantedStartingHour*4+wantedStartingQuarter;
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");

		for(ParseObject p : tempListOrders){
			cal.setTime(this.dateToPark);
			int orderStartTime = p.getInt("hour");
			int orderTimeAmount = p.getInt("hoursAmount");
			int orderEndTime = orderStartTime+orderTimeAmount;

			Boolean noValidParkingCondition = (orderStartTime == wantedStartTime);
			noValidParkingCondition = Boolean.logicalOr(noValidParkingCondition,(orderEndTime)%(24*4) == (wantedStartTime+this.hoursAmunt)%(24*4));
			noValidParkingCondition = Boolean.logicalOr(noValidParkingCondition,orderStartTime<wantedStartTime && (orderEndTime)%(24*4) > wantedStartTime);
			noValidParkingCondition = Boolean.logicalOr(noValidParkingCondition, wantedStartTime<orderStartTime && (wantedStartTime+this.hoursAmunt)%(24*4) > orderStartTime);

			String orderDate = p.getString("date");
			if(formatDate.format(cal.getTime()).equals(orderDate)){
				if(noValidParkingCondition){
					validParking.remove(p.getString("slotId"));
				}
			}
		}
		
		return validParking;
	}
	
	private static double distance(ParseGeoPoint p1, ParseGeoPoint p2) {

		double lat1 = p1.getLatitude();
		double lat2 = p2.getLatitude();
		double lon1 = p1.getLongitude();
		double lon2 = p2.getLongitude();
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
	public List<PresentParkingSlot> getAllAvailableParkingSlot(Billing costCalculator){
		List<ParseObject> tempListParkingSlot = manager.getAllObjects("ParkingSlot", 600);
		List<ParseObject> tempListOrders = manager.getAllObjects("Order", 600);
		List<String> validParkings = this.noHourCollisionParking(tempListOrders, tempListParkingSlot);

		List<PresentParkingSlot> returnList = new ArrayList<PresentParkingSlot>();
		for(ParseObject p : tempListParkingSlot){
			String parkingName = p.getString("name");
			if(validParkings.contains(parkingName)){
				ParseGeoPoint location = p.getParseGeoPoint("location");
				StickersColor rank = StickersColor.values()[p.getInt("rank")];
				double ratting = 0; // need to add rating to parkingSlot
				returnList.add(
						new PresentParkingSlot(parkingName, location.getLatitude(),location.getLongitude(),
								costCalculator.calculateCost(rank, distance(location,this.destenation)),
								distance(p.getParseGeoPoint("location"),this.destenation),ratting)
						);
			}

			
		}
		return returnList;
	}

	public Boolean orderParkingSlot(String driverID, String slotID) throws ParseException, InterruptedException{
		List<ParseObject> tempListOrders = manager.getAllObjects("Order", 600);
		if(!isParkingValid(slotID, tempListOrders)) return new Boolean(false);
		
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(dateToPark); // sets calendar time/date
	    cal.add(Calendar.HOUR_OF_DAY, hoursAmunt/4);
	    cal.add(Calendar.MINUTE, hoursAmunt%4);
		Date endTime =cal.getTime();
		new Order(driverID, slotID, dateToPark, endTime, manager);
		return new Boolean(true);
		
	}

	private boolean isParkingValid(String slotID, List<ParseObject> tempListOrders) {
		for(ParseObject p : tempListOrders){
			if(slotID.equals(p.getString("slotId"))) return false;
		}
		return true;
	}

}
