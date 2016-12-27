package gui.driver.app;

import data.members.StickersColor;

public class StaticMethods {

	public static String getStickerClolorFromEnum(StickersColor ¢) {
		switch (¢) {
		case BLUE:
			return "Blue";
		case GREEN:
			return "Green";
		case WHITE:
			return "White";
		case RED:
			return "Red";
		case BORDEAUX:
			return "Bordeaux";
		case YELLOW:
			return "Yellow";
		default:
			return "";
		}
	}
}
