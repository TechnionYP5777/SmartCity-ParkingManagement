package gui.driver.shaharTesting;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MyDetails {
	static Stage window;

	public static void display(Stage primaryStage, WindowEnum prevWindow, final ArrayList<Label> newLabels,
			final ArrayList<Label> newValues) {
		window = primaryStage;
		window.setTitle("My Details");
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(20, 20, 20, 20));
		grid.setVgap(15);
		grid.setHgap(10);
		grid.setBackground(
				new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, new Insets(2, 2, 2, 2))));

		Button editDetailsButton = new Button();
		editDetailsButton.setText("Edit");
		int buttonIndex;
		ArrayList<Label> labels;
		ArrayList<Label> values;
		
		if (newLabels == null) {
			labels  = new ArrayList<Label>();
			values  = new ArrayList<Label>();
			
			Label eMailLabel = new Label("eMail:");
			Label eMail = new Label("user@gmail.com");
			labels.add(eMailLabel);
			values.add(eMail);

			Label UsernameLabel = new Label("Username:");
			Label username = new Label("AwesomeUser");
			labels.add(UsernameLabel);
			values.add(username);

			Label carNumberLabel = new Label("Car Number:");
			Label carNumber = new Label("123456789");
			labels.add(carNumberLabel);
			values.add(carNumber);

		}
		else{
			labels = newLabels;
			values = newValues;
		}
		
		int i = 0;
		for (; i < labels.size(); i++) {
			GridPane.setConstraints(labels.get(i), 0, i);
			GridPane.setConstraints(values.get(i), 1, i);
			grid.getChildren().addAll(labels.get(i), values.get(i));
		}
		
		buttonIndex = i;
		editDetailsButton.setOnAction(e -> {
			// move to editing my details
				MyDetailsEdit.display(primaryStage, prevWindow, labels, values);

		});
		
		GridPane.setConstraints(editDetailsButton, 0, buttonIndex);

		grid.getChildren().add(editDetailsButton);
		Scene scene = new Scene(grid, 300, 150);
		window.setScene(scene);
		window.show();

	}

}
