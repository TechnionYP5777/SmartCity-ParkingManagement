package gui.driver.app;

import javafx.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.application.Application;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.geometry.Insets;
import javafx.geometry.HPos;
import javafx.scene.control.Label;

public class Login extends Application {
	
	Stage window;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		window = primaryStage;
		window.setTitle("Login Window");
		
		GridPane grid = new GridPane();
		grid.setVgap(10);
		
		Label headLine = new Label("System Log in.");
		GridPane.setConstraints(headLine, 0,0);
		GridPane.setHalignment(headLine, HPos.CENTER);
		
		TextField userInput = new TextField();
		userInput.setPromptText("Username");
		GridPane.setConstraints(userInput, 0,1);
		GridPane.setHalignment(userInput, HPos.CENTER);
		
		TextField passInput = new TextField();
		passInput.setPromptText("Password");
		GridPane.setConstraints(passInput, 0,2);
		GridPane.setHalignment(passInput, HPos.CENTER);
		
		grid.getChildren().addAll(headLine, userInput, passInput);

		Scene scene = new Scene(grid,450,450);
		window.setScene(scene);		
		window.show();
	}
}


