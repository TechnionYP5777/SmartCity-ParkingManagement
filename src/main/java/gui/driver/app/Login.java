package gui.driver.app;

import data.management.DBManager;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;

public class Login extends Application {
	
	Stage window;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		/*
		window = primaryStage;
		window.setTitle("Login");
		Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml")); 
		window.setScene(new Scene(root,400,500));
		DBManager.initialize();
		window.show();*/
		
		window = primaryStage;
		window.setTitle("Choose parking slot");
		Parent root = FXMLLoader.load(getClass().getResource("ChooseParkingSlotScreen.fxml")); 
		window.setScene(new Scene(root,1200,800));		
		window.show();
	}
}


