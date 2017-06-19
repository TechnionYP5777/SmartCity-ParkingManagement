package logic;

import org.parse4j.ParseGeoPoint;

import data.members.ParkingSlot;
import data.members.StickersColor;

public class BasicBilling  implements Billing{

	@Override
	public double calculateCost(StickersColor rank, double distance) {
		return 20+10*rank.ordinal()-distance;
	}
	
	@Override
	public double calculateCostBySlot(ParkingSlot slot, ParseGeoPoint destenation){
		double distance = Distance(slot.getParseGeoPoint(), destenation);
		return calculateCost(slot.getRank(), distance);
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
