package gui.driver.app;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SaveDetailsButton {

	public static void display(final String title, final String message) {
		Stage window;
		final Button button = new Button();
		window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(250);

		final Label label = new Label();
		label.setText(message);
		button.setOnAction(λ -> window.close());

		final VBox layout = new VBox();
		layout.getChildren().addAll(label, button);
		layout.setAlignment(Pos.CENTER);

		window.setScene(new Scene(layout));
		window.showAndWait();

	}
}
