package gui.driver.shaharTesting;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {

	public static void display(String title, String message) {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(250);

		Label label = new Label();
		label.setText(message);
		Button button = new Button("close");
		button.setOnAction(λ -> window.close());
		VBox layout = new VBox();
		layout.getChildren().addAll(label, button);
		layout.setAlignment(Pos.CENTER);

		window.setScene(new Scene(layout));
		window.showAndWait();
	}
}
