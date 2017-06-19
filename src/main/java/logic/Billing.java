package logic;

import org.parse4j.ParseGeoPoint;

import data.members.ParkingSlot;
import data.members.StickersColor;

public interface Billing {

	public double calculateCost(StickersColor rank, double distance);
	
	public double calculateCostBySlot(ParkingSlot slot, ParseGeoPoint destenation);
}
