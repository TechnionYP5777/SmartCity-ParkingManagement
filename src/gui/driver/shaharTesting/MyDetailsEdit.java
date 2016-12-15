package gui.driver.shaharTesting;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MyDetailsEdit {

static Stage window;
	
	public static void display(Stage primaryStage, WindowEnum prevWindow){
		window = primaryStage;
		window.setTitle("Get Password By Email");
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(20,20,20,20));
		grid.setVgap(8);
		grid.setHgap(10);
		grid.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, new Insets(2,2,2,2))));
		
		Label instruction = new Label("Username:");
		GridPane.setConstraints(instruction, 0, 0);
		
		
		TextField eMailInput = new TextField();
		String defaultMail = "user@gmail.com";
		eMailInput.setText(defaultMail);
		GridPane.setConstraints(eMailInput, 1, 0);
		
		Button sendButton = new Button();
		sendButton.setText("Send Mail");
		
		sendButton.setOnAction(e->{
			//move to editing my details
			
			
		});
		
		grid.getChildren().addAll(instruction, eMailInput, sendButton);
		Scene scene = new Scene(grid, 420,150);
		window.setScene(scene);
		window.show();
		
	}
}
