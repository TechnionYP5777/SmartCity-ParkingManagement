package gui.driver.app;

import data.members.StickersColor;
import javafx.scene.control.Button;
import javafx.scene.media.MediaPlayer;

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
//onEntry checks if you just entered the window or you clicked on the mute button
	public static void dealWithMute(MediaPlayer p, Button buttonMute, boolean onEntry) {
		if (!onEntry)
			if (p.isMute()) {
				p.setMute(false);
				buttonMute.setText("MUTE");
				buttonMute.getStyleClass().remove("button-muteON");
				buttonMute.getStyleClass().add("button-muteOFF");
			} else {
				p.setMute(true);
				buttonMute.setText("UNMUTE");
				System.out.println("removing button-muteOFF:");
				System.out.println(buttonMute.toString());
				buttonMute.getStyleClass().remove("button-muteOFF");
				System.out.println(buttonMute.toString());
				buttonMute.getStyleClass().add("button-muteON");
			}
		else if (!p.isMute()) {
			p.setMute(false);
			buttonMute.setText("MUTE");
			buttonMute.getStyleClass().remove("button-muteON");
			buttonMute.getStyleClass().add("button-muteOFF");
		} else {
			p.setMute(true);
			buttonMute.setText("UNMUTE");
			buttonMute.getStyleClass().remove("button-muteOFF");
			buttonMute.getStyleClass().add("button-muteON");
		}

	}

}
