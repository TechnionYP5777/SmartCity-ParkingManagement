package logic;

import org.parse4j.ParseGeoPoint;

import data.members.ParkingSlot;
import data.members.StickersColor;

public class BasicBilling  implements Billing{

	@Override
	public double calculateCost(StickersColor rank, double distance) {
		return 10 * rank.ordinal() + 20-distance;
	}
	
	@Override
	public double calculateCostBySlot(ParkingSlot s, ParseGeoPoint destenation){
		return calculateCost(s.getRank(), Distance(s.getParseGeoPoint(), destenation));
	}
	
	// Calculate the distance between a parking slot, and the destination
	public static double Distance(ParseGeoPoint parkingSlot, ParseGeoPoint destination) {

		double lat1 = parkingSlot.getLatitude(), lat2 = destination.getLatitude(), lon1 = parkingSlot.getLongitude(),
				lon2 = destination.getLongitude();
		final int R = 6371; // Radius of the earth

	    double latDistance = Math.toRadians(lat2 - lat1), lonDistance = Math.toRadians(lon2 - lon1),
				a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2) * Math.cos(Math.toRadians(lat1))
						* Math.cos(Math.toRadians(lat2));
	    return Math.abs(4990000 - 2000 * R * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))) ;
	}

}
