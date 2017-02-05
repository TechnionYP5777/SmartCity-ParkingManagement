package gui.manager.OrLearning;

import org.controlsfx.control.Notifications;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class NotificationsController {

	@FXML
	private static AnchorPane notificationsAnchor;

	@FXML
	void triggerParkingNotification(final ActionEvent $) {
		final Notifications notificationBuilder = Notifications.create().title("Color has changed")
				.text("In Taub, from RED to GRAY").owner(notificationsAnchor).graphic(null)
				.hideAfter(Duration.seconds(5)).position(Pos.TOP_RIGHT).onAction(λ -> System.out.println("Clicked"));
		notificationBuilder.darkStyle();
		notificationBuilder.showConfirm();
	}

}
