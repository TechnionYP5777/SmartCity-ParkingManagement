package logic;

import data.members.StickersColor;

public interface Billing {

	public double calculateCost(StickersColor rank, double distance);
}
