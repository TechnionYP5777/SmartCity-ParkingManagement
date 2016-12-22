package gui.driver.app;

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

public class MyDetails extends AbstractWindow{
	
	
	public MyDetails(){
		windowEnum = WindowEnum.MY_DETAILS;
	}

	public void display(Stage primaryStage, WindowEnum prevWindow, final ArrayList<Label> newLabels,
			final ArrayList<Label> newValues, ArrayList<AbstractWindow> prevWindows) {
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
		
		if (newLabels != null) {
			labels = newLabels;
			values = newValues;
		} else {
			labels = new ArrayList<Label>();
			values = new ArrayList<Label>();
			
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
		
		int i = 0;
		for (; i < labels.size(); ++i) {
			GridPane.setConstraints(labels.get(i), 0, i);
			GridPane.setConstraints(values.get(i), 1, i);
			grid.getChildren().addAll(labels.get(i), values.get(i));
		}
		
		buttonIndex = i;
		editDetailsButton.setOnAction(e -> {
			// move to editing my details
			MyDetailsEdit MDE = new MyDetailsEdit();
			prevWindows.add(this);
			MDE.display(primaryStage, prevWindow, labels, values, prevWindows);

		});
		
		//TODO: finish it
		Button backButton = new Button();
		backButton.setText("Back");
		backButton.setOnAction(e -> {
			// move to editing my details
			this.window.close();
			prevWindows.get(prevWindows.size()-1).window.show();
			prevWindows.remove(prevWindows.size()-1);
		});
		GridPane.setConstraints(backButton, 1, buttonIndex);
		
		GridPane.setConstraints(editDetailsButton, 0, buttonIndex);

		grid.getChildren().addAll(editDetailsButton, backButton);
		Scene scene = new Scene(grid, 300, 150);
		window.setScene(scene);
		window.show();

	}

}
