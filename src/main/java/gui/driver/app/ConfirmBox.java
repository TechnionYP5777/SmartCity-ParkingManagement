package gui.driver.app;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {

	static boolean answer;
	static Button yesButton, noButton;

	public boolean display(final String title, final String message) {
		final Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(250);
		window.setMinHeight(150);
		window.getIcons().add(new Image(getClass().getResourceAsStream("Smart_parking_icon.png")));

		final Label label = new Label();
		label.setText(message);

		yesButton = new Button("Yes");
		yesButton.setOnAction(λ -> {

			answer = true;
			window.close();
		});

		noButton = new Button("No");
		noButton.setOnAction(λ -> {
			answer = false;
			window.close();
		});

		final VBox layout = new VBox();
		layout.getChildren().addAll(label, noButton, yesButton);
		layout.setAlignment(Pos.CENTER);
		final Scene scene = new Scene(layout);
		scene.getStylesheets().add(getClass().getResource("mainStyle.css").toExternalForm());
		window.setScene(scene);
		window.showAndWait();

		return answer;
	}

}
