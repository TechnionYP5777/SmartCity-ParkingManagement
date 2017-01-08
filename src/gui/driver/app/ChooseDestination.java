package gui.driver.app;

import java.util.Set;

import com.lynden.gmapsfx.javascript.object.LatLong;

import gui.map.PmMap;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class ChooseDestination extends AbstractWindow{
	
	ChooseDestination(){
		windowEnum = WindowEnum.CHOOSE_DESTINATION;
		window = new Stage();
		window.getIcons().add(new Image(getClass().getResourceAsStream("Smart_parking_icon.png")));
	}
	
	public void display(Stage primaryStage){
		
		window.setTitle("Choose Destination");
		Label title = new Label(); 
		DropShadow shadow = new DropShadow();
		shadow.setOffsetY(4.0);
		shadow.setColor(Color.color(0.4f, 0.4f, 0.4f));
		title.setEffect(shadow);
		title.setTextAlignment(TextAlignment.CENTER);
		title.setText("Navigate");
		title.setFont(Font.font(null, FontWeight.BOLD, 48));
		title.getStyleClass().add("label-title"); 
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(20, 20, 20, 20));
		grid.setVgap(15);
		grid.setHgap(10);
		grid.setBackground(
				new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY,
						new Insets(2, 2, 2, 2))));
	
		Set<String> locationsList = navigate.getLocations();
		
		GridPane.setConstraints(title, 0, 0);
		GridPane.setColumnSpan(title, 2);
		int currIdx = 1; 
		Label from = new Label("From:");
		ChoiceBox<String> fromValue = new ChoiceBox<>();
		fromValue.getItems().addAll(locationsList);
		fromValue.setValue("Taub");
		fromValue.getStyleClass().add("cb");
		GridPane.setConstraints(fromValue, 1, currIdx);
		GridPane.setConstraints(from, 0, currIdx++);
		
		Label to = new Label("To:");
		ChoiceBox<String> toValue = new ChoiceBox<>();
		toValue.getItems().addAll(locationsList);
		toValue.setValue("Nosh Gate");
		toValue.getStyleClass().add("cb");
		GridPane.setConstraints(toValue, 1, currIdx);
		GridPane.setConstraints(to, 0, currIdx++);
		
		
		
		Button buttonBack = new Button("Back");
		buttonBack.setOnAction(e -> {
			this.window.close();
			AbstractWindow.prevWindows.get(AbstractWindow.prevWindows.size()-1).window.show();
			AbstractWindow.prevWindows.remove(AbstractWindow.prevWindows.size()-1);

		});
		GridPane.setConstraints(buttonBack, 0, currIdx);
		//buttonBack.setDisable(true);
		
		Button buttonGO = new Button("GO!");
		
		buttonGO.getStyleClass().add("button-go");
		
		buttonGO.setOnAction(e -> {
			//************Add navigation functionality************//
			window.close();
			ChooseAction.prevWindows.add(this);
			PmMap map =new PmMap();
			map.display(primaryStage);

		});
		GridPane.setConstraints(buttonGO, 1, currIdx++);
		//buttonGO.setDisable(true);
		
		grid.getChildren().addAll(title, from, fromValue, to, toValue, buttonBack, buttonGO);
		
		Scene scene = new Scene(grid);
		scene.getStylesheets().add(getClass().getResource("mainStyle.css").toExternalForm());
		window.setScene(scene);
		window.show();
	}

}
