package logic;

import org.parse4j.ParseGeoPoint;

import data.members.ParkingSlot;
import data.members.StickersColor;

public class UniformBilling implements Billing{

	@Override
	public double calculateCost(StickersColor rank, double distance) {
		return 20;
	}

	@Override
	public double calculateCostBySlot(ParkingSlot slot, ParseGeoPoint destenation){
		return 20;
	}
}
