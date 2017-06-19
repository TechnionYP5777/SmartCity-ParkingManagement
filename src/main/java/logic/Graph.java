package logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.parse4j.ParseGeoPoint;
import org.parse4j.ParseObject;

import data.management.DatabaseManager;
import data.management.DatabaseManagerImpl;
import data.members.StickersColor;

/**
 * 
 * @author Toma
 * @since 19.06.2017
 * 
 * The purpose of this class is to implement the logic behind the graph presentation
 */

public class Graph {

	// This method will collect data about price vs. distance
	public static Map<Double, Double> CreatePriceDistanceData(ParseGeoPoint destenation){
		DatabaseManager manager;
		manager = new DatabaseManagerImpl();
		manager.initialize();
		List<ParseObject> allParkingSlot = manager.getAllObjects("ParkingSlot", 600);
		Map<Double, Double> distanceVsPrice = new HashMap<Double,Double>();
		for (ParseObject p : allParkingSlot) {
			StickersColor rank = StickersColor.values()[p.getInt("rank")];
			double distance = Distance(p.getParseGeoPoint("location"), destenation);
			Billing clasicBilling = new BasicBilling();
			double cost = clasicBilling.calculateCost(rank,distance);
			if(distanceVsPrice.containsKey(distance)){
				// if the current distance already exist, don't change it 
				continue;
			}
			distanceVsPrice.put(distance, cost);		
		}
		return distanceVsPrice;
	}
	
	// Calculate the distance between a parking slot, and the destination
	private static double Distance(ParseGeoPoint parkingSlot, ParseGeoPoint destination) {

		double lat1 = parkingSlot.getLatitude();
		double lat2 = destination.getLatitude();
		double lon1 = parkingSlot.getLongitude();
		double lon2 = destination.getLongitude();
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

}
