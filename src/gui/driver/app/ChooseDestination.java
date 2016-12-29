package gui.driver.app;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ChooseDestination extends AbstractWindow{
	
	ChooseDestination(){
		
	}
	
	public void display(Stage primaryStage){
		
		window.setTitle("My Details");
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(20, 20, 20, 20));
		grid.setVgap(15);
		grid.setHgap(10);
		grid.setBackground(
				new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY,
						new Insets(2, 2, 2, 2))));

		Label from = new Label("From:");
		ChoiceBox<String> fromValue = new ChoiceBox<>();
		fromValue.getItems().addAll("Shaar Nesher", "Shaar Nosh", "Taub", "Fishbah",
				"Rifkin Dorms", "Canada Dorms");
		fromValue.setValue("Shaar Nesher");
		//fromValue.getValue();
		GridPane.setConstraints(fromValue, 1, 0);
		GridPane.setConstraints(from, 0, 0);
		grid.getChildren().add(from);
		grid.getChildren().add(fromValue);
		
		Scene scene = new Scene(grid, 300, 250);
		window.setScene(scene);
		window.show();
	}

}
