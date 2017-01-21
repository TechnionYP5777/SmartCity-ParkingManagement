package gui.driver.app;


//import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GetPassByMail extends AbstractWindow {
	
	public GetPassByMail(){
		windowEnum = WindowEnum.GET_PASS_BY_MAIL;
		window.getIcons().add(new Image(getClass().getResourceAsStream("Smart_parking_icon.png")));
		//window.initModality(Modality.APPLICATION_MODAL);
	}
	
	public void display(Stage primaryStage, WindowEnum prevWindow){
		window = primaryStage;
		window.setTitle("Get Password By Email");
		//window.initModality(Modality.APPLICATION_MODAL);
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(20,20,20,20));
		grid.setVgap(8);
		grid.setHgap(10);
		grid.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, new Insets(2,2,2,2))));
		window.setWidth(550);
		window.setHeight(180);
		
		Label instruction = new Label("Please enter your eMail address in order to get your password");
		GridPane.setConstraints(instruction, 0, 0);
		
		
		TextField eMailInput = new TextField();
		String defaultMail = "user@gmail.com";
		eMailInput.setText(defaultMail);
		GridPane.setConstraints(eMailInput, 0, 1);
		
		Button sendButton = new Button();
		sendButton.setText("Send Mail");
		
		sendButton.setOnAction(e->{
			if(eMailInput.getText().equals(defaultMail))
				(new AlertBox()).display("Bad Input", "The mail you entered is the default! " + "\nPlease try again.");
			else if (!isValidMailAddress(eMailInput))
				(new AlertBox()).display("Bad Input", "Illegal address entered! " + "\nPlease try again.");
			else {
				try {
					sendPassword(eMailInput);
				} catch (Exception eMailException) {
					System.out.println(e);
				}
				this.window.close();
				AbstractWindow.prevWindows.get(AbstractWindow.prevWindows.size()-1).window.show();
				AbstractWindow.prevWindows.remove(AbstractWindow.prevWindows.size()-1);
				(new AlertBox()).display("Password Sent", "The password was sent to your eMail account");
			}
			
		});
		GridPane.setConstraints(sendButton, 0,2);
		
		Button backButton = new Button();
		ImageView ivBack= new ImageView(new Image(getClass().getResourceAsStream("back_button.png")));
		backButton.setGraphic(ivBack);
		backButton.getStyleClass().add("button-go");
		backButton.setOnAction( (e) -> {
			// move to editing my details
			this.window.close();
			handleBack(); 
		});
		GridPane.setConstraints(backButton, 1, 2);
		
		grid.getChildren().addAll(instruction, eMailInput, sendButton, backButton);
		Scene scene = new Scene(grid, 420,150);
		scene.getStylesheets().add(getClass().getResource("mainStyle.css").toExternalForm());
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
	
	public void handleBack() {
		AbstractWindow.prevWindows.get(AbstractWindow.prevWindows.size()-1).window.show();
		AbstractWindow.prevWindows.remove(AbstractWindow.prevWindows.size()-1);
	}

}
