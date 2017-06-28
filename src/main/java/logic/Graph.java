package logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.parse4j.ParseGeoPoint;
import org.parse4j.ParseObject;

import data.management.DatabaseManager;
import data.management.DatabaseManagerImpl;
import data.members.StickersColor;
import util.Distance;

/**
 * 
 * @author Toma
 * @since 19.06.2017
 * 
 * The purpose of this class is to implement the logic behind the graph presentation
 */

public class Graph {
	private DatabaseManager manager;
	
	//in default constructor. create the normal dbm
	public Graph(){
		manager = new DatabaseManagerImpl();
	}
	
	//for testing, use a mocking dbm
	public Graph(DatabaseManager dbm){
		manager = dbm;
	}
	
	// This method will collect data about price vs. distance
	public Map<Double, Double> CreatePriceDistanceData(ParseGeoPoint destenation){
		manager.initialize();
		List<ParseObject> allParkingSlot = manager.getAllObjects("ParkingSlot", 600);
		Map<Double, Double> distanceVsPrice = new HashMap<Double,Double>();
		for (ParseObject p : allParkingSlot) {
			StickersColor rank = StickersColor.values()[p.getInt("rank")];
			double distance = Distance.AirDistance(p.getParseGeoPoint("location"), destenation);
			if (!distanceVsPrice.containsKey(distance))
				distanceVsPrice.put(distance, (new BasicBilling()).calculateCost(rank, distance));		
		}
		return distanceVsPrice;
	}
}
