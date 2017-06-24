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
		DatabaseManager manager = new DatabaseManagerImpl();
		manager.initialize();
		List<ParseObject> allParkingSlot = manager.getAllObjects("ParkingSlot", 600);
		Map<Double, Double> distanceVsPrice = new HashMap<Double,Double>();
		for (ParseObject p : allParkingSlot) {
			StickersColor rank = StickersColor.values()[p.getInt("rank")];
			double distance = BasicBilling.Distance(p.getParseGeoPoint("location"), destenation);
			if (!distanceVsPrice.containsKey(distance))
				distanceVsPrice.put(distance, (new BasicBilling()).calculateCost(rank, distance));		
		}
		return distanceVsPrice;
	}
}
