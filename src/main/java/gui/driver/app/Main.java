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

public class Main extends Application {
	
	/**
	 * @author dshames
	 *  This is the main of our program
	 */
	
	Stage window;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		window = primaryStage;
		window.setTitle("Login");
		Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml")); 
		window.setScene(new Scene(root, ScreenSizesConstants.LoginScreenWidth, ScreenSizesConstants.LoginScreenHeight));
		DBManager.initialize();
		window.show();
	}
}
