package gui.driver.app;

import java.util.ArrayList;

import data.members.StickersColor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

	// onEntry checks if you just entered the window or you clicked on the mute
	// button
	public static void dealWithMute(MediaPlayer p, ArrayList<Button> muteButtonsAL) {
		for (Button currButton : muteButtonsAL)
			currButton.setGraphic(
					new ImageView(new Image(StaticMethods.class.getResourceAsStream(p.isMute() ? "unmute_button.png" : "mute_button.png"))));
		p.setMute(!p.isMute());
	}

	public static Button cloneButton(Button firstButton) {
		Button $ = new Button();
		$.setText(firstButton.getText());
//		System.out.println("firstButton.getOnAction(): " + firstButton.getOnAction());
//		System.out.println(firstButton.getStyleClass().toString());
		$.getStyleClass().addAll(firstButton.getStyleClass());
		$.setOnAction(e -> {
			StaticMethods.dealWithMute(AbstractWindow.mediaPlayer, AbstractWindow.muteButtonsAL);
		});
		return $;
	}

}
