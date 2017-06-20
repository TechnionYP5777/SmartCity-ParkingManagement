package gui.driver.app;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import logic.Billing;
import logic.ParkingSlotRequest;
import logic.PresentParkingSlot;
import javafx.scene.*;
import javafx.fxml.*;
import data.management.DBManager;
import data.management.DatabaseManager;
import data.management.DatabaseManagerImpl;
import data.members.ParkingSlot;
import data.members.StickersColor;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import org.parse4j.ParseGeoPoint;

import Exceptions.LoginException;


public class LoginContorller {
	
	@FXML
	private TextField idField;
	@FXML
	private PasswordField pwField;
	@FXML
	private Hyperlink forgotPwHyperLink;
	@FXML
	private Label statusLabel;
	@FXML
	private Button loginButton;
	@FXML
	private Button createNewButton;
	@FXML
	private ProgressIndicator progressIndicator;
	
	
	@FXML
    protected void initialize(){
		progressIndicator.setVisible(false);
	}
	
	@FXML
	public void forgotPwClicked(ActionEvent event) throws Exception {
	 	Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setTitle("Forgot your password");
		Parent root = FXMLLoader.load(getClass().getResource("ForgotPwScreen.fxml")); 
		window.setScene(new Scene(root,400,500));		
		window.show();
	}
	@FXML
	public void loginButtonClicked(ActionEvent event) throws Exception {
		
		String id = idField.getText();
		String pw = pwField.getText();
		statusLabel.setText("ID or Password are wrong");
		
		if (id.equals("") || pw.equals("")){
			statusLabel.setVisible(true);
			return;		
		}
		
		statusLabel.setText("Loading...");
		statusLabel.setStyle(" -fx-text-fill: black; -fx-font-size: 15px; -fx-font-weight: normal");
		statusLabel.setVisible(true);
		
		Task<String> loginTask = new Task<String>() {
            @Override
            protected String call() throws Exception {	
            	try {
	            	DBManager.login("Driver", "id", id, "password", pw);
	    			
            	} catch (LoginException e){
            		return e.toString();
        		}
  	
            	return "success";
	        }
        };
       new Thread(loginTask).start();
       
       progressIndicator.progressProperty().bind(loginTask.progressProperty());
       progressIndicator.setVisible(true); 
       
       loginTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
           @Override
           public void handle(WorkerStateEvent workerStateEvent) {
        	   progressIndicator.setVisible(false);
               String result =  loginTask.getValue();
               
   			if (result.equals("user doesn't exists") || result.equals("password doesn't match")){
				statusLabel.setText("ID or Password are wrong");
				statusLabel.setStyle(" -fx-text-fill: red; -fx-font-size: 15px; -fx-font-weight: bold");
				statusLabel.setVisible(true);
				return;
   			}
   			if(result.equals("success")){
   				try {
	   				Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
	   				window.setTitle("Choose parking slot");
	   				Parent root = FXMLLoader.load(getClass().getResource("ChooseParkingSlotScreen.fxml")); 
	   				window.setScene(new Scene(root,1300,900));		
	   				window.show();
   				}
   				catch (Exception e){
   					
   				}
   				
   			}
               

       			

           }
       });
		/*
		try {
			
			DBManager.login("Driver", "id", id, "password", pw);
			statusLabel.setText("Successful login");
			statusLabel.setStyle(" -fx-text-fill: green; -fx-font-size: 15px; -fx-font-weight: bold");
			statusLabel.setVisible(true);
			
		 	Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
			window.setTitle("Choose destination and time");
			Parent root = FXMLLoader.load(getClass().getResource("ChooseDestinationAndTimeScreen.fxml")); 
			window.setScene(new Scene(root,600,500));		
			window.show();
			/*
			Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
			window.setTitle("Map");
			Parent root = FXMLLoader.load(getClass().getResource("ChooseParkingSlotScreen.fxml")); 
			window.setScene(new Scene(root,600,600));		
			window.show();

		} catch (LoginException e){
			String err = e.toString();
			if (err.equals("user doesn't exists") || err.equals("password doesn't match")){
				statusLabel.setVisible(true);
				return;		
			}

		}*/
		
	}
	@FXML
	public void createButtonClicked(ActionEvent event) throws Exception{
	 	Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
		window.setTitle("Registration");
		Parent root = FXMLLoader.load(getClass().getResource("RegistrationScreen.fxml")); 
		window.setScene(new Scene(root,400,700));		
		window.show();


	}
}
