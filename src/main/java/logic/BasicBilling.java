package logic;

import org.parse4j.ParseGeoPoint;

import data.members.ParkingSlot;
import data.members.StickersColor;
import util.Distance;

public class BasicBilling  implements Billing{
	
	@Override
	public double calculateCost(StickersColor rank, double distance) {
		return 20 + rank.ordinal() - distance/100;
	}
	
	@Override
	public double calculateCostBySlot(ParkingSlot s, ParseGeoPoint destenation){
		return calculateCost(s.getRank(), Distance.AirDistance(s.getParseGeoPoint(), destenation));
	}
}
