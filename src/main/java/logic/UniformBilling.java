package logic;

import data.members.StickersColor;

public class UniformBilling implements Billing{

	@Override
	public double calculateCost(StickersColor rank, double distance) {
		return 20;
	}

}
