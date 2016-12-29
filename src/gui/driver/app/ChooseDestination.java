package gui.driver.app;

import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
		windowEnum = WindowEnum.CHOOSE_DESTINATION;
		window = new Stage();
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

		List list = new List();
	
		ArrayList<String> locationsList = new ArrayList<String>(Arrays.asList(
				"Shaar Nesher", "Shaar Nosh", "Taub", "Fishbah",
				"Rifkin Dorms", "Canada Dorms"));
		
		int currIdx = 0;
		Label from = new Label("From:");
		ChoiceBox<String> fromValue = new ChoiceBox<>();
		fromValue.getItems().addAll(locationsList);
		fromValue.setValue("Shaar Nesher");
		GridPane.setConstraints(fromValue, 1, currIdx);
		GridPane.setConstraints(from, 0, currIdx++);
		
		Label to = new Label("To:");
		ChoiceBox<String> toValue = new ChoiceBox<>();
		toValue.getItems().addAll(locationsList);
		toValue.setValue("Shaar Nosh");
		GridPane.setConstraints(toValue, 1, currIdx);
		GridPane.setConstraints(to, 0, currIdx++);
		
		
		
		Button buttonBack = new Button("Back");
		buttonBack.setOnAction(e -> {
			window.close();
			ChooseAction CA = new ChooseAction();
			ChooseAction.prevWindows.add(this);
			CA.display(primaryStage, WindowEnum.CHOOSE_ACTION);

		});
		GridPane.setConstraints(buttonBack, 0, currIdx);
		//buttonBack.setDisable(true);
		
		Button buttonGO = new Button("GO!");
		buttonGO.setPrefSize(100, 100);
		//buttonGO.setStyle("-fx-background-color: green; -fx-text-fill: black;");
		buttonGO.setStyle("-fx-background-color: #3c7fb1, linear-gradient(#fafdfe, #e8f5fc), "
				+ "linear-gradient(#eaf6fd 0%, #d9f0fc 49%, #bee6fd 50%, #a7d9f5 100%);"
				+ "-fx-background-insets: 0,1,2; -fx-background-radius: 3,2,1; -fx-padding: 3 30 3 30;"
				+ "-fx-text-fill: black; -fx-font-size: 14px;");
		//buttonGO.setHover(true);
		
		buttonGO.setOnAction(e -> {
			//************Add navigation functionality************//
			window.close();
			ChooseAction CA = new ChooseAction();
			ChooseAction.prevWindows.add(this);
			CA.display(primaryStage, WindowEnum.CHOOSE_ACTION);

		});
		GridPane.setConstraints(buttonGO, 1, currIdx++);
		//buttonGO.setDisable(true);
		
		grid.getChildren().addAll(from, fromValue, to, toValue, buttonBack, buttonGO);
		
		Scene scene = new Scene(grid, 300, 150);
		window.setScene(scene);
		window.show();
	}

}
