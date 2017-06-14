package logic;

import data.members.StickersColor;

public class BasicBillingClass  implements BillingClass{

	@Override
	public double calculateCost(StickersColor rank, double distance) {
		return 20;
	}

}
