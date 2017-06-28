package logic;

import org.parse4j.ParseGeoPoint;

import data.members.ParkingSlot;
import data.members.StickersColor;
import util.Distance;

public class BasicBilling  implements Billing{

	private int pricingIndex = 500;
	
	@Override
	public double calculateCost(StickersColor rank, double distance) {
		return pricingIndex*rank.ordinal() - distance;
	}
	
	@Override
	public double calculateCostBySlot(ParkingSlot s, ParseGeoPoint destenation){
		return calculateCost(s.getRank(), Distance.AirDistance(s.getParseGeoPoint(), destenation));
	}
}
