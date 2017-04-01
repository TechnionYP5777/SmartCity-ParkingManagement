package gui.manager.OrLearning;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class AlertBox {

	public static void display(final String title, final String msg) {
		final Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Alert Box");

		final Label label1 = new Label();
		label1.setText(msg);

		final VBox layout = new VBox(10);
		layout.getChildren().add(label1);
		layout.setAlignment(Pos.CENTER);

		window.setScene(new Scene(layout));
		window.showAndWait();
	}

}
