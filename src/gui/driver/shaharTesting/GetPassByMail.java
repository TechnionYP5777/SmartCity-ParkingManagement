package gui.driver.shaharTesting;


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

public class GetPassByMail{
	static Stage window;
	
	public static void display(Stage primaryStage){
		window = primaryStage;
		window.setTitle("Get Password By Email");
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(20,20,20,20));
		grid.setVgap(8);
		grid.setHgap(10);
		
		Label instruction = new Label("Please enter your eMail address in order to get your password");
		GridPane.setConstraints(instruction, 0, 0);
		
//		Label nameLabel = new Label("Mail: ");
//		GridPane.setConstraints(nameLabel, 0, 1);
		
		TextField eMailInput = new TextField();
		String defaultMail = "user@gmail.com";
		eMailInput.setText(defaultMail);
		GridPane.setConstraints(eMailInput, 0, 1);
		
		Button sendButton = new Button();
		sendButton.setText("Send Mail");
		
		sendButton.setOnAction(e->{
			if(eMailInput.getText().equals(defaultMail))
				AlertBox.display("Bad Input", "The mail you entered is the default! " + "\nPlease try again.");
			else if (!isValidMailAddress(eMailInput))
				AlertBox.display("Bad Input", "Illegal address entered! " + "\nPlease try again.");
			else {
				try {
					sendPassword(eMailInput);
				} catch (Exception eMailException) {
					System.out.println(e);
				}
				AlertBox.display("Password Sent", "The password was sent to your eMail account");
				//window.setScene(Main.getScene());
			}
			
		});
		GridPane.setConstraints(sendButton, 0,2);
		
		grid.getChildren().addAll(instruction, eMailInput, sendButton);
		Scene scene = new Scene(grid, 420,150);
		window.setScene(scene);
		window.show();
		
	}

	public static boolean isValidMailAddress(TextField eMailInput){
		//A regular expression, translated into code by regex101.com
		return eMailInput.getText().matches("[\\d\\w\\.]+@(campus|gmail|walla|hotmail|t2)(\\.(technion|ac|il|net|com)+)+");
	}
	
	public static void sendPassword(TextField eMailInput){
		//Send password to the mail entered, display error message if there is a problem.
		
	}

}
