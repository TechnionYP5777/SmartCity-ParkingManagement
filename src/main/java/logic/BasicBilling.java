package logic;

import data.members.StickersColor;

public class BasicBilling  implements Billing{

	@Override
	public double calculateCost(StickersColor rank, double distance) {
		return 20+10*rank.ordinal()-distance;
	}

}
