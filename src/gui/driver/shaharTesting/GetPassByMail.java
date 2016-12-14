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
		
		Label instruction = new Label("Please enter your eMail address in order to get your password");
		GridPane.setConstraints(instruction, 0, 0);
		
//		Label nameLabel = new Label("Mail: ");
//		GridPane.setConstraints(nameLabel, 0, 1);
		
		TextField nameInput = new TextField();
		nameInput.setText("user@gmail.com");
		GridPane.setConstraints(nameInput, 0, 1);
		
		Button sendButton = new Button();
		sendButton.setText("Send Mail");
		sendButton.setOnAction(e->{
			if(!nameInput.getText().endsWith("@gmail.com")){
				AlertBox.display("Bad Input", "Illegal address entered! "
						+ "\nPlease try again.");
			}
			else{
				AlertBox.display("Password Sent", "The password was sent to your eMail account");
			}
			
		});
		GridPane.setConstraints(sendButton, 0,2);
		
		grid.getChildren().addAll(instruction, nameInput, sendButton);
		Scene scene = new Scene(grid, 420,150);
		mainWindow.setScene(scene);
		mainWindow.show();
		
	}

}
