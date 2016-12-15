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
	
	public static void display(Stage primaryStage, WindowEnum prevWindow){
		window = primaryStage;
		window.setTitle("Get Password By Email");
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(20,20,20,20));
		grid.setVgap(8);
		grid.setHgap(10);
		grid.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, new Insets(2,2,2,2))));
		
		ArrayList<Label> labels = new ArrayList<Label>();
		ArrayList<Label> values = new ArrayList<Label>();
		
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
		
		Button changeDetailsButton = new Button();
		changeDetailsButton.setText("Send Mail");
		
		changeDetailsButton.setOnAction(e->{
			//move to editing my details
			
			
		});
		GridPane.setConstraints(changeDetailsButton, 0, 1);
		
		GridPane.setConstraints(eMailLabel, 0, 0);
		GridPane.setConstraints(eMail, 1, 0);
		grid.getChildren().addAll(eMailLabel, eMail, changeDetailsButton);
		Scene scene = new Scene(grid, 420,150);
		window.setScene(scene);
		window.show();
		
	}

}
