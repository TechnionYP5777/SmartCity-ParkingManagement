package gui.driver.shaharTesting;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.geometry.Insets;
//import javafx.geometry.Pos;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.ListView;
//import javafx.scene.layout.StackPane;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.StackPane;
//import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GetPassByMail extends Application{
	Stage mainWindow;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		mainWindow = primaryStage;
		mainWindow.setTitle("Get Password By Email");
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(20,20,20,2));
		grid.setVgap(8);
		grid.setHgap(10);
		
		Label nameLabel = new Label("Mail: ");
		GridPane.setConstraints(nameLabel, 0, 0);
		
		TextField nameInput = new TextField();
		nameInput.setPromptText("<mail>");
		GridPane.setConstraints(nameInput, 1, 0);
		
		Button sendButton = new Button();
		sendButton.setText("Send Mail");
		GridPane.setConstraints(sendButton, 1,2);
		
		grid.getChildren().addAll(nameLabel, nameInput, sendButton);
		Scene scene = new Scene(grid, 300,150);
		mainWindow.setScene(scene);
		mainWindow.show();
		
	}

}
